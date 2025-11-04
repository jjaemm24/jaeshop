package com.jaeshop.modules.product.mapper;

import com.jaeshop.modules.product.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    void save(Product product);

    Product findById(Long id);

    List<Product> findAll();

    void update(Product product);

    void delete(Long id);

    Product findByModelCode(@Param("modelCode") String modelCode);
}
