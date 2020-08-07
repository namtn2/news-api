package com.api.news.demo.service;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.News;
import org.springframework.stereotype.Service;

@Service
public interface NewsService {

    ResultDTO createOrUpdateNews(News news);

    ResultDTO findAllNews();

    ResultDTO search(News news);

    ResultDTO findNewsById(Long id);

    ResultDTO deleteNews(Long id);

    ResultDTO findAllNewsByCategoryId(Long categoryId);
}
