package com.ldp.assignment.ldp.services;

import com.ldp.assignment.ldp.dao.ManagerRepository;
import com.ldp.assignment.ldp.dto.UserDTO;
import com.ldp.assignment.ldp.entity.Manager;
import com.ldp.assignment.ldp.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService{


    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Manager findManagerByUser(UserDTO userDTO) {
        User user=modelMapper.map(userDTO,User.class);
        return managerRepository.findManagerByUser(user);
    }


    @Override
    public Manager getById(int id) {
        return managerRepository.getById(id);
    }
}
