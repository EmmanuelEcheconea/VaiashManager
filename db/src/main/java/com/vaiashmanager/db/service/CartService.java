package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.request.CartRqDTO;
import com.vaiashmanager.db.dto.response.CartRsDTO;
import com.vaiashmanager.db.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public interface CartService {
    public CartRsDTO updateCart(final Long idCart, final CartRqDTO cart);


}
