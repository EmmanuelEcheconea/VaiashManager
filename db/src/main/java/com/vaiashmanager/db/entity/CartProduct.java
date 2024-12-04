package com.vaiashmanager.db.entity;

import com.vaiashmanager.db.enums.CartProductState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_product")
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product idProduct;
    @ManyToOne
    @JoinColumn(name = "id_carro")
    private Cart idCart;
    private CartProductState state;
}
