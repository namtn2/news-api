package com.api.news.demo.controller;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.News;
import com.api.news.demo.service.CategoryService;
import com.api.news.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FreemarkerController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    NewsService newService;

    @GetMapping(value = {"/", "category"})
    String category(Model model) {

        setAttributeCategoryAndNews(model);

        return "category";
    }

    @GetMapping(value = {"/category/{categoryId}"})
    String newsByCategoryId(Model model, @PathVariable Long categoryId) {
        ResultDTO resultDTO = newService.findAllNewsByCategoryId(categoryId);
        model.addAttribute("listNews", resultDTO.getLst());

        setAttributeCategoryAndNews(model);

        return "category-detail";
    }

    @GetMapping(value = {"/news/{id}"})
    String newsDetail(Model model, @PathVariable Long id) {
        ResultDTO resultDTO = newService.findNewsById(id);
        model.addAttribute("newsDetail", resultDTO.getObject() == null ? new Object() : resultDTO.getObject());

        setAttributeCategoryAndNews(model);

        return "news";
    }

    private void setAttributeCategoryAndNews(Model model) {
        ResultDTO resultDTO1 = categoryService.findAllCategory();
        model.addAttribute("listCategory", resultDTO1.getLst());

        ResultDTO resultDTO2 = newService.findAllNews();
        model.addAttribute("listNewsAll", resultDTO2.getLst());
    }
}
