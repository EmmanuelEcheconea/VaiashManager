package com.vaiashmanager.db.mapper;

import com.vaiashmanager.db.dto.request.CategoryRqDTO;
import com.vaiashmanager.db.dto.response.CategoryRsDTO;
import com.vaiashmanager.db.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryRsDTO categoryToCategoryRsDTO(final Category category) {
        return CategoryRsDTO.builder().id(category.getId()).nombreCategoria(category.getNombreCategoria()).build();
    }

    public Category categoryRqDTOToCategory(final CategoryRqDTO categoryRqDTO) {
        return Category.builder().nombreCategoria(categoryRqDTO.getNombreCategoria()).build();
    }

}
