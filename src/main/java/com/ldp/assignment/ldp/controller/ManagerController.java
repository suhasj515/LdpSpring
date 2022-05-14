package com.ldp.assignment.ldp.controller;

import com.ldp.assignment.ldp.dto.CustomerDTO;
import com.ldp.assignment.ldp.entity.Customer;
import com.ldp.assignment.ldp.entity.Hotel;
import com.ldp.assignment.ldp.entity.Manager;
import com.ldp.assignment.ldp.services.CustomerService;
import com.ldp.assignment.ldp.services.HotelService;
import com.ldp.assignment.ldp.services.ManagerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @Autowired
    HotelService hotelService;

    @Autowired
    CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;



    @GetMapping("/{id}")
    public String viewManger(@PathVariable int id, Model model){
        Manager manager= managerService.getById(id);
        String a="as";

        Hotel hotel=manager.getHotel();
        List<Customer> customerList= hotel.getCustomersList();

        model.addAttribute("manager",manager);
        model.addAttribute("hotel",hotel);
        model.addAttribute("customerList",customerList);

        return "manager_page";
    }

    @GetMapping("/update")
    public String updateCustomer(@RequestParam("customerId") int id, Model model){
        CustomerDTO customer= customerService.getById(id);
        model.addAttribute("customer",customer);
        Hotel hotel= customer.getHotels();
        model.addAttribute("hotel",hotel);

        return "customer-form";
    }

    @GetMapping("/cancel-booking")
    public String cancelBookingCustomer(@RequestParam("customerId") Integer id, Model model){
        CustomerDTO customer= customerService.getById(id);
        customer.setHotels(null);
        customerService.save(customer);

        return "redirect:"+"/";
    }

    @GetMapping("/save")
    public String save() {
        Hotel hotel = new Hotel("apple", 3000, "andaman");
        hotelService.save(hotel);
        return "bye";
    }

}
