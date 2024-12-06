package com.vaiashmanager.db.dto.response;

import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.enums.SaleState;
import lombok.*;

import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class SaleRsDTO {
    private Long id;
    private Cart cart;
    private Date fechaVenta;
    private Double totalVenta;
    private String estado;
}
