package com.ldp.assignment.ldp.dao;

import com.ldp.assignment.ldp.entity.Manager;
import com.ldp.assignment.ldp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,Integer> {
    public Manager findManagerByUser(User user);
}
