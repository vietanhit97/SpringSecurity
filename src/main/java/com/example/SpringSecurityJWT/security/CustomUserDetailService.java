package com.example.SpringSecurityJWT.security;


import com.example.SpringSecurityJWT.entity.User;
import com.example.SpringSecurityJWT.reponsitory.UserReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserReponsitory userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user==null){
            throw  new UsernameNotFoundException("người dùng không tồn tại");
        }
        return new CustomUserDetail(user);
    }
}
