package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.request.CartProductRqDTO;
import com.vaiashmanager.db.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cartProduct")
public class CartProductController {

    private CartProductService cartProductService;

    @Autowired
    public CartProductController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }


    @PostMapping("")
    public void cartProductCreate(@RequestBody final CartProductRqDTO cartProductRqDTO) {
        this.cartProductService.createCartProduct(cartProductRqDTO);
    }
}
