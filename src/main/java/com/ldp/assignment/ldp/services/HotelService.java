package com.ldp.assignment.ldp.services;

import com.ldp.assignment.ldp.entity.Hotel;

import java.util.List;

public interface HotelService {
    public Hotel save(Hotel hotel);

    public List<Hotel> findAll();

    public Hotel getById(int id);
}
