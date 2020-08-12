package com.api.news.demo.controller;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.Category;
import com.api.news.demo.model.LogType;
import com.api.news.demo.service.CategoryService;
import com.api.news.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Component
@RequestMapping("/category-api")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    LogService logService;
    
    @PostMapping("/create")
    ResponseEntity<ResultDTO> create(@RequestBody Category category) {
        ResultDTO resultDTO = categoryService.createOrUpdateCategory(category);
        logService.save(resultDTO, LogType.CREATE, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<ResultDTO> update(@RequestBody Category news) {
        ResultDTO resultDTO = categoryService.createOrUpdateCategory(news);
        logService.save(resultDTO, LogType.UPDATE, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/list")
    ResponseEntity<ResultDTO> findAllCategory() {
        ResultDTO resultDTO = categoryService.findAllCategory();
        logService.save(resultDTO, LogType.SEARCH, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/search")
    ResponseEntity<ResultDTO> search(@RequestBody(required = false) Category category) {
        ResultDTO resultDTO = categoryService.search(category);
        logService.save(resultDTO, LogType.SEARCH, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResultDTO> findCategoryById(@PathVariable Long id) {
        ResultDTO resultDTO = categoryService.findCategoryById(id);
        logService.save(resultDTO, LogType.SEARCH, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResultDTO> delete(@PathVariable Long id) {
        ResultDTO resultDTO = categoryService.deleteCategory(id);
        logService.save(resultDTO, LogType.DELETE, resultDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}
