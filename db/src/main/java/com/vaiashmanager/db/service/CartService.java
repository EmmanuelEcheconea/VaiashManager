package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.CartFiltersRq;
import com.vaiashmanager.db.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CartService {
    public List<Cart> retrieveAllCart();
    public Cart createCart(final Cart cart);
    public Cart updateCart(final Long idCart, final Cart cart);
    public void deleteCart(final Long idCart);
    public Page<Cart> getCarts(Pageable pageable);

    public List<Cart> filters(CartFiltersRq cartFiltersRq);

}
