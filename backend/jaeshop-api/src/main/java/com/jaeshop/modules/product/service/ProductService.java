package com.jaeshop.modules.product.service;

import com.jaeshop.modules.product.dto.ProductRequest;
import com.jaeshop.modules.product.dto.ProductResponse;

import java.util.List;


public interface ProductService {

    Long createProduct(ProductRequest req);

    ProductResponse getProduct(Long id);

    List<ProductResponse> getProducts();

    void updateProduct(Long id, ProductRequest req);

    void deleteProduct(Long id);
}