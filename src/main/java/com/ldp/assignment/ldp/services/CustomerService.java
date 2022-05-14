package com.ldp.assignment.ldp.services;

import com.ldp.assignment.ldp.dto.CustomerDTO;
import com.ldp.assignment.ldp.dto.UserDTO;
import com.ldp.assignment.ldp.entity.Customer;

public interface CustomerService {

    public CustomerDTO getById(int id);
    public Customer save(CustomerDTO customerDTO);
    public CustomerDTO findCustomerByUser(UserDTO userDTO);

}
