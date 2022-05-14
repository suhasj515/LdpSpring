package com.ldp.assignment.ldp.dao;

import com.ldp.assignment.ldp.entity.Customer;
import com.ldp.assignment.ldp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    public Customer findCustomerByUser(User user);

}
