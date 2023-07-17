package com.example.SpringSecurityJWT.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @Column(name = "RoleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    @Column(name = "RoleName",unique = true)
    @Enumerated(EnumType.STRING)
    private ERole roleName;
}
