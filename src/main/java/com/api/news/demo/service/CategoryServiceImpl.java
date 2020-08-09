package com.api.news.demo.service;

import com.api.news.demo.repository.CategoryRepository;
import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.Category;
import com.api.news.demo.repository.NewsRepository;
import com.api.news.demo.utils.Constants;
import com.api.news.demo.utils.StringUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@Log4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    NewsRepository newsRepository;

    @Override
    public ResultDTO createOrUpdateCategory(Category category) {
        return categoryRepository.createOrUpdateCategory(category);
    }

    @Override
    public ResultDTO findAllCategory() {
        return categoryRepository.findAllCategory();
    }

    @Override
    public ResultDTO findCategoryById(Long id) {
        if (StringUtils.isLongNullOrZero(id)) {
            return new ResultDTO();
        }
        return categoryRepository.findCategoryById(id);
    }

    @Override
    public ResultDTO deleteCategory(Long id) {
        ResultDTO resultDTO = new ResultDTO();
        if (StringUtils.isLongNullOrZero(id)) {
            return resultDTO;
        }
        //check category da co news hay chua
        ResultDTO resNews = newsRepository.findAllNewsByCategoryId(id);
        if (resNews != null && (resNews.getLst() != null && !resNews.getLst().isEmpty())) {
            resultDTO.setMessage("This category is already have news, can not delete this category !");
            return resultDTO;
        }
        return categoryRepository.deleteCategory(id);
    }

    @Override
    public ResultDTO search(Category category) {
        return categoryRepository.search(category);
    }
}
