package com.jaeshop.modules.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProductImageService {

    String uploadProductImage(Long productId, MultipartFile file);
}
