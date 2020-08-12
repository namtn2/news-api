package com.api.news.demo.repository;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    ResultDTO createOrUpdateUser(User user);

    ResultDTO searchUser(User user);

    ResultDTO findUserById(List<Long> id);

    List<User> searchUserByNameAndEmail(User user);
}
