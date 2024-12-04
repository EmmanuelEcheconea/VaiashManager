package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ClientRepository extends JpaRepository<Client, Long> {
}
