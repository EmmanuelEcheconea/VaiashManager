package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.SaleDetailFiltersRq;
import com.vaiashmanager.db.entity.SaleDetail;
import com.vaiashmanager.db.service.SaleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("saleDetail")
public class SaleDetailController {

    private SaleDetailService saleDetailService;
    @Autowired
    public SaleDetailController(final SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }
    @GetMapping("")
    public List<SaleDetail> retrieveAllSaleDetail() {
        List<SaleDetail> response = this.saleDetailService.retrieveAllDetalleVenta();
        return response;
    }

    @PostMapping("")
    public SaleDetail createSaleDetail(@RequestBody final SaleDetail saleDetail) {
        return this.saleDetailService.createDetalleVenta(saleDetail);
    }

    @PutMapping("{idSaleDetail}")
    public SaleDetail updateSaleDetail(@PathVariable("idSaleDetail") final Long idSaleDetail,
                                         @RequestBody final SaleDetail saleDetail) {
        return this.saleDetailService.updateDetalleVenta(idSaleDetail, saleDetail);
    }

    @DeleteMapping("{idSaleDetail}")
    public void deleteDetalleVenta(@PathVariable("idSaleDetail") final Long idSaleDetail) {
        this.saleDetailService.deleteDetalleVenta(idSaleDetail);
    }

    @GetMapping("/SaleDetails")
    public Page<SaleDetail> listSaleDetail(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return saleDetailService.getDetalleVentas(pageable);
    }

    @PostMapping("/filters")
    public List<SaleDetail> filterSaleDetail(@RequestBody SaleDetailFiltersRq saleDetailFiltersRq) {
        return this.saleDetailService.filters(saleDetailFiltersRq);
    }

}
