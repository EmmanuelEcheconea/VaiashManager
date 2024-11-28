package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.CategoryFiltersRq;
import com.vaiashmanager.db.entity.Category;
import com.vaiashmanager.db.repository.CategoryRepository;
import com.vaiashmanager.db.repository.CategoryRepositoryPageable;
import com.vaiashmanager.db.service.CategoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private EntityManager entityManager;
    private CategoryRepositoryPageable categoryRepositoryPageable;

    @Autowired
    public CategoryServiceImpl(final CategoryRepository categoryRepository,
                               EntityManager entityManager,
                               CategoryRepositoryPageable categoryRepositoryPageable) {
        this.categoryRepository = categoryRepository;
        this.entityManager = entityManager;
        this.categoryRepositoryPageable = categoryRepositoryPageable;
    }

    @Override
    public List<Category> retrieveAllCategoria() {
        Iterable<Category> categoriaIterable = this.categoryRepository.findAll();
        List<Category> productos = StreamSupport.stream(categoriaIterable.spliterator(), false)
                .collect(Collectors.toList());
        return productos;
    }

    @Override
    public Category createCategoria(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategoria(Long idCategoria, Category category) {
        final Category retrieveProduct = this.categoryRepository.findById(idCategoria).get();
        if(category.getNombreCategoria() != null) {
            retrieveProduct.setNombreCategoria(category.getNombreCategoria());
        }

        final Category response = this.categoryRepository.save(retrieveProduct);
        return response;
    }

    @Override
    public void deleteCategoria(Long idCategoria) {
        final Category retrieveProduct = this.categoryRepository.findById(idCategoria).get();
        this.categoryRepository.delete(retrieveProduct);
    }

    @Override
    public Page<Category> getCategorias(Pageable pageable) {
        return this.categoryRepositoryPageable.findAll(pageable);
    }

    @Override
    public List<Category> filters(CategoryFiltersRq categoryFiltersRq) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        Root<Category> root = cq.from(Category.class);

        if (categoryFiltersRq != null) {
            List<Predicate> predicates = new ArrayList<>();
            if (categoryFiltersRq.getNombreCategoria() != null) {
                predicates.add(cb.equal(root.get("nombreCategoria"), categoryFiltersRq.getNombreCategoria()));
            }
            if (categoryFiltersRq.getId() > -1) {
                predicates.add(cb.equal(root.get("id"), categoryFiltersRq.getId()));
            }
            if (!predicates.isEmpty()) {
                cq.where(cb.and(predicates.toArray(new Predicate[0])));
            }
            return entityManager.createQuery(cq).getResultList();
        }
        return new ArrayList<>();
    }


}
