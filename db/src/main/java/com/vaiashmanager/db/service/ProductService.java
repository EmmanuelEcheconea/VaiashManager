package com.vaiashmanager.db.service;

import com.vaiashmanager.db.dto.request.ProductFiltersRq;
import com.vaiashmanager.db.dto.request.ProductRqDTO;
import com.vaiashmanager.db.dto.response.ProductRsDTO;
import com.vaiashmanager.db.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Component
public interface ProductService {
    public List<Product> retrieveAllProduct();
    public ProductRsDTO createProduct(final ProductRqDTO product);
    public Product updateProduct(final Long idProduct, final Product product);
    public void deleteProduct(final Long idProduct);
    public Page<Product> getProducts(Pageable pageable);

    public List<Product> filters(ProductFiltersRq productFiltersRq);

}
