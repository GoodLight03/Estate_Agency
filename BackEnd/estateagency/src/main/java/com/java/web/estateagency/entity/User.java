package com.java.web.estateagency.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="username",unique = true)
    private String username;

    @Column(name="email",unique = true)
    private String email;

    private String fullname;

    private String password;

    private String state;

    private String address;

    private String phone;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String img;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
