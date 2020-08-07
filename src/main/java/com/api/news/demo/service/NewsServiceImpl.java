package com.api.news.demo.service;

import com.api.news.demo.repository.NewsRepository;
import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.News;
import com.api.news.demo.utils.StringUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@Log4j
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Override
    public ResultDTO createOrUpdateNews(News news) {
        return newsRepository.createOrUpdateNews(news);
    }

    @Override
    public ResultDTO findAllNews() {
        return newsRepository.findAllNews();
    }

    @Override
    public ResultDTO findNewsById(Long id) {
        if (StringUtils.isLongNullOrZero(id)) {
            return new ResultDTO();
        }
        return newsRepository.findNewsById(id);
    }

    @Override
    public ResultDTO deleteNews(Long id) {
        if (StringUtils.isLongNullOrZero(id)) {
            return new ResultDTO();
        }
        return newsRepository.deleteNews(id);
    }

    @Override
    public ResultDTO findAllNewsByCategoryId(Long categoryId) {
        return newsRepository.findAllNewsByCategoryId(categoryId);
    }

    @Override
    public ResultDTO search(News news) {
        return newsRepository.search(news);
    }
}
