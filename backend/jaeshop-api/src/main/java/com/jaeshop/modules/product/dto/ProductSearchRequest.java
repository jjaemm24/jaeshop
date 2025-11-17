package com.jaeshop.modules.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest {
    private String keyword;
    private Long categoryId;
    private String brand;
    private Integer minPrice;
    private Integer maxPrice;
    private String sort;  // new / price_asc / price_desc / name_asc / name_desc

    private Integer page = 1;
    private Integer size = 10;

    public int getOffset() {
        return (page - 1) * size;
    }
}
