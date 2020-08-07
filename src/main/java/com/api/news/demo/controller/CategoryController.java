package com.api.news.demo.controller;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.Category;
import com.api.news.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"/", "category"})
    String category(Model model) {
        ResultDTO resultDTO = categoryService.findAllCategory();
        model.addAttribute("listCategory", resultDTO.getLst());
        return "index";
    }

    @GetMapping(value = "/category/{id}")
    String findCategoryById(Model model, Long id) {
        ResultDTO resultDTO = categoryService.findCategoryById(id);
        model.addAttribute("categoryDetail", (Category) resultDTO.getObject());
        return "category";
    }
}
