package com.ldp.assignment.ldp.dao;

import com.ldp.assignment.ldp.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
}
