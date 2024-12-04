package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.request.ProductFiltersRq;
import com.vaiashmanager.db.dto.request.ProductRqDTO;
import com.vaiashmanager.db.dto.response.ProductRsDTO;
import com.vaiashmanager.db.entity.Product;
import com.vaiashmanager.db.exception.CustomExceptionHandler;
import com.vaiashmanager.db.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private ProductService productService;
    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("")
    public ResponseEntity<?> retrieveAllProduct() throws CustomExceptionHandler {
        List<Product> response = this.productService.retrieveAllProduct();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody final ProductRqDTO product) {
        ProductRsDTO response = this.productService.createProduct(product);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{idProduct}")
    public ResponseEntity<?> updateProduct(@PathVariable("idProduct") final Long idProduct, @RequestBody final Product product) {
        Product response =  this.productService.updateProduct(idProduct, product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{idProduct}")
    public void deleteProduct(@PathVariable("idProduct") final Long idProduct) {
        this.productService.deleteProduct(idProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<?> listProducts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> response = productService.getProducts(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filters")
    public ResponseEntity<?> filterProduct(@RequestBody ProductFiltersRq productFiltersRq) {
        List<Product> response = this.productService.filters(productFiltersRq);
        return ResponseEntity.ok(response);
    }

}
