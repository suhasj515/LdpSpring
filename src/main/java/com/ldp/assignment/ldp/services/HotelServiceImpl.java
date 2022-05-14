package com.ldp.assignment.ldp.services;

import com.ldp.assignment.ldp.dao.HotelRepository;
import com.ldp.assignment.ldp.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService{

    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getById(int id) {
        return hotelRepository.getById(id);
    }
}
