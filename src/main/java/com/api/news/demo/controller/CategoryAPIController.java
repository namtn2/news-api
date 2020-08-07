package com.api.news.demo.controller;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.Category;
import com.api.news.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Component
@RequestMapping("/category-api")
public class CategoryAPIController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    ResponseEntity<ResultDTO> create(@RequestBody Category category) {
        ResultDTO resultDTO = categoryService.createOrUpdateCategory(category);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<ResultDTO> update(@RequestBody Category news) {
        ResultDTO resultDTO = categoryService.createOrUpdateCategory(news);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/list")
    ResponseEntity<ResultDTO> findAllCategory() {
        ResultDTO resultDTO = categoryService.findAllCategory();
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @PostMapping("/search")
    ResponseEntity<ResultDTO> search(@RequestBody(required = false) Category category) {
        ResultDTO resultDTO = categoryService.search(category);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResultDTO> findCategoryById(@PathVariable Long id) {
        ResultDTO resultDTO = categoryService.findCategoryById(id);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResultDTO> delete(@PathVariable Long id) {
        ResultDTO resultDTO = categoryService.deleteCategory(id);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}
