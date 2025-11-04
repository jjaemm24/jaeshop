package com.jaeshop.modules.category.service;

import com.jaeshop.modules.category.dto.CategoryRequest;
import com.jaeshop.modules.category.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    void createCategory(CategoryRequest request);

    List<CategoryResponse> getCategoryTree();

    void updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}
