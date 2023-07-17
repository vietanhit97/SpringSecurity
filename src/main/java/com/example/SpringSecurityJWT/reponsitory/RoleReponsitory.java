package com.example.SpringSecurityJWT.reponsitory;

import com.example.SpringSecurityJWT.entity.ERole;
import com.example.SpringSecurityJWT.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleReponsitory extends JpaRepository<Roles,Integer> {

    Optional<Roles> findByRoleName(ERole roleName);
}
