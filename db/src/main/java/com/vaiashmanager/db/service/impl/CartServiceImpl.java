package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.CartFiltersRq;
import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.repository.CartRepository;
import com.vaiashmanager.db.repository.CartRepositoryPageable;
import com.vaiashmanager.db.service.CartService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private EntityManager entityManager;
    private CartRepositoryPageable cartRepositoryPageable;

    @Autowired
    public CartServiceImpl(final CartRepository cartRepository, final  EntityManager entityManager,
                           CartRepositoryPageable cartRepositoryPageable) {
        this.cartRepository = cartRepository;
        this.entityManager = entityManager;
        this.cartRepositoryPageable = cartRepositoryPageable;
    }

    @Override
    public List<Cart> retrieveAllCart() {
        Iterable<Cart> productosIterable = this.cartRepository.findAll();
        List<Cart> productos = StreamSupport.stream(productosIterable.spliterator(), false)
                .collect(Collectors.toList());
        return productos;
    }

    @Override
    public Cart createCart(final Cart product) {
        product.setEstado(true);
        product.setIdCliente(1L);
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        product.setFechaCreacion(formattedDate);
        return this.cartRepository.save(product);
    }

    @Override
    public Cart updateCart(final Long idCart, final Cart cart) {
        final Cart retrieveProduct = this.cartRepository.findById(idCart).get();
        if(cart.getIdCliente() != null) {
            retrieveProduct.setIdCliente(cart.getIdCliente());
        }
        if(cart.getEstado() != null) {
            retrieveProduct.setEstado(cart.getEstado());
        }
        final Cart response = this.cartRepository.save(retrieveProduct);
        return response;
    }

    @Override
    public void deleteCart(Long idProduct) {
        final Optional<Cart> retrieveCart = this.cartRepository.findById(idProduct);
        if(retrieveCart.get() != null) {
            this.cartRepository.delete(retrieveCart.get());
        }
    }

    @Override
    public Page<Cart> getCarts(Pageable pageable) {
        return this.cartRepositoryPageable.findAll(pageable);
    }

    @Override
    public List<Cart> filters(CartFiltersRq cartFiltersRq) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cart> cq = cb.createQuery(Cart.class);
        Root<Cart> root = cq.from(Cart.class);

        if (cartFiltersRq != null) {
            List<Predicate> predicates = new ArrayList<>();
            if (cartFiltersRq.getEstado() != null) {
                predicates.add(cb.equal(root.get("estado"), cartFiltersRq.getEstado()));
            }
            if (cartFiltersRq.getId() > -1) {
                predicates.add(cb.equal(root.get("id"), cartFiltersRq.getId()));
            }
            if (cartFiltersRq.getIdCliente() != null) {
                predicates.add(cb.equal(root.get("idCliente"), cartFiltersRq.getIdCliente()));
            }
            if (cartFiltersRq.getFechaCreacion() != null) {
                predicates.add(cb.equal(root.get("fechaCreacion"), cartFiltersRq.getFechaCreacion()));
            }
            if (!predicates.isEmpty()) {
                cq.where(cb.and(predicates.toArray(new Predicate[0])));
            }
            return entityManager.createQuery(cq).getResultList();
        }
        return new ArrayList<>();    }
}
