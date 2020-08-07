package com.api.news.demo.controller;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NewsController {

    @Autowired
    NewsService newService;

//    @GetMapping(value={"/news"})
//    String news(Model model) {
//        ResultDTO resultDTO = newService.findAllNews();
//        model.addAttribute("list-news",((User) resultDTO.getLst().get(0)).getEmail());
//        return "news";
//    }

    @GetMapping(value = {"/news/{categoryId}"})
    String news(Model model, @PathVariable Long categoryId) {
        ResultDTO resultDTO = newService.findAllNewsByCategoryId(categoryId);
        model.addAttribute("list-news", resultDTO.getLst());
        return "news";
    }

//    @GetMapping(value = {"/news/{id}"})
//    String newsDetail(Model model, @PathVariable Long id) {
//        ResultDTO resultDTO = newService.findNewsById(id);
//        model.addAttribute("news-detail", resultDTO.getObject());
//        return "news/{id}";
//    }
}
