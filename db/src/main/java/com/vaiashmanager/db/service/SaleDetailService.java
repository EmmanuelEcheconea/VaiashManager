package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.SaleDetailFiltersRq;
import com.vaiashmanager.db.entity.SaleDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface SaleDetailService {
    public List<SaleDetail> retrieveAllDetalleVenta();
    public SaleDetail createDetalleVenta(final SaleDetail saleDetail);
    public SaleDetail updateDetalleVenta(final Long idDetalleVenta, final SaleDetail saleDetail);
    public void deleteDetalleVenta(final Long idDetalleVenta);

    public Page<SaleDetail> getDetalleVentas(Pageable pageable);

    public List<SaleDetail> filters(SaleDetailFiltersRq saleDetailFiltersRq);
}
