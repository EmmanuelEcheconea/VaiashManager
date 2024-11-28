package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.CategoryFiltersRq;
import com.vaiashmanager.db.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CategoryService {
    public List<Category> retrieveAllCategoria();
    public Category createCategoria(final Category category);
    public Category updateCategoria(final Long idCategoria, final Category category);
    public void deleteCategoria(final Long idCategoria);

    public Page<Category> getCategorias(Pageable pageable);

    public List<Category> filters(CategoryFiltersRq categoryFiltersRq);
}
