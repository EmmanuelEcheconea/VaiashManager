package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.request.SaleFiltersRq;
import com.vaiashmanager.db.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SaleService {
    public List<Sale> retrieveAllSales();
    public Sale createSale(final Sale sale);
    public Sale updateSale(final Long idSale, final Sale sale);
    public void deleteSale(final Long idSale);
    public Page<Sale> getVentas(Pageable pageable);

    public List<Sale> filters(SaleFiltersRq saleFiltersRq);
}
