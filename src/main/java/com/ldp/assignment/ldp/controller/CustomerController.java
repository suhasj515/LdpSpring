package com.ldp.assignment.ldp.controller;


import com.ldp.assignment.ldp.dto.CustomerDTO;
import com.ldp.assignment.ldp.dto.UserDTO;
import com.ldp.assignment.ldp.entity.Hotel;
import com.ldp.assignment.ldp.exceptions.CustomerNotFoundException;
import com.ldp.assignment.ldp.services.CustomerService;
import com.ldp.assignment.ldp.services.HotelService;
import com.ldp.assignment.ldp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private String redirect="redirect:";


    @GetMapping("/{id}")
    public String viewCustomer(@PathVariable int id, Model model){

        CustomerDTO customerDTO= customerService.getById(id);


        if(customerDTO.getFirstName()==null){
            throw new CustomerNotFoundException("Customer not found in system!!");
        }
        List<Hotel> avilableHotelList;
        Boolean booked;
        if(customerDTO.getHotels()!=null) {
            Hotel hotel = customerDTO.getHotels();
           avilableHotelList = hotelService.findAll();
           avilableHotelList.remove(hotel);
           booked=true;
            model.addAttribute("booked",booked);
            model.addAttribute("hotel",hotel);
        }else {
            booked=false;
            model.addAttribute("booked",booked);
            avilableHotelList= hotelService.findAll();
        }

        model.addAttribute("customer",customerDTO);
        model.addAttribute("avilableHotelList",avilableHotelList);

        return "customer_page";
    }

    @GetMapping("/cancel_booking")
    public String cancelBooking(@RequestParam("customerId") Integer id)
    {

        CustomerDTO customerDTO= customerService.getById(id);
        if(log.isInfoEnabled()) {
            log.info("Cancelling hotel for customer : {}",customerDTO);
        }
        customerDTO.setHotels(null);
        customerService.save(customerDTO);


        return redirect+"http://localhost:8082/customer/"+id;
    }

    @PostMapping("/booking")
    public String bookHotel(@ModelAttribute("tempCustomer") CustomerDTO tempCustomerDTO, @RequestParam("hotelId") int id) throws ParseException {

        if(log.isDebugEnabled()) {
            log.debug("String working? {}", tempCustomerDTO);
        }

        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(tempCustomerDTO.getFirstName());


        Hotel hotel= hotelService.getById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName= auth.getName();
        UserDTO user= userService.getById(userName);
        CustomerDTO customerDTO= customerService.findCustomerByUser(user);

        customerDTO.setDate(new Date(date1.getTime() + (1000 * 60 * 60 * 24)));
        customerDTO.setHotels(hotel);
        if(log.isInfoEnabled()) {
            log.info("Customer after setting date: {}",customerDTO);
        }
        customerService.save(customerDTO);
        int customerId= customerDTO.getId();

        return redirect+"http://localhost:8082/customer/"+customerId;
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") CustomerDTO customerDTO){
        CustomerDTO prevCustomerDTO= customerService.getById(customerDTO.getId());
        if(log.isInfoEnabled()) {
            log.info(" New Customer: {}",customerDTO);
            log.info(" previous Customer: {}",prevCustomerDTO);
        }
        customerDTO.setHotels(prevCustomerDTO.getHotels());
        customerDTO.setUser(prevCustomerDTO.getUser());
        customerService.save(customerDTO);

        return redirect+"http://localhost:8082/";
    }

    @GetMapping("/confirm-booking")
    public String addDate(@RequestParam("hotelId") int id,Model model){


        Hotel hotel= hotelService.getById(id);
        model.addAttribute("hotel",hotel);
        model.addAttribute("tempCustomer",new CustomerDTO());

        return "confirm-bookin";
    }
}
