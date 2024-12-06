package com.vaiashmanager.db.dto.request;

import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.entity.Product;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class CartProductRqDTO {
    private Long idProduct;
    private Long idCart;
    private Long idClient;
    private int amount;
    private String operation;
}
