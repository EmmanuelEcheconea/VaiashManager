package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.request.CategoryRqDTO;
import com.vaiashmanager.db.dto.response.CategoryRsDTO;
import com.vaiashmanager.db.entity.Category;
import com.vaiashmanager.db.enums.CategoryError;
import com.vaiashmanager.db.exception.CustomExceptionHandler;
import com.vaiashmanager.db.mapper.CategoryMapper;
import com.vaiashmanager.db.repository.CategoryRepository;
import com.vaiashmanager.db.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(final CategoryRepository categoryRepository,final CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> retrieveAllCategoria() {
        Iterable<Category> categoryIterable = this.categoryRepository.findAll();
        if(!categoryIterable.iterator().hasNext()) {
            return new ArrayList<>();
        }
        return StreamSupport.stream(categoryIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Category createCategoria(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public CategoryRsDTO updateCategoria(Long idCategoria, CategoryRqDTO category) {
        final Optional<Category> retrieveProduct = this.categoryRepository.findById(idCategoria);
        if(retrieveProduct.isEmpty()) {
            throw new CustomExceptionHandler(CategoryError.NOT_FOUND.getMessage(), CategoryError.NOT_FOUND.getStatus());
        }
        if(category.getNombreCategoria() != null) {
            retrieveProduct.get().setNombreCategoria(category.getNombreCategoria());
        }
        return this.categoryMapper.categoryToCategoryRsDTO(this.categoryRepository.save(retrieveProduct.get()));
    }

    @Override
    public void deleteCategoria(Long idCategoria) {
        final Optional<Category> retrieveProduct = this.categoryRepository.findById(idCategoria);
        if(retrieveProduct.isEmpty()) {
            throw new CustomExceptionHandler(CategoryError.NOT_FOUND.getMessage(), CategoryError.NOT_FOUND.getStatus());
        }
        this.categoryRepository.delete(retrieveProduct.get());
    }

}
