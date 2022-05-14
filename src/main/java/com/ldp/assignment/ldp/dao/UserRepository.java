package com.ldp.assignment.ldp.dao;

import com.ldp.assignment.ldp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
