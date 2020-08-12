package com.api.news.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Column(name = "ID")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "PHONE")
    private Long phone;

    @Column(name = "PASSWORD")
    private String password;
    
    @Transient
    private boolean rememberMe;
    
    @Transient
    private String token;
    
    @Transient
    private String refreshToken;

    public User(String email, String jwt) {
        this.email = email;
        this.token = jwt;
    }

    public User(String email) {
        this.email = email;
    }
}
