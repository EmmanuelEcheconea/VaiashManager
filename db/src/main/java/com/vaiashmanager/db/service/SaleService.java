package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.request.SaleFiltersRq;
import com.vaiashmanager.db.dto.request.SaleRqDTO;
import com.vaiashmanager.db.dto.response.SaleRsDTO;
import com.vaiashmanager.db.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SaleService {
    public List<SaleRsDTO> retrieveAllSales();
    public SaleRsDTO createSale(final SaleRqDTO sale);
    public void deleteSale(final Long idSale);
    public Page<SaleRsDTO> getVentas(Pageable pageable);

    public List<SaleRsDTO> filters(SaleFiltersRq saleFiltersRq);
}
