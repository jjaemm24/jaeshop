package com.jaeshop.modules.image.mapper;

import com.jaeshop.modules.image.domain.ProductImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductImageMapper {

    void save(ProductImage image);

    List<ProductImage> findByProductId(Long productId);

}
