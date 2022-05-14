package com.ldp.assignment.ldp.services;

import com.ldp.assignment.ldp.dto.UserDTO;
import com.ldp.assignment.ldp.entity.Manager;

public interface ManagerService {

    public Manager findManagerByUser(UserDTO userDTO);
    public Manager getById(int id);
}
