package com.api.news.demo.controller;

import com.api.news.demo.model.Config;
import com.api.news.demo.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Component
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    ConfigService configService;

    @GetMapping("/group/{group}")
    ResponseEntity<List<Config>> findConfigByGroup(@PathVariable String group) {
        List<Config> lst = configService.findConfigByGroup(group);
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

}
