package com.example.SpringSecurityJWT.service;

import com.example.SpringSecurityJWT.entity.User;

import java.util.Optional;

public interface UserService {
    User getByUserName(String userName);
    Boolean exitsByUserName(String userName);
    User saveOrAdd(User user);
}
