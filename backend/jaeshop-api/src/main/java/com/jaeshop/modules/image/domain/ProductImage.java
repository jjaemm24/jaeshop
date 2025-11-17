package com.jaeshop.modules.image.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductImage {
    private Long id;
    private Long productId;
    private String imageUrl;
    private Boolean isThumbnail;
    private Integer sortOrder;
    private LocalDateTime createdAt;
}
