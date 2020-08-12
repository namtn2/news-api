package com.api.news.demo.controller;

import com.api.news.demo.model.LogModel;
import com.api.news.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Component
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService logService;

    @PostMapping("/search")
        ResponseEntity<Page<LogModel>> search(@RequestBody LogModel logModel, @RequestParam("rowStart") int rowStart, @RequestParam("maxRow") int maxRow ) {
        Page<LogModel> search = logService.search(logModel, rowStart, maxRow);
        return new ResponseEntity<>(search, HttpStatus.OK);
    }
}
