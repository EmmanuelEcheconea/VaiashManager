package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.request.CategoryRqDTO;
import com.vaiashmanager.db.dto.response.CategoryRsDTO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CategoryService {
    public List<CategoryRsDTO> retrieveAllCategory();
    public CategoryRsDTO createCategory(final CategoryRqDTO category);
    public CategoryRsDTO updateCategory(final Long idCategoria, final CategoryRqDTO category);
    public void deleteCategory(final Long idCategoria);

}
