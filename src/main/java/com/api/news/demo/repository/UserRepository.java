package com.api.news.demo.repository;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository {

//    ResultDTO login(User user);

    ResultDTO createOrUpdateUser(User user);

    ResultDTO searchUser(User user);

    ResultDTO findUserById(List<Long> id);

    List<User> searchUserByNameAndEmail(User user);
}
