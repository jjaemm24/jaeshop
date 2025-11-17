package com.jaeshop.modules.product.service;

import com.jaeshop.global.exception.CustomException;
import com.jaeshop.global.exception.ErrorCode;
import com.jaeshop.global.response.PageResponse;
import com.jaeshop.modules.category.mapper.CategoryMapper;
import com.jaeshop.modules.image.domain.ProductImage;
import com.jaeshop.modules.image.dto.ProductImageResponse;
import com.jaeshop.modules.image.mapper.ProductImageMapper;
import com.jaeshop.modules.product.domain.Product;
import com.jaeshop.modules.product.dto.ProductRequest;
import com.jaeshop.modules.product.dto.ProductResponse;
import com.jaeshop.modules.product.dto.ProductSearchRequest;
import com.jaeshop.modules.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final ProductImageMapper productImageMapper;

    @Override
    public Long createProduct(ProductRequest req) {

        // 1) 카테고리 존재 확인
        if (categoryMapper.findById(req.getCategoryId()) == null) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        // 2) 모델코드 생성 (null or "" 일 때)
        String modelCode = req.getModelCode();
        if (modelCode == null || modelCode.isBlank()) {
            modelCode = generateModelCode(req.getBrand());
        }

        // 3) 모델코드 중복 체크
        if (productMapper.findByModelCode(modelCode) != null) {
            throw new CustomException(ErrorCode.DUPLICATED_MODEL_CODE);
        }

        // 4) 상품 생성 (기본 status = active)
        Product product = Product.builder()

                .categoryId(req.getCategoryId())
                .name(req.getName())
                .description(req.getDescription())
                .originalPrice(req.getOriginalPrice())
                .salePrice(req.getSalePrice())
                .stock(req.getStock())
                .status("active")
                .brand(req.getBrand())
                .modelCode(modelCode)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        productMapper.save(product);
        return product.getId();
    }

    @Override
    public ProductResponse getProduct(Long id) {
        Product product = productMapper.findById(id);

        if (product == null) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return toResponse(product);
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productMapper.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateProduct(Long id, ProductRequest req) {
        Product product = productMapper.findById(id);
        if (product == null) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        product.update(
                req.getName(),
                req.getDescription(),
                req.getOriginalPrice(),
                req.getSalePrice(),
                req.getStock(),
                product.getStatus(),   // 상태 유지
                req.getBrand(),
                req.getModelCode() != null && !req.getModelCode().isBlank()
                        ? req.getModelCode() : product.getModelCode()
        );

        productMapper.update(product);
    }

    @Override
    public void deleteProduct(Long id) {
        if (productMapper.findById(id) == null) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // 실제 삭제 (나중에 soft-delete로 바꿔도 됨)
        productMapper.delete(id);
    }

    @Override
    public PageResponse<ProductResponse> searchProducts(ProductSearchRequest req) {
        int total = productMapper.count(req);

        if (total == 0) {
            return PageResponse.empty(req.getPage(), req.getSize());
        }

        List<Product> products = productMapper.search(req);

        List<ProductResponse> content = products.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return PageResponse.of(content, req.getPage(), req.getSize(), total);
    }

    // ====== private helpers ======

    private String generateModelCode(String brand) {
        String prefix = (brand != null && !brand.isBlank()) ? brand.toUpperCase() : "GEN";
        String timestamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = UUID.randomUUID().toString().substring(0, 4).toUpperCase();

        return prefix + "-" + timestamp + "-" + random;
    }

    private ProductResponse toResponse(Product p) {

        // 🔹 해당 상품의 이미지 조회
        List<ProductImage> images = productImageMapper.findByProductId(p.getId());

        List<ProductImageResponse> imageResponses = images.stream()
                .map(i -> ProductImageResponse.builder()
                        .id(i.getId())
                        .url(i.getImageUrl())
                        .isThumbnail(i.getIsThumbnail())
                        .sortOrder(i.getSortOrder())
                        .build()
                ).toList();

        return ProductResponse.builder()
                .id(p.getId())
                .categoryId(p.getCategoryId())
                .name(p.getName())
                .description(p.getDescription())
                .originalPrice(p.getOriginalPrice())
                .salePrice(p.getSalePrice())
                .stock(p.getStock())
                .status(p.getStatus())
                .brand(p.getBrand())
                .modelCode(p.getModelCode())
                .images(imageResponses)
                .build();
    }

}
