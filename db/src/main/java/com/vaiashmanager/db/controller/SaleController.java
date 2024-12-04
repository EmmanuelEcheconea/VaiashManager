package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.request.SaleFiltersRq;
import com.vaiashmanager.db.entity.Sale;
import com.vaiashmanager.db.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sale")
public class SaleController {

    private SaleService saleService;
    @Autowired
    public SaleController(final SaleService saleService) {
        this.saleService = saleService;
    }
    @GetMapping("")
    public ResponseEntity<?> retrieveAllSale() {
        List<Sale> response = this.saleService.retrieveAllSales();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createSale(@RequestBody final Sale sale) {
        Sale response = this.saleService.createSale(sale);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{idSale}")
    public ResponseEntity<?> updateVenta(@PathVariable("idSale") final Long idSale,
                            @RequestBody final Sale sale) {
        Sale response = this.saleService.updateSale(idSale, sale);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{idSale}")
    public void deleteVenta(@PathVariable("idSale") final Long idSale) {
        this.saleService.deleteSale(idSale);
    }


    @GetMapping("/sales")
    public ResponseEntity<?> listDetalleSale(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Sale> response = saleService.getVentas(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filters")
    public ResponseEntity<?> filterSale(@RequestBody SaleFiltersRq saleFiltersRq) {
        List<Sale> response = this.saleService.filters(saleFiltersRq);
        return ResponseEntity.ok(response);
    }

}
