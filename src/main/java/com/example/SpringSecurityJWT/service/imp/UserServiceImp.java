package com.example.SpringSecurityJWT.service.imp;

import com.example.SpringSecurityJWT.entity.User;
import com.example.SpringSecurityJWT.reponsitory.UserReponsitory;
import com.example.SpringSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserReponsitory userReponsitory;

    @Override
    public User getByUserName(String userName) {
        return userReponsitory.findByUserName(userName);
    }

    @Override
    public Boolean exitsByUserName(String userName) {
        return userReponsitory.existsByUserName(userName);
    }

    @Override
    public User saveOrAdd(User user) {
        return userReponsitory.save(user);
    }
}
