package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.request.ProductFiltersRq;
import com.vaiashmanager.db.dto.request.ProductRqDTO;
import com.vaiashmanager.db.dto.response.ProductRsDTO;
import com.vaiashmanager.db.entity.Product;
import com.vaiashmanager.db.enums.ProductError;
import com.vaiashmanager.db.exception.CustomExceptionHandler;
import com.vaiashmanager.db.mapper.ProductMapper;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductRepositoryPageable productRepositoryPageable;
    private EntityManager entityManager;
    private ProductMapper productMapper;
    @Autowired
    public ProductServiceImpl(final ProductRepository productRepository, ProductRepositoryPageable productRepositoryPageable,
                              EntityManager entityManager,ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productRepositoryPageable = productRepositoryPageable;
        this.entityManager = entityManager;
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> retrieveAllProduct() {
        Iterable<Product> productosIterable = this.productRepository.findAll();
        if(!productosIterable.iterator().hasNext()) {
            return new ArrayList<>();
        }
        return  StreamSupport.stream(productosIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public ProductRsDTO createProduct(ProductRqDTO productRq) {
        if(productRq != null) {
            System.out.println(productRq.getNombre());
            Product response = this.productRepository.save(this.productMapper.productRqDTOToProduct(productRq));
            return this.productMapper.productToProductRsDTO(response);
        }
        throw new CustomExceptionHandler(ProductError.PRODUCT_EMPTY.getMessage(), ProductError.PRODUCT_EMPTY.getStatus());
    }

    @Override
    public Product updateProduct(Long idProduct, Product product) {
        final Optional<Product> retrieveProduct = this.productRepository.findById(idProduct);
        if(retrieveProduct.isEmpty()) {
            throw new CustomExceptionHandler(ProductError.NOT_FOUND.getMessage(), ProductError.PRODUCT_EMPTY.getStatus());
        }
        if(product.getCantidadStock() > 0) {
            retrieveProduct.get().setCantidadStock(product.getCantidadStock());
        }
        if(product.getCategory() != null) {
            retrieveProduct.get().setCategory(product.getCategory());
        }
        if(product.getPrecio() > 0) {
            retrieveProduct.get().setPrecio(product.getPrecio());
        }
        if(product.getNombre() != null) {
            retrieveProduct.get().setNombre(product.getNombre());
        }
        if(product.getDescripcion() != null) {
            retrieveProduct.get().setDescripcion(product.getDescripcion());
        }
        return this.productRepository.save(retrieveProduct.get());
    }

    @Override
    public void deleteProduct(Long idProduct) {
        final Optional<Product> retrieveProduct = this.productRepository.findById(idProduct);
        if(retrieveProduct.isEmpty()) {
            throw new CustomExceptionHandler(ProductError.NOT_FOUND.getMessage(), ProductError.PRODUCT_EMPTY.getStatus());
        }
        this.productRepository.delete(retrieveProduct.get());
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
        }
        return entityManager.createQuery(cq).getResultList();
    }

}
