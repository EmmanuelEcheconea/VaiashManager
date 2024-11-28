package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SaleRepository extends CrudRepository<Sale, Long> {
}
