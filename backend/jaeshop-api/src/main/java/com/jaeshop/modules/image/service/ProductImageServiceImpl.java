package com.jaeshop.modules.image.service;

import com.jaeshop.global.exception.CustomException;
import com.jaeshop.global.exception.ErrorCode;
import com.jaeshop.modules.image.domain.ProductImage;
import com.jaeshop.modules.image.mapper.ProductImageMapper;
import com.jaeshop.modules.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ImageUploadService imageUploadService;
    private final ProductImageMapper productImageMapper;
    private final ProductMapper productMapper;


    @Override
    public String uploadProductImage(Long productId, MultipartFile file) {

        if (productMapper.findById(productId) == null) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        String imageUrl = imageUploadService.upload(file);

        ProductImage image = ProductImage.builder()
                .productId(productId)
                .imageUrl(imageUrl)
                .isThumbnail(false)
                .sortOrder(0)
                .build();

        productImageMapper.save(image);

        return imageUrl;
    }
}
