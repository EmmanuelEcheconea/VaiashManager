package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryPageable extends PagingAndSortingRepository<Product, Long> {
}
