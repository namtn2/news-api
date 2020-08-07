package com.api.news.demo.service;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    ResultDTO createOrUpdateCategory(Category category);

    ResultDTO findAllCategory();

    ResultDTO search(Category category);

    ResultDTO findCategoryById(Long id);

    ResultDTO deleteCategory(Long id);
}
