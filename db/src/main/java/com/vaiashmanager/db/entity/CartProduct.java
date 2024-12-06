package com.vaiashmanager.db.entity;

import com.vaiashmanager.db.enums.CartProductState;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_product")
@ToString
@Builder
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product idProduct;
    @ManyToOne
    @JoinColumn(name = "id_cart")
    private Cart idCart;
    private String state;
    private int amount;
}
