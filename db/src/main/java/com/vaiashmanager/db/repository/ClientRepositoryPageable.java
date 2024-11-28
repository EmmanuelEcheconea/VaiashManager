package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ClientRepositoryPageable extends PagingAndSortingRepository<Client, Long> {
}
