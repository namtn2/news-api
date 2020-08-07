package com.api.news.demo.service;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.repository.UserRepository;
import com.api.news.demo.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.api.news.demo.model.User uSearch = new com.api.news.demo.model.User();
        uSearch.setEmail(email);
        ResultDTO resultDTO = userRepository.searchUser(uSearch);
        if (Constants.RESULT.SUCCESS.equals(resultDTO.getKey()) && resultDTO.getObject() != null) {
            com.api.news.demo.model.User u = (com.api.news.demo.model.User) resultDTO.getObject();
            return new User(u.getEmail(), u.getPassword(), new ArrayList<>());
        }
        return null;
    }
}
