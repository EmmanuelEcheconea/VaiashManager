package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.SaleDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SaleDetailRepository extends CrudRepository<SaleDetail, Long> {
}
