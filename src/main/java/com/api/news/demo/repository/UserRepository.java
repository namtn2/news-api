package com.api.news.demo.repository;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.User;
//import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    ResultDTO login(User user);

    ResultDTO createOrUpdateUser(User user);

    ResultDTO searchUser(User user);

    ResultDTO findUserById(Long id);

//    User createUserSocial(Connection<?> connection);
}
