package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    public Cart findByClient_Id(Client client);
    public Optional<Cart> findByClient_IdAndState(Client client, String state);

}
