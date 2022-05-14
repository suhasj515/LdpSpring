package com.ldp.assignment.ldp.dto;

import com.ldp.assignment.ldp.entity.Customer;
import com.ldp.assignment.ldp.entity.Manager;
import lombok.Data;

@Data
public class UserDTO {


    private String userName;

    private String password;

    private int enabled;

    private Customer customer;

    private Manager manager;
}
