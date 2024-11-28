package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.CartFiltersRq;
import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    private CartService cartService;
    @Autowired
    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping("")
    public List<Cart> retrieveAllCart() {
        List<Cart> response = this.cartService.retrieveAllCart();
        return response;
    }

    @PostMapping("")
    public Cart createCart(@RequestBody final Cart Cart) {
        return this.cartService.createCart(Cart);
    }

    @PutMapping("{idCart}")
    public Cart updateCart(@PathVariable("idCart") final Long idCart, @RequestBody final Cart cart) {
        return this.cartService.updateCart(idCart, cart);
    }

    @DeleteMapping("{idCart}")
    public void deleteCart(@PathVariable("idCart") final Long idCart) {
        this.cartService.deleteCart(idCart);
    }

    @GetMapping("/carts")
    public Page<Cart> listCarts(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cartService.getCarts(pageable);
    }

    @PostMapping("/filters")
    public List<Cart> filterCart(@RequestBody CartFiltersRq cartFiltersRq) {
        return this.cartService.filters(cartFiltersRq);
    }

}
