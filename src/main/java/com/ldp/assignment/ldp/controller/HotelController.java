package com.ldp.assignment.ldp.controller;

import com.ldp.assignment.ldp.entity.Hotel;
import com.ldp.assignment.ldp.services.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    private static final Logger log = LoggerFactory.getLogger(HotelController.class);

    @GetMapping("/save")
    public String save()
    {
        Hotel hotel= new Hotel("apple",3000,"andaman");

        if(log.isInfoEnabled()) {
            log.info("hotel to be saved : {}",hotel);
        }
        hotelService.save(hotel);
        return "bye";

    }

}
