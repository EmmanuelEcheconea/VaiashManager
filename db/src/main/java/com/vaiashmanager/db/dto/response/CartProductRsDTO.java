package com.vaiashmanager.db.dto.response;

import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.entity.Product;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductRsDTO {
    private Long id;
    private Product idProduct;
    private Cart idCart;
    private String state;
    private int amount;
}
