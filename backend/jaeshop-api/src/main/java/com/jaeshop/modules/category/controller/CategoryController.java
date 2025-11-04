package com.jaeshop.modules.category.controller;

import com.jaeshop.global.response.ApiResponse;
import com.jaeshop.modules.category.dto.CategoryRequest;
import com.jaeshop.modules.category.dto.CategoryResponse;
import com.jaeshop.modules.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<Void> create(@RequestBody CategoryRequest request) {
        categoryService.createCategory(request);
        return ApiResponse.ok();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getTree() {
        return ApiResponse.ok(categoryService.getCategoryTree());
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable Long id,
            @RequestBody CategoryRequest request
    ) {
        categoryService.updateCategory(id, request);
        return ApiResponse.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.ok();
    }

}
