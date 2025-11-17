package com.jaeshop.modules.image.controller;

import com.jaeshop.global.response.ApiResponse;
import com.jaeshop.modules.image.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping("/{productId}/images")
    public ApiResponse<?> uploadImage(
            @PathVariable Long productId,
            @RequestPart("file") MultipartFile file
    ) {
        String url = productImageService.uploadProductImage(productId, file);
        return ApiResponse.ok(url);
    }

}
