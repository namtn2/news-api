package com.api.news.demo.controller;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.News;
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
public class NewsAPIController {

    @Autowired
    NewsService newsService;

    @PostMapping("/create")
    ResponseEntity<ResultDTO> create(@RequestBody News news) {
        ResultDTO resultDTO = newsService.createOrUpdateNews(news);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<ResultDTO> update(@RequestBody News news) {
        ResultDTO resultDTO = newsService.createOrUpdateNews(news);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/list")
    ResponseEntity<ResultDTO> findAllNews() {
        ResultDTO resultDTO = newsService.findAllNews();
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/search")
    ResponseEntity<ResultDTO> search(@RequestBody(required = false) News news) {
        ResultDTO resultDTO = newsService.search(news);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResultDTO> findNewsById(@PathVariable Long id) {
        ResultDTO resultDTO = newsService.findNewsById(id);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResultDTO> delete(@PathVariable Long id) {
        ResultDTO resultDTO = newsService.deleteNews(id);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}
