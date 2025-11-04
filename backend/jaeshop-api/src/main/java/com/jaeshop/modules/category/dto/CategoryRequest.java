package com.jaeshop.modules.category.dto;

import lombok.Getter;

@Getter
public class CategoryRequest {
    private Long parentId;
    private String name;
    private Integer sortOrder;
    private Boolean isActive;
}
