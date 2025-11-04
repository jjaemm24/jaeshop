package com.jaeshop.modules.product.controller;

import com.jaeshop.global.response.ApiResponse;
import com.jaeshop.modules.product.dto.ProductRequest;
import com.jaeshop.modules.product.dto.ProductResponse;
import com.jaeshop.modules.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping
    public ApiResponse<Long> create(@RequestBody ProductRequest request) {
        Long id = productService.createProduct(request);
        return ApiResponse.ok(id); // id 반환
    }

    // 상품 상세 조회
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> get(@PathVariable Long id) {
        return ApiResponse.ok(productService.getProduct(id));
    }

    // 상품 목록 조회
    @GetMapping
    public ApiResponse<List<ProductResponse>> getList() {
        return ApiResponse.ok(productService.getProducts());
    }

    // 상품 수정
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        productService.updateProduct(id, request);
        return ApiResponse.ok();
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.ok();
    }
}
