package com.api.news.demo.repository;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.News;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository {

    ResultDTO createOrUpdateNews(News news);

    ResultDTO findAllNews();

    ResultDTO search(News news);

    ResultDTO findNewsById(Long id);

    ResultDTO deleteNews(Long id);

    ResultDTO findAllNewsByCategoryId(Long categoryId);
}
