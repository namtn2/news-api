package com.api.news.demo.controller;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.LogModel;
import com.api.news.demo.model.LogType;
import com.api.news.demo.model.User;
import com.api.news.demo.service.LogService;
import com.api.news.demo.service.MyUserDetailsService;
import com.api.news.demo.service.UserService;
import com.api.news.demo.utils.Constants;
import com.api.news.demo.utils.JwtUtil;
import com.api.news.demo.utils.Utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@Component
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    LogService logService;

    @PostMapping("/login")
    ResponseEntity<ResultDTO> login(@RequestBody User user) {
        ResultDTO resultDTO = new ResultDTO();
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (Exception e) {
            resultDTO.setMessage("Login failed. Please check your email/password");
        }
        
        User u = null;
        if (authenticate != null) {
            Long time = null;
            if (user.isRememberMe()) {
                time = 1000 * 60 * 60 * 12l;
            }

            String jwt = jwtUtil.generateToken(user.getEmail(), time);
            String refreshToken = jwtUtil.createRefreshToken(user.getEmail());

            ResultDTO searchUser = userService.searchUser(user);
            u = (User) searchUser.getObject();
            u.setRememberMe(user.isRememberMe());
            u.setToken(jwt);
            u.setRefreshToken(refreshToken);

            resultDTO.setKey(Constants.RESULT.SUCCESS);
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setObject(u);
        }
        
        logService.save(u, u == null ? 0l : (u.getId() == null ? 0l : u.getId()), LogType.LOGIN, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/login-google")
    ResponseEntity<ResultDTO> loginGoogle(@RequestBody User user) {
        ResultDTO resultDTO = userService.loginGoogle(user.getToken());
        User u = (User) resultDTO.getObject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(u.getEmail());

        Long time = null;
        if (user.isRememberMe()) {
            time = 1000 * 60 * 60 * 12l;
        }

        String jwt = jwtUtil.generateToken(userDetails.getUsername(), time);
        u.setToken(jwt);
        String refreshToken = jwtUtil.createRefreshToken(userDetails.getUsername());
        u.setRefreshToken(refreshToken);

        resultDTO.setKey(Constants.RESULT.SUCCESS);
        resultDTO.setMessage(Constants.RESULT.SUCCESS);
        resultDTO.setObject(u);

        logService.save(u, u.getId(), LogType.LOGIN_WITH_GOOGLE, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/register-google")
    ResponseEntity<ResultDTO> registerGoogle(@RequestBody String token) {
        ResultDTO resultDTO = userService.registerGoogle(token);

        User u = (User) resultDTO.getObject();
        if (u.getId() != null) {
            resultDTO.setMessage("You already sign up with this email. Please sign up with another email or sign in with this email !");
            resultDTO.setKey(Constants.RESULT.FAIL);
        }

        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/g-captcha")
    ResponseEntity<ResultDTO> validateCaptcha(@RequestBody String token) {
        ResultDTO resultDTO = userService.validateCaptcha(token);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/logout")
    ResponseEntity<ResultDTO> logout(@RequestBody User user) {
        ResultDTO searchUser = userService.searchUser(user);
        User u = (User) searchUser.getObject();

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setKey(Constants.RESULT.SUCCESS);
        resultDTO.setMessage(Constants.RESULT.SUCCESS);

        logService.save(u, u.getId(), LogType.LOGOUT, resultDTO);

        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<ResultDTO> create(@RequestBody User user) {
        ResultDTO resultDTO = userService.createOrUpdateUser(user);
        User u = (User) resultDTO.getObject();
        logService.save(u, u.getId(), LogType.REGISTER, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    ResponseEntity<ResultDTO> refreshToken(@RequestBody User user) {
        ResultDTO resultDTO = new ResultDTO();

        //check log
        LogModel logModel = new LogModel();
        logModel.setEmail(user.getEmail());

        Query q = logService.prepareQuery(logModel, new Query());
        List<LogModel> search = logService.searchLog(0, 1, q);

        if (search != null && !search.isEmpty()) {
            logModel = search.get(0);
            if (Utils.diffDateGetMinutes(new Date(logModel.getCreateTime()), new Date()) < 11) {
                //check log if user still active in 10 minutes

                //create new token
                Map<String, String> map = jwtUtil.refreshToken(user.getRefreshToken());
                String newToken = map.get("token");
                String refreshToken = map.get("refreshToken");

                if (!Utils.isStringNullOrEmpty(newToken)) {
                    resultDTO.setKey(Constants.RESULT.SUCCESS);
                    resultDTO.setMessage(Constants.RESULT.SUCCESS);
                    resultDTO.setObject(Constants.RESULT.SUCCESS);

                    User u = new User();
                    u.setToken(newToken);
                    u.setRefreshToken(refreshToken);
                    u.setEmail(user.getEmail());
                    resultDTO.setObject(u);
                }
            }
        }

        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}
