package com.ldp.assignment.ldp.services;

import com.ldp.assignment.ldp.dao.AuthoritiesRepository;
import com.ldp.assignment.ldp.dto.AuthoritiesDTO;
import com.ldp.assignment.ldp.entity.Authorities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Override
    public Authorities save(AuthoritiesDTO authoritiesDTO) {
        Authorities authorities= maptoEntity(authoritiesDTO);
        return authoritiesRepository.saveAndFlush(authorities);
    }

    public Authorities maptoEntity(AuthoritiesDTO authoritiesDTO){
        return modelMapper.map(authoritiesDTO,Authorities.class);
    }
}
