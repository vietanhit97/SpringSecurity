package com.example.SpringSecurityJWT.controller;

import com.example.SpringSecurityJWT.entity.ERole;
import com.example.SpringSecurityJWT.entity.Roles;
import com.example.SpringSecurityJWT.entity.User;
import com.example.SpringSecurityJWT.payload.request.LoginRequest;
import com.example.SpringSecurityJWT.payload.request.SingUpRequest;
import com.example.SpringSecurityJWT.payload.response.JwtReponse;
import com.example.SpringSecurityJWT.payload.response.MessageReponse;
import com.example.SpringSecurityJWT.security.CustomUserDetail;
import com.example.SpringSecurityJWT.service.RoleService;
import com.example.SpringSecurityJWT.service.UserService;
import com.example.SpringSecurityJWT.token.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;
    @PostMapping("/singup")
    public ResponseEntity<?> registerUser(@RequestBody SingUpRequest singUpRequest){
        if(userService.exitsByUserName(singUpRequest.getUserName())){
            return ResponseEntity.badRequest().body(new MessageReponse("ten da ton tai"));
        }
        User user = new User();
        user.setUserName(singUpRequest.getUserName());
        user.setPassWord(encoder.encode(singUpRequest.getPassword()));
        Set<String> strRoles = singUpRequest.getRoles();
        Set<Roles> roles = new HashSet<>();
        if(strRoles==null){
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(()-> new RuntimeException("role khong ton tai"));
            roles.add(userRole);
        }else {
            strRoles.forEach(role->{
                switch (role){
                    case "admin" :
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN).orElseThrow(()->new RuntimeException("role is not found"));
                        roles.add(adminRole);
                    case "user" :
                        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("role is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userService.saveOrAdd(user);
        return ResponseEntity.ok(new MessageReponse("Dang ky thanh cong !!"));
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(customUserDetail);
            List<String> listRole = customUserDetail.getAuthorities()
                    .stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            JwtReponse jwtResponse = new JwtReponse(jwt, customUserDetail.getUsername(), listRole);
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthenticationException e) {
            // Xử lý lỗi xác thực không thành công
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
