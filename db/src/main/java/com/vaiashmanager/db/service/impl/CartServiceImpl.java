package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.repository.CartRepository;
import com.vaiashmanager.db.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart updateCart(final Long idCart, final Cart cart) {
        final Cart retrieveCart = this.cartRepository.findById(idCart).get();
        if(cart.getClient() != null) {
            retrieveCart.setClient(cart.getClient());
        }
        if(cart.getState() != null) {
            retrieveCart.setState(cart.getState());
        }
        return this.cartRepository.save(retrieveCart);
    }
}
