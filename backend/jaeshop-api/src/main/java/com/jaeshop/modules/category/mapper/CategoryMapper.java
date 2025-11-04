package com.jaeshop.modules.category.mapper;

import com.jaeshop.modules.category.domain.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    void save(Category category);

    Category findById(Long id);

    List<Category> findAll();

    List<Category> findByParentId(@Param("parentId") Long parentId);

    void update(Category category);

    void delete(Long id);
}
