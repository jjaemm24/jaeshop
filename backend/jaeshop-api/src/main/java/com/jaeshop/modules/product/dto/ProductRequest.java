package com.jaeshop.modules.product.dto;

import lombok.Getter;

@Getter
public class ProductRequest {
    private Long categoryId;
    private String name;
    private String description;
    private Integer originalPrice;
    private Integer salePrice;
    private Integer stock;
    private String brand;
    private String modelCode;
}
