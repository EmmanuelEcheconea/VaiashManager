package com.vaiashmanager.db.mapper;

import com.vaiashmanager.db.dto.response.CartRsDTO;
import com.vaiashmanager.db.entity.Cart;
import org.springframework.stereotype.Component;

@Component

public class CartMapper {

    public CartRsDTO cartToCartRsDTO(final Cart cart) {
        return CartRsDTO.builder().id(cart.getId()).state(cart.getState()).client(cart.getClient()).
                fechaCreacion(cart.getFechaCreacion()).build();
    }
}
