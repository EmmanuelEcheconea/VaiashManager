package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class CartController {

    private CartService cartService;
    @Autowired
    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping("{idCart}")
    public ResponseEntity<?> updateCart(@PathVariable("idCart") final Long idCart, @RequestBody final Cart cart) {
        Cart response = this.cartService.updateCart(idCart, cart);
        return ResponseEntity.ok(response);
    }
}
