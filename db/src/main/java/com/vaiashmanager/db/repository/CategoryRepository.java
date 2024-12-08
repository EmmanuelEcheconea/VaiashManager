package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CategoryRepository extends CrudRepository<Category, Long> {
    public Optional<Category> findByNombreCategoria(final String nombreCategoria);
}
