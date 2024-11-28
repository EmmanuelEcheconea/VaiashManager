package com.vaiashmanager.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFiltersRq {
    private String nombre;
    private String description;
    private int id;
    private double precio;
    private int cantidad_stock;
    private String categoria;

}
