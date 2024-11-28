package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.SaleDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SaleDetailRepositoryPageable extends PagingAndSortingRepository<SaleDetail, Long> {
}
