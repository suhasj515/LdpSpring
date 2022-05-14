package com.ldp.assignment.ldp.services;

import com.ldp.assignment.ldp.dto.AuthoritiesDTO;
import com.ldp.assignment.ldp.entity.Authorities;

public interface AuthoritiesService {
    public Authorities save(AuthoritiesDTO authoritiesDTO);
}
