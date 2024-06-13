package com.java.web.estateagency.service;

import java.util.Optional;
import java.util.Set;

import com.java.web.estateagency.entity.ERole;
import com.java.web.estateagency.entity.Role;

public interface RoleService {
    Role save(Role role);

    Set<Role> findALl();

    Optional<Role> findByNameRole(ERole name);
}
