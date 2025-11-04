package com.jaeshop.modules.category.service;

import com.jaeshop.global.exception.CustomException;
import com.jaeshop.global.exception.ErrorCode;
import com.jaeshop.modules.category.domain.Category;
import com.jaeshop.modules.category.dto.CategoryRequest;
import com.jaeshop.modules.category.dto.CategoryResponse;
import com.jaeshop.modules.category.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public void createCategory(CategoryRequest req) {
        Long parentId = req.getParentId();
        int level = 0;

        if (parentId != null) {
            Category parent = categoryMapper.findById(parentId);
            if (parent == null) {
                throw new CustomException(ErrorCode.INVALID_CATEGORY_PARENT);
            }
            level = parent.getLevel() + 1;
        }

        Category category = Category.builder()
                .parentId(parentId)
                .name(req.getName())
                .sortOrder(req.getSortOrder())
                .isActive(req.getIsActive())
                .level(level)
                .build();

        categoryMapper.save(category);
    }

    @Override
    public List<CategoryResponse> getCategoryTree() {
        List<Category> all = categoryMapper.findAll();

        // 1) Category -> CategoryResponse 변환
        Map<Long, CategoryResponse> map = all.stream()
                .collect(Collectors.toMap(Category::getId, c ->
                        CategoryResponse.builder()
                                .id(c.getId())
                                .parentId(c.getParentId())
                                .name(c.getName())
                                .level(c.getLevel())
                                .sortOrder(c.getSortOrder())
                                .isActive(c.getIsActive())
                                .children(new ArrayList<>())
                                .build()
                ));

        // 2) 루트 리스트 생성
        List<CategoryResponse> roots = new ArrayList<>();

        // 3) 트리 구성
        for (CategoryResponse node : map.values()) {
            if (node.getParentId() == null) {
                roots.add(node);
            } else {
                CategoryResponse parent = map.get(node.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }

        // 4) 루트 정렬
        roots.sort(Comparator.comparing(CategoryResponse::getSortOrder));

        return roots;
    }

    @Override
    @Transactional
    public void updateCategory(Long id, CategoryRequest req) {
        Category category = categoryMapper.findById(id);
        if (category == null) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        category.update(req.getName(), req.getSortOrder(), req.getIsActive());

        categoryMapper.update(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryMapper.findById(id);
        if (category == null) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        categoryMapper.delete(id);
    }
}
