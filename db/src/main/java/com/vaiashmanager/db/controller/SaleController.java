package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.SaleFiltersRq;
import com.vaiashmanager.db.entity.Sale;
import com.vaiashmanager.db.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sale")
public class SaleController {

    private SaleService saleService;
    @Autowired
    public SaleController(final SaleService SaleService) {
        this.saleService = saleService;
    }
    @GetMapping("")
    public List<Sale> retrieveAllSale() {
        List<Sale> response = this.saleService.retrieveAllSales();
        return response;
    }

    @PostMapping("")
    public Sale createSale(@RequestBody final Sale sale) {
        return this.saleService.createSale(sale);
    }

    @PutMapping("{idSale}")
    public Sale updateVenta(@PathVariable("idSale") final Long idSale,
                            @RequestBody final Sale sale) {
        return this.saleService.updateSale(idSale, sale);
    }

    @DeleteMapping("{idSale}")
    public void deleteVenta(@PathVariable("idSale") final Long idSale) {
        this.saleService.deleteSale(idSale);
    }


    @GetMapping("/sales")
    public Page<Sale> listDetalleSale(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return saleService.getVentas(pageable);
    }

    @PostMapping("/filters")
    public List<Sale> filterSale(@RequestBody SaleFiltersRq saleFiltersRq) {
        return this.saleService.filters(saleFiltersRq);
    }

}
