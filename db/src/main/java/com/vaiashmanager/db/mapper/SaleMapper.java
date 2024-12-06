package com.vaiashmanager.db.mapper;

import com.vaiashmanager.db.dto.request.SaleRqDTO;
import com.vaiashmanager.db.dto.response.SaleRsDTO;
import com.vaiashmanager.db.entity.Sale;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {
    public SaleRsDTO saleToSaleRsDTO(final Sale sale) {
        return SaleRsDTO.builder().estado(sale.getEstado()).totalVenta(sale.getTotalVenta()).fechaVenta(sale.getFechaVenta())
                .cart(sale.getCart()).id(sale.getId()).build();
    }


}
