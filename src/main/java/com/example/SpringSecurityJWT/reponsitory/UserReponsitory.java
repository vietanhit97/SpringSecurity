package com.example.SpringSecurityJWT.reponsitory;

import com.example.SpringSecurityJWT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReponsitory extends JpaRepository<User,Integer> {
       User findByUserName(String userName);
       Boolean existsByUserName(String userName);
}
