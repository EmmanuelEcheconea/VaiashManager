package com.vaiashmanager.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SaleDetailFiltersRq {
    private Long id;
    private Long idVenta;
    private Long idProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double sumaTotal;
}
