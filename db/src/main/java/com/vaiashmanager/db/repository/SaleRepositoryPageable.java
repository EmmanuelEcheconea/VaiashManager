package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Sale;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SaleRepositoryPageable extends PagingAndSortingRepository<Sale, Long> {
}
