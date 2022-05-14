package com.ldp.assignment.ldp.services;

import com.ldp.assignment.ldp.dao.UserRepository;
import com.ldp.assignment.ldp.dto.UserDTO;
import com.ldp.assignment.ldp.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO getById(String id) {
        User user=userRepository.getById(id);
        return modelMapper.map(user,UserDTO.class);
    }


    @Override
    public User save(UserDTO userDTO) {
        User user= modelMapper.map(userDTO,User.class);
        return userRepository.saveAndFlush(user);

    }
}
