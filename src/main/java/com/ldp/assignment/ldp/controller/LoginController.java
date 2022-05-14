package com.ldp.assignment.ldp.controller;

import com.ldp.assignment.ldp.dao.UserRepository;
import com.ldp.assignment.ldp.dto.AuthoritiesDTO;
import com.ldp.assignment.ldp.dto.CustomerDTO;
import com.ldp.assignment.ldp.dto.UserDTO;
import com.ldp.assignment.ldp.entity.Authorities;
import com.ldp.assignment.ldp.entity.Customer;
import com.ldp.assignment.ldp.entity.User;
import com.ldp.assignment.ldp.services.AuthoritiesService;
import com.ldp.assignment.ldp.services.CustomerService;
import com.ldp.assignment.ldp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthoritiesService authoritiesService;

    @Autowired
    CustomerService customerService;


    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {

        return "login-page";

    }

    // add request mapping for /access-denied

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";

    }

    @GetMapping("/register/showRegistrationForm")
    public String showRegistrationForm(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("customer",new Customer());
        model.addAttribute("authorities", new Authorities());
        return "registration-form";
    }

    @PostMapping("/register/processRegistrationForm")
    public String processRegister(@ModelAttribute("user") UserDTO userDTO, @ModelAttribute("customer") CustomerDTO customerDTO, @ModelAttribute("authorities") AuthoritiesDTO authoritiesDTO){
        userDTO.setEnabled(1);

        userDTO.setPassword("{noop}"+userDTO.getPassword());
        authoritiesDTO.setAuthority("ROLE_CUSTOMER");
        userService.save(userDTO);
        authoritiesService.save(authoritiesDTO);
        User tempUser= userRepository.getById(userDTO.getUserName());
        customerDTO.setUser(tempUser);
        customerService.save(customerDTO);
        return "registration-confirmation";
    }
}
