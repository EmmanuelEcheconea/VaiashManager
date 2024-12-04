package com.vaiashmanager.db.service;

import com.vaiashmanager.db.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public interface CartService {
    public Cart updateCart(final Long idCart, final Cart cart);


}
