package com.ldp.assignment.ldp.services;

import com.ldp.assignment.ldp.dao.CustomerRepository;
import com.ldp.assignment.ldp.dto.CustomerDTO;
import com.ldp.assignment.ldp.dto.UserDTO;
import com.ldp.assignment.ldp.entity.Customer;
import com.ldp.assignment.ldp.entity.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    @Transactional
    public CustomerDTO getById(int id) {
        Customer customer=customerRepository.getById(id);
        return modelMapper.map(customer,CustomerDTO.class);
    }

    @Override
    @Transactional
    public Customer save(CustomerDTO customerDTO) {
        Customer customer=maptoEntity(customerDTO);
        if(log.isInfoEnabled()) {
            log.info("degub dto: {}", customerDTO);
            log.info("degub entity: {}" ,customer);
        }
        return customerRepository.saveAndFlush(customer);

    }

    @Override
    @Transactional
    public CustomerDTO findCustomerByUser(UserDTO userDTO) {
        User user=maptoEntity(userDTO);
        Customer customer= customerRepository.findCustomerByUser(user);
        return maptoDTO(customer);
    }

    public CustomerDTO maptoDTO(Customer customer){
        return modelMapper.map(customer,CustomerDTO.class);
    }

    public Customer maptoEntity(CustomerDTO customerDTO){
        return modelMapper.map(customerDTO,Customer.class);
    }

    public User maptoEntity(UserDTO userDTO){
        return modelMapper.map(userDTO,User.class);
    }
}
