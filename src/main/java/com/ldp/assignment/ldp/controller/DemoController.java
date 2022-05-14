package com.ldp.assignment.ldp.controller;

import com.ldp.assignment.ldp.dto.CustomerDTO;
import com.ldp.assignment.ldp.dto.UserDTO;
import com.ldp.assignment.ldp.entity.Manager;
import com.ldp.assignment.ldp.services.CustomerService;
import com.ldp.assignment.ldp.services.ManagerService;
import com.ldp.assignment.ldp.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {


    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    ManagerService managerService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/")
    public String loginRedirect(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName= auth.getName();
        String role= String.valueOf(auth.getAuthorities());

        UserDTO userDTO= userService.getById(userName);

        if(log.isInfoEnabled())
        {
            log.info("Logged in user : {}",userDTO);
        }
        if(role.equals("[ROLE_CUSTOMER]")) {
            if(log.isInfoEnabled()) {
                log.info("Logged in Customer: {}", customerService.findCustomerByUser(userDTO));
            }
            CustomerDTO customer= customerService.findCustomerByUser(userDTO);
            return "redirect:"+"http://localhost:8082/customer/"+customer.getId();
        }else if(role.equals("[ROLE_MANAGER]")) {
            if(log.isInfoEnabled())
            {
                log.info("Manager Logged in : {}", managerService.findManagerByUser(userDTO));
            }
            Manager manager= managerService.findManagerByUser(userDTO);
            return "redirect:"+"http://localhost:8082/manager/"+manager.getId();
        }
        return "hello";
    }

}
