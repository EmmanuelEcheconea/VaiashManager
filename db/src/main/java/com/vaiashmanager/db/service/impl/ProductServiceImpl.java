package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.ProductFiltersRq;
import com.vaiashmanager.db.entity.Product;
import com.vaiashmanager.db.repository.ProductRepository;
import com.vaiashmanager.db.repository.ProductRepositoryPageable;
import com.vaiashmanager.db.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductRepositoryPageable productRepositoryPageable;
    private EntityManager entityManager;
    @Autowired
    public ProductServiceImpl(final ProductRepository productRepository, ProductRepositoryPageable productRepositoryPageable,
                              EntityManager entityManager) {
        this.productRepository = productRepository;
        this.productRepositoryPageable = productRepositoryPageable;
        this.entityManager = entityManager;
    }

    @Override
    public List<Product> retrieveAllProduct() {
        Iterable<Product> productosIterable = this.productRepository.findAll();
        List<Product> products = StreamSupport.stream(productosIterable.spliterator(), false)
                .collect(Collectors.toList());
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long idProduct, Product product) {
        final Product retrieveProduct = this.productRepository.findById(idProduct).get();
        if(product.getCantidadStock() > 0) {
            retrieveProduct.setCantidadStock(product.getCantidadStock());
        }
        if(product.getCategoria() != null) {
            retrieveProduct.setCategoria(product.getCategoria());
        }
        if(product.getPrecio() > 0) {
            retrieveProduct.setPrecio(product.getPrecio());
        }
        if(product.getNombre() != null) {
            retrieveProduct.setNombre(product.getNombre());
        }
        if(product.getDescripcion() != null) {
            retrieveProduct.setDescripcion(product.getDescripcion());
        }
        final Product response = this.productRepository.save(retrieveProduct);
        return response;
    }

    @Override
    public void deleteProduct(Long idProduct) {
        final Product retrieveProduct = this.productRepository.findById(idProduct).get();
        this.productRepository.delete(retrieveProduct);
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepositoryPageable.findAll(pageable);
    }

    public List<Product> filters(ProductFiltersRq productFiltersRq) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        if (productFiltersRq != null) {
            List<Predicate> predicates = new ArrayList<>();
            if (productFiltersRq.getDescription() != null) {
                predicates.add(cb.equal(root.get("descripcion"), productFiltersRq.getDescription()));
            }
            if (productFiltersRq.getPrecio() > -1) {
                predicates.add(cb.equal(root.get("precio"), productFiltersRq.getPrecio()));
            }
            if (productFiltersRq.getNombre() != null) {
                predicates.add(cb.equal(root.get("nombre"), productFiltersRq.getNombre()));
            }
            if (productFiltersRq.getCategoria() != null) {
                predicates.add(cb.equal(root.get("categoria"), productFiltersRq.getCategoria()));
            }
            if (productFiltersRq.getCantidad_stock() > 0.0) {
                predicates.add(cb.equal(root.get("cantidadStock"), productFiltersRq.getCantidad_stock()));
            }
            if (productFiltersRq.getId() > 0L) {
                predicates.add(cb.equal(root.get("id"), productFiltersRq.getId()));
            }
            if (!predicates.isEmpty()) {
                cq.where(cb.and(predicates.toArray(new Predicate[0])));
            }
            return entityManager.createQuery(cq).getResultList();
        }
        return new ArrayList<>();
    }

}
