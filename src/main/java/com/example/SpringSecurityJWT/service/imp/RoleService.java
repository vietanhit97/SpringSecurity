package com.example.SpringSecurityJWT.service.imp;

import com.example.SpringSecurityJWT.entity.ERole;
import com.example.SpringSecurityJWT.entity.Roles;
import com.example.SpringSecurityJWT.reponsitory.RoleReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements com.example.SpringSecurityJWT.service.RoleService {
    @Autowired
    private RoleReponsitory roleReponsitory;
    @Override
    public Optional<Roles> findByRoleName(ERole roleName) {
        return roleReponsitory.findByRoleName(roleName);
    }
}
