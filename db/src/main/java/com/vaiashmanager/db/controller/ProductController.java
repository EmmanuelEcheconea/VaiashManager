package com.vaiashmanager.db.controller;

import com.vaiashmanager.db.dto.ProductFiltersRq;
import com.vaiashmanager.db.entity.Product;
import com.vaiashmanager.db.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Product> retrieveAllProduct() {
        List<Product> response = this.productService.retrieveAllProduct();
        return response;
    }

    @PostMapping("")
    public Product createProduct(@RequestBody final Product product) {
        return this.productService.createProduct(product);
    }

    @PutMapping("{idProduct}")
    public Product updateProduct(@PathVariable("idProduct") final Long idProduct, @RequestBody final Product product) {
        return this.productService.updateProduct(idProduct, product);
    }

    @DeleteMapping("{idProduct}")
    public void deleteProduct(@PathVariable("idProduct") final Long idProduct) {
        this.productService.deleteProduct(idProduct);
    }

    @GetMapping("/products")
    public Page<Product> listProducts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getProducts(pageable);
    }

    @PostMapping("/filters")
    public List<Product> filterProduct(@RequestBody ProductFiltersRq productFiltersRq) {
        return this.productService.filters(productFiltersRq);
    }

}
