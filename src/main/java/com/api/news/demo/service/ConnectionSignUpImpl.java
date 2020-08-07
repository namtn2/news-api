//package com.api.news.demo.service;
//
//import com.api.news.demo.model.User;
//import com.api.news.demo.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionSignUp;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ConnectionSignUpImpl implements ConnectionSignUp {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Override
//    public String execute(Connection<?> connection) {
//        User u = userRepository.createUserSocial(connection);
//        return u.getName();
//    }
//}
