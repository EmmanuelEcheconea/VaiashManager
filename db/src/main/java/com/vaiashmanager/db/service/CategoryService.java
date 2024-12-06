package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.request.CategoryRqDTO;
import com.vaiashmanager.db.dto.response.CategoryRsDTO;
import com.vaiashmanager.db.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CategoryService {
    public List<Category> retrieveAllCategoria();
    public Category createCategoria(final Category category);
    public CategoryRsDTO updateCategoria(final Long idCategoria, final CategoryRqDTO category);
    public void deleteCategoria(final Long idCategoria);

}
