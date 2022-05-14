package com.ldp.assignment.ldp.services;

import com.ldp.assignment.ldp.dto.UserDTO;
import com.ldp.assignment.ldp.entity.User;

public interface UserService {

    public UserDTO getById(String id);

    public User save(UserDTO userDTO);

}
