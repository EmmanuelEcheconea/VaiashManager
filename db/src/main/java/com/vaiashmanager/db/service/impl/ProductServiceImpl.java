package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.request.ProductFiltersRq;
import com.vaiashmanager.db.dto.request.ProductRqDTO;
import com.vaiashmanager.db.dto.response.ProductRsDTO;
import com.vaiashmanager.db.entity.Category;
import com.vaiashmanager.db.entity.Product;
import com.vaiashmanager.db.enums.CategoryError;
import com.vaiashmanager.db.enums.ProductError;
import com.vaiashmanager.db.exception.CustomExceptionHandler;
import com.vaiashmanager.db.mapper.ProductMapper;
import com.vaiashmanager.db.repository.CategoryRepository;
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
import org.springframework.data.domain.PageImpl;
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
    private CategoryRepository categoryRepository;
    @Autowired
    public ProductServiceImpl(final ProductRepository productRepository, ProductRepositoryPageable productRepositoryPageable,
                              EntityManager entityManager,ProductMapper productMapper,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productRepositoryPageable = productRepositoryPageable;
        this.entityManager = entityManager;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductRsDTO> retrieveAllProduct() {
        Iterable<Product> productosIterable = this.productRepository.findAll();
        if(!productosIterable.iterator().hasNext()) {
            return new ArrayList<>();
        }
        return StreamSupport.stream(productosIterable.spliterator(), false)
                .map(this.productMapper::productToProductRsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductRsDTO createProduct(ProductRqDTO productRq) {
        if(productRq != null) {
            Optional<Product> productByName = this.productRepository.findByNombre(productRq.getNombre());
            if(productByName.isPresent()) {
                throw new CustomExceptionHandler(ProductError.PRODUCT_EXIST.getMessage(), ProductError.PRODUCT_EXIST.getStatus());
            }
            Optional<Category> categoryById = this.categoryRepository.findById(productRq.getIdCategoria());
            if(categoryById.isEmpty()) {
                throw new CustomExceptionHandler(CategoryError.NOT_FOUND.getMessage(), CategoryError.NOT_FOUND.getStatus());
            }
            Product response = this.productRepository.save(this.productMapper.productRqDTOToProduct(productRq, categoryById.get()));
            return this.productMapper.productToProductRsDTO(response);
        }
        throw new CustomExceptionHandler(ProductError.PRODUCT_EMPTY.getMessage(), ProductError.PRODUCT_EMPTY.getStatus());
    }

    @Override
    public ProductRsDTO updateProduct(Long idProduct, ProductRqDTO product) {
        final Optional<Product> retrieveProduct = this.productRepository.findById(idProduct);
        if(retrieveProduct.isEmpty()) {
            throw new CustomExceptionHandler(ProductError.NOT_FOUND.getMessage(), ProductError.PRODUCT_EMPTY.getStatus());
        }
        if(product.getCantidadStock() > 0) {
            retrieveProduct.get().setCantidadStock(product.getCantidadStock());
        }
        Optional<Category> categoryById = this.categoryRepository.findById(retrieveProduct.get().getCategory().getId());
        if(categoryById.isEmpty()) {
            throw new CustomExceptionHandler(CategoryError.NOT_FOUND.getMessage(), CategoryError.NOT_FOUND.getStatus());
        }
        retrieveProduct.get().setCategory(categoryById.get());
        if(product.getPrecio() > 0) {
            retrieveProduct.get().setPrecio(product.getPrecio());
        }
        if(product.getNombre() != null) {
            retrieveProduct.get().setNombre(product.getNombre());
        }
        if(product.getDescription() != null) {
            retrieveProduct.get().setDescripcion(product.getDescription());
        }
        return this.productMapper.productToProductRsDTO(this.productRepository.save(retrieveProduct.get()));
    }

    @Override
    public void deleteProduct(Long idProduct) {
        final Optional<Product> retrieveProduct = this.productRepository.findById(idProduct);
        if(retrieveProduct.isEmpty()) {
            throw new CustomExceptionHandler(ProductError.NOT_FOUND.getMessage(), ProductError.PRODUCT_EMPTY.getStatus());
        }
        this.productRepository.delete(retrieveProduct.get());
    }

    public Page<ProductRsDTO> getProducts(Pageable pageable) {
        Page<Product> productsPage = productRepositoryPageable.findAll(pageable);
        List<ProductRsDTO> productRsDTOs = productsPage.getContent().stream()
                .map(this.productMapper::productToProductRsDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(productRsDTOs, pageable, productsPage.getTotalElements());
    }

    public List<ProductRsDTO> filters(ProductFiltersRq productFiltersRq) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        if (productFiltersRq != null) {
            List<Predicate> predicates = new ArrayList<>();
            if (productFiltersRq.getDescription() != null) {
                predicates.add(cb.equal(root.get("descripcion"), productFiltersRq.getDescription()));
            }
            if (productFiltersRq.getPrecio() > 0.0) {
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
        List<Product> response = entityManager.createQuery(cq).getResultList();
        return  response.stream().map(this.productMapper::productToProductRsDTO).collect(Collectors.toList());
    }

}
