package com.vaiashmanager.db.mapper;

import com.vaiashmanager.db.dto.request.ProductRqDTO;
import com.vaiashmanager.db.dto.response.ProductRsDTO;
import com.vaiashmanager.db.entity.Category;
import com.vaiashmanager.db.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductRsDTO productToProductRsDTO(Product product) {
        ProductRsDTO dto = new ProductRsDTO();
        dto.setDescription(product.getDescripcion());
        dto.setPrecio(product.getPrecio());
        dto.setNombre(product.getNombre());
        dto.setCategoria(product.getCategory());
        dto.setCantidadStock(product.getCantidadStock());
        dto.setId(product.getId());
        return dto;
    }

    public Product productRqDTOToProduct(ProductRqDTO dto) {
        Product product = new Product();
        product.setDescripcion(dto.getDescription());
        product.setPrecio(dto.getPrecio());
        product.setNombre(dto.getNombre());
        product.setCantidadStock(dto.getCantidadStock());
        return product;
    }
    public Product productRqDTOToProduct(ProductRqDTO dto, Category category) {
        Product product = new Product();
        product.setDescripcion(dto.getDescription());
        product.setPrecio(dto.getPrecio());
        product.setNombre(dto.getNombre());
        product.setCategory(category);
        product.setCantidadStock(dto.getCantidadStock());
        return product;
    }
}
