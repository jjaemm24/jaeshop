package com.jaeshop.modules.image.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductImageResponse {
    private Long id;
    private String url;
    private Boolean isThumbnail;
    private Integer sortOrder;
}