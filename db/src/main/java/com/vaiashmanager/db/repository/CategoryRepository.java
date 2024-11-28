package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
