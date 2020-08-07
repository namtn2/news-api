package com.api.news.demo.repository;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository {

    ResultDTO createOrUpdateCategory(Category category);

    ResultDTO findAllCategory();

    ResultDTO search(Category category);

    ResultDTO findCategoryById(Long id);

    ResultDTO deleteCategory(Long id);
}
