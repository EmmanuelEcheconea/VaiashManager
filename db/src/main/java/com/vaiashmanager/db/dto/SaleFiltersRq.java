package com.vaiashmanager.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SaleFiltersRq {
    private Long id;
    private Long idCarro;
    private Date fechaVenta;
    private Double totalVenta;
    private boolean estado;
}
