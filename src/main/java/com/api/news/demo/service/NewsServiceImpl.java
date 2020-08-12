package com.api.news.demo.service;

import com.api.news.demo.repository.NewsRepository;
import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.LogType;
import com.api.news.demo.model.News;
import com.api.news.demo.utils.Constants;
import com.api.news.demo.utils.Utils;
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

    @Autowired
    LogService logService;

    @Override
    public ResultDTO createOrUpdateNews(News news) {
        ResultDTO resultDTO = newsRepository.createOrUpdateNews(news);
        if (Constants.RESULT.FAIL.equals(resultDTO.getKey())) {
//            logService.save(resultDTO, 0L, news.getId() == null ? LogType.CREATE : LogType.UPDATE);
            return resultDTO;
        }
//        logService.save(resultDTO, news.getId() == null ? ((News) resultDTO.getObject()).getId() : news.getId(), news.getId() == null ? LogType.CREATE : LogType.UPDATE);
        return resultDTO;
    }

    @Override
    public ResultDTO findAllNews() {
        return newsRepository.findAllNews();
    }

    @Override
    public ResultDTO findNewsById(Long id) {
        if (Utils.isLongNullOrZero(id)) {
            return new ResultDTO();
        }
        return newsRepository.findNewsById(id);
    }

    @Override
    public ResultDTO deleteNews(Long id) {
        ResultDTO resultDTO = new ResultDTO();
        if (Utils.isLongNullOrZero(id)) {
            return new ResultDTO();
        }
        resultDTO = newsRepository.deleteNews(id);
//        logService.save(resultDTO, id, LogType.DELETE);
        return resultDTO;
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
