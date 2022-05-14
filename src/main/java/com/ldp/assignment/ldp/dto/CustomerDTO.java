package com.ldp.assignment.ldp.dto;

import com.ldp.assignment.ldp.entity.Hotel;
import com.ldp.assignment.ldp.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerDTO {


    private int id;


    private String firstName;


    private String lastName;

    private String email;


    private Date date;


    private Hotel hotels;


    private User user;

}
