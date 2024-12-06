package com.vaiashmanager.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "producto")
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    @Column(name = "cantidad_stock")
    private int cantidadStock;
    @ManyToOne
    @JoinColumn(name = "categoria")
    private Category category;
}
