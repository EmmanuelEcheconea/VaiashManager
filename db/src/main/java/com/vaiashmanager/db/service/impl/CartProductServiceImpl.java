package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.request.CartProductRqDTO;
import com.vaiashmanager.db.dto.response.CartProductRsDTO;
import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.entity.CartProduct;
import com.vaiashmanager.db.entity.Client;
import com.vaiashmanager.db.entity.Product;
import com.vaiashmanager.db.enums.CartError;
import com.vaiashmanager.db.enums.CartProductError;
import com.vaiashmanager.db.enums.CartProductState;
import com.vaiashmanager.db.enums.CartState;
import com.vaiashmanager.db.exception.CustomExceptionHandler;
import com.vaiashmanager.db.repository.CartProductRepository;
import com.vaiashmanager.db.repository.CartRepository;
import com.vaiashmanager.db.repository.ProductRepository;
import com.vaiashmanager.db.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CartProductServiceImpl implements CartProductService {

    private CartProductRepository cartProductRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;

    @Autowired
    public CartProductServiceImpl(CartProductRepository cartProductRepository,
                                  final ProductRepository productRepository,
                                  CartRepository cartRepository) {
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }
    @Override
    public void createCartProduct(CartProductRqDTO cartProductRqDTO) {
        Optional<Product> product =this.productRepository.findById(cartProductRqDTO.getIdProduct());
        if(product.isEmpty() || (product.get().getCantidadStock() - cartProductRqDTO.getAmount()) < 0) {
            throw new CustomExceptionHandler(CartProductError.OUT_OF_STOCK.getMessage(), CartProductError.OUT_OF_STOCK.getStatus());
        }
        Optional<Cart> cart = this.cartRepository.findByClient_IdAndState(Client.builder().id(cartProductRqDTO.getIdClient()).build(), CartState.ACTIVO.name());
        if(cart.isEmpty()) {
            throw new CustomExceptionHandler(CartError.PRODUCT_EMPTY.getMessage(), CartError.PRODUCT_EMPTY.getStatus());
        }
        Optional<CartProduct> cartProduct = this.cartProductRepository.findByIdCartAndStateAndIdProduct(cart.get(),
                CartProductState.PENDIENTE.name(), product.get());
        if(cartProduct.isPresent()) {
            if(cartProductRqDTO.getOperation().equals("SUMA")){
                cartProduct.get().setAmount(cartProduct.get().getAmount() + cartProductRqDTO.getAmount());
            }
            if(cartProductRqDTO.getOperation().equals("Resta")){
                cartProduct.get().setAmount(cartProduct.get().getAmount() - cartProductRqDTO.getAmount());
            }
            this.cartProductRepository.save(cartProduct.get());
        }else {
            this.cartProductRepository.save(CartProduct.builder().idCart(cart.get())
                    .idProduct(Product.builder().id(cartProductRqDTO.getIdProduct()).build()).state(CartProductState.PENDIENTE.name())
                    .amount(cartProductRqDTO.getAmount()).build());
            product.get().setCantidadStock(product.get().getCantidadStock() - cartProductRqDTO.getAmount());
            this.productRepository.save(product.get());
        }

    }
}
