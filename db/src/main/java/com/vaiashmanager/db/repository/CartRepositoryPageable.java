package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Cart;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CartRepositoryPageable extends PagingAndSortingRepository<Cart, Long> {
}
