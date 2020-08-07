package com.api.news.demo.controller;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.User;
import com.api.news.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@Component
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    ResponseEntity<ResultDTO> login(@RequestBody User user, Principal principal) {
        ResultDTO resultDTO = userService.login(user);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/login/{token}")
    ResponseEntity<ResultDTO> loginGoogle(@PathVariable String token) {
        ResultDTO resultDTO = userService.loginGoogle(token);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/register/{token}")
    ResponseEntity<ResultDTO> registerGoogle(@PathVariable String token) {
        ResultDTO resultDTO = userService.registerGoogle(token);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/g-captcha/{token}")
    ResponseEntity<ResultDTO> validateCaptcha(@PathVariable String token) {
        ResultDTO resultDTO = userService.validateCaptcha(token);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<ResultDTO> create(@RequestBody User user) {
        ResultDTO resultDTO = userService.createOrUpdateUser(user);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<ResultDTO> update(@RequestBody User user) {
        ResultDTO resultDTO = userService.createOrUpdateUser(user);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

//    @GetMapping("/list")
//    ResponseEntity<ResultDTO> findAllUser() {
//        ResultDTO resultDTO = userService.findAllUser();
//        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    ResponseEntity<ResultDTO> findUserById(@PathVariable Long id) {
        ResultDTO resultDTO = userService.findUserById(id);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}
