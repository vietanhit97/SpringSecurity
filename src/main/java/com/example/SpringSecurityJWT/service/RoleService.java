package com.example.SpringSecurityJWT.service;

import com.example.SpringSecurityJWT.entity.ERole;
import com.example.SpringSecurityJWT.entity.Roles;
import org.springframework.stereotype.Service;

import java.util.Optional;
public interface RoleService {
    Optional<Roles> findByRoleName(ERole roleName);
}
