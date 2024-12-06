package com.vaiashmanager.db.repository;

import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.entity.CartProduct;
import com.vaiashmanager.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByIdCartAndState(Cart cart, String state);
    Optional<CartProduct> findByIdCartAndStateAndIdProduct(Cart cart, String state, Product product);
}
