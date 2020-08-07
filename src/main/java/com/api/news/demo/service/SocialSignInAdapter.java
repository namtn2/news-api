//package com.api.news.demo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.web.SignInAdapter;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.NativeWebRequest;
//
//@Service
//public class SocialSignInAdapter implements SignInAdapter {
//
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    @Override
//    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
//        final UserDetails user = userDetailsService.loadUserByUsername(userId);
//        final Authentication newAuth = new UsernamePasswordAuthenticationToken(
//                user,
//                null,
//                user.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(newAuth);
//        return "http:localhost:8000/#/";
//    }
//}