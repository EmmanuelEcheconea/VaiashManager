package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ClientRepository extends CrudRepository<Client, Long> {
}
