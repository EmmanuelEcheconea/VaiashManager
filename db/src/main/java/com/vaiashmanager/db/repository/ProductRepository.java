package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    public Optional<Product> findByNombre(String nombre);
}
