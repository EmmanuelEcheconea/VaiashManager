package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.request.CartRqDTO;
import com.vaiashmanager.db.dto.response.CartRsDTO;
import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.enums.CartError;
import com.vaiashmanager.db.exception.CustomExceptionHandler;
import com.vaiashmanager.db.mapper.CartMapper;
import com.vaiashmanager.db.repository.CartRepository;
import com.vaiashmanager.db.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    private CartMapper cartMapper;
    @Autowired
    public CartServiceImpl(final CartRepository cartRepository, final CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartRsDTO updateCart(final Long idCart, final CartRqDTO cart) {
        final Optional<Cart> retrieveCart = this.cartRepository.findById(idCart);
        if( retrieveCart.isEmpty()) {
            throw new CustomExceptionHandler(CartError.NOT_FOUND.getMessage(), CartError.NOT_FOUND.getStatus());
        }
        if(cart.getState() != null) {
            retrieveCart.get().setState(cart.getState());
        }
        return this.cartMapper.cartToCartRsDTO(this.cartRepository.save(retrieveCart.get()));
    }
}
