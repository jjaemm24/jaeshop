package com.jaeshop.modules.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private Integer originalPrice;
    private Integer salePrice;
    private Integer stock;
    private String status;
    private String brand;
    private String modelCode;
}
