package com.vaiashmanager.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CartFiltersRq {
    private Long id;
    private Long idCliente;
    private LocalDateTime fechaCreacion;
    private Boolean estado;
}
