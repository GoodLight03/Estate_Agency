package com.java.web.estateagency.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.ERole;
import com.java.web.estateagency.entity.Role;
import com.java.web.estateagency.repository.RoleRepository;
import com.java.web.estateagency.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role save(Role roleDto) {
        Role rl=new Role();
        rl.setName(roleDto.getName());
        return roleRepository.save(rl);
    }

    @Override
    public Set<Role> findALl() {
        // TODO Auto-generated method stub
        return roleRepository.findALl();
    }

    @Override
    public Optional<Role> findByNameRole(ERole name) {
        return roleRepository.findByName(name);
    }
     
}
