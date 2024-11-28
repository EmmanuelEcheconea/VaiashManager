package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CategoryRepositoryPageable extends PagingAndSortingRepository<Category, Long> {
}
