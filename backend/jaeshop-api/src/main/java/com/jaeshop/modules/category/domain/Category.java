package com.jaeshop.modules.category.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Category {
    private Long id;
    private Long parentId;
    private String name;
    private Integer level;
    private Integer sortOrder;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(String name, Integer sortOrder, Boolean isActive) {
        this.name = name;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
    }
}
