package com.api.news.demo.controller;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.LogType;
import com.api.news.demo.model.News;
import com.api.news.demo.service.LogService;
import com.api.news.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Component
@RequestMapping("/news-api")
public class NewsController {

    @Autowired
    NewsService newsService;
    
    @Autowired
    LogService logService;

    @PostMapping("/create")
    ResponseEntity<ResultDTO> create(@RequestBody News news) {
        ResultDTO resultDTO = newsService.createOrUpdateNews(news);
        logService.save(resultDTO, LogType.CREATE, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<ResultDTO> update(@RequestBody News news) {
        ResultDTO resultDTO = newsService.createOrUpdateNews(news);
        logService.save(resultDTO, LogType.UPDATE, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/list")
    ResponseEntity<ResultDTO> findAllNews() {
        ResultDTO resultDTO = newsService.findAllNews();
        logService.save(resultDTO, LogType.SEARCH, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/search")
    ResponseEntity<ResultDTO> search(@RequestBody(required = false) News news) {
        ResultDTO resultDTO = newsService.search(news);
        logService.save(resultDTO, LogType.SEARCH, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResultDTO> findNewsById(@PathVariable Long id) {
        ResultDTO resultDTO = newsService.findNewsById(id);
        logService.save(resultDTO, LogType.SEARCH, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResultDTO> delete(@PathVariable Long id) {
        ResultDTO resultDTO = newsService.deleteNews(id);
        logService.save(resultDTO, LogType.DELETE, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}
