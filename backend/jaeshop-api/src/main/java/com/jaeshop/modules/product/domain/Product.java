package com.jaeshop.modules.product.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Product {
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(String name, String description, Integer originalPrice,
                       Integer salePrice, Integer stock, String status,
                       String brand, String modelCode) {
        this.name = name;
        this.description = description;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.stock = stock;
        this.status = status;
        this.brand = brand;
        this.modelCode = modelCode;
    }
}
