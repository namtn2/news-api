package com.api.news.demo.service;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

//    ResultDTO login(User user);

    ResultDTO createOrUpdateUser(User user);

    ResultDTO loginGoogle(String token);

    ResultDTO registerGoogle(String token);

    ResultDTO validateCaptcha(String token);

//    ResultDTO findUserById(Long id);
    
    ResultDTO searchUser(User user);
}
