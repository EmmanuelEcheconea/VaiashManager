package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.request.CartProductRqDTO;
import com.vaiashmanager.db.dto.response.CartProductRsDTO;
import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.entity.CartProduct;
import com.vaiashmanager.db.entity.Client;
import com.vaiashmanager.db.entity.Product;
import com.vaiashmanager.db.enums.*;
import com.vaiashmanager.db.exception.CustomExceptionHandler;
import com.vaiashmanager.db.repository.CartProductRepository;
import com.vaiashmanager.db.repository.CartRepository;
import com.vaiashmanager.db.repository.ClientRepository;
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
    private ClientRepository clientRepository;
    @Autowired
    public CartProductServiceImpl(CartProductRepository cartProductRepository,
                                  final ProductRepository productRepository,
                                  CartRepository cartRepository,
                                  ClientRepository clientRepository) {
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.clientRepository = clientRepository;
    }
    @Override
    public void createCartProduct(CartProductRqDTO cartProductRqDTO) {
        Optional<Product> product =this.productRepository.findById(cartProductRqDTO.getIdProduct());
        if(product.isEmpty() || (product.get().getCantidadStock() - cartProductRqDTO.getAmount()) < 0) {
            throw new CustomExceptionHandler(ProductError.NOT_FOUND.getMessage(), ProductError.NOT_FOUND.getStatus());
        }

        Optional<Cart> cart = this.cartRepository.findById(cartProductRqDTO.getIdCart());
        if(cart.isEmpty()) {
            throw new CustomExceptionHandler(CartError.NOT_FOUND.getMessage(), CartError.NOT_FOUND.getStatus());
        }
        Optional<CartProduct> cartProduct = this.cartProductRepository.findByIdCartAndStateAndIdProduct(cart.get(),
                CartProductState.PENDIENTE.name(), product.get());
        if(cartProduct.isPresent()) {
            if(cartProductRqDTO.getOperation().isEmpty() || cartProductRqDTO.getOperation().trim().equals("")) {
                throw new CustomExceptionHandler(CartProductError.NO_OPERATION.getMessage(), CartProductError.NO_OPERATION.getStatus());
            }
            if(cartProductRqDTO.getOperation().equals("SUMA")){
                int suma = cartProduct.get().getAmount() + cartProductRqDTO.getAmount();
                cartProduct.get().setAmount(suma);
                if(product.get().getCantidadStock() - cartProductRqDTO.getAmount() < 0) {
                    throw new CustomExceptionHandler(ProductError.SIN_STOCK.getMessage(), ProductError.SIN_STOCK.getStatus());
                }
                product.get().setCantidadStock(product.get().getCantidadStock() - cartProductRqDTO.getAmount());
                this.productRepository.save(product.get());
            }
            if(cartProductRqDTO.getOperation().equals("RESTA")){
                int resta = cartProduct.get().getAmount() - cartProductRqDTO.getAmount();
                cartProduct.get().setAmount(resta);
                product.get().setCantidadStock(product.get().getCantidadStock() + cartProductRqDTO.getAmount());
                this.productRepository.save(product.get());
            }
            this.cartProductRepository.save(cartProduct.get());
        }else {
            CartProduct auto = CartProduct.builder()
                    .idCart(cart.get())
                    .idProduct(product.get())
                    .state(CartProductState.PENDIENTE.name())
                    .amount(cartProductRqDTO.getAmount())
                    .build();
            System.out.println(auto.getId() +"hola");
            this.cartProductRepository.save(auto);
            product.get().setCantidadStock(product.get().getCantidadStock() - cartProductRqDTO.getAmount());
            this.productRepository.save(product.get());
        }

    }
}
