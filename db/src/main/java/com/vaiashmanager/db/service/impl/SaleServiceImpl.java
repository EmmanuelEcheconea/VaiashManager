package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.request.SaleFiltersRq;
import com.vaiashmanager.db.dto.request.SaleRqDTO;
import com.vaiashmanager.db.dto.response.SaleRsDTO;
import com.vaiashmanager.db.entity.Cart;
import com.vaiashmanager.db.entity.CartProduct;
import com.vaiashmanager.db.entity.Product;
import com.vaiashmanager.db.entity.Sale;
import com.vaiashmanager.db.enums.*;
import com.vaiashmanager.db.exception.CustomExceptionHandler;
import com.vaiashmanager.db.mapper.SaleMapper;
import com.vaiashmanager.db.repository.*;
import com.vaiashmanager.db.service.SaleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SaleServiceImpl implements SaleService {

    private SaleRepository saleRepository;
    private EntityManager entityManager;
    private SaleRepositoryPageable saleRepositoryPageable;
    private SaleMapper saleMapper;
    private CartProductRepository cartProductRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    @Autowired
    public SaleServiceImpl(final SaleRepository saleRepository,
                           EntityManager entityManager,
                           SaleRepositoryPageable saleRepositoryPageable,
                           SaleMapper saleMapper,
                           CartProductRepository cartProductRepository,
                           CartRepository cartRepository,
                           ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.entityManager = entityManager;
        this.saleRepositoryPageable = saleRepositoryPageable;
        this.saleMapper = saleMapper;
        this.cartProductRepository = cartProductRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<SaleRsDTO> retrieveAllSales() {
        Iterable<Sale> SaleIterable = this.saleRepository.findAll();
        if(!SaleIterable.iterator().hasNext()) {
            return new ArrayList<>();
        }
        return StreamSupport.stream(SaleIterable.spliterator(), false).map(this.saleMapper::saleToSaleRsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SaleRsDTO createSale(SaleRqDTO sale) {
        if(sale != null) {
            Optional<Cart> cartEntity = this.cartRepository.findById(sale.getIdCart());
            if(cartEntity.isEmpty()){
                throw new CustomExceptionHandler(CartError.NOT_FOUND.getMessage(), CartError.NOT_FOUND.getStatus());
            }
            List<CartProduct> cartProductList = this.cartProductRepository.
                        findByIdCartAndState(cartEntity.get(), CartProductState.PENDIENTE.name());
            if(cartProductList.isEmpty()) {
                //aca hay que ver el tema de  carproduct q si esta vacio, el mensaje sea, q  no tien compras para  ahcer
                throw  new CustomExceptionHandler(CartProductError.NOT_FOUND.getMessage(), CartProductError.NOT_FOUND.getStatus());
            }

            double totalSum = cartProductList.stream()
                    .mapToDouble(cp -> cp.getIdProduct().getPrecio())
                    .sum();
            Sale entitySale = Sale.builder().cart(cartEntity.get()).estado(SaleState.PAGADO.name()).
                    fechaVenta(new Date()).totalVenta(totalSum).build();

            SaleRsDTO response = this.saleMapper.saleToSaleRsDTO(this.saleRepository.save(entitySale));
            cartProductList.forEach(cartProduct -> cartProduct.setState("PAGADO"));
            this.cartProductRepository.saveAll(cartProductList);
           // cartEntity.get().setState(CartState.INACTIVO.name());
            //this.cartRepository.save(cartEntity.get());
            //crear nuevo carro
           // Cart newCart = Cart.builder().state(CartState.ACTIVO.name()).client(cartEntity.get()
           //         .getClient()).fechaCreacion(new Timestamp(System.currentTimeMillis())).build();
           // this.cartRepository.save(newCart);
            return response;
        }
        throw new CustomExceptionHandler(SaleError.PRODUCT_EMPTY.getMessage(), SaleError.PRODUCT_EMPTY.getStatus());
    }


    @Override
    public void deleteSale(Long idSale) {
        final Optional<Sale> retrieveProduct = this.saleRepository.findById(idSale);
        if (retrieveProduct.isEmpty()) {
            throw new CustomExceptionHandler(SaleError.NOT_FOUND.getMessage(), SaleError.PRODUCT_EMPTY.getStatus());
        }
        this.saleRepository.delete(retrieveProduct.get());
    }

    @Override
    public Page<SaleRsDTO> getVentas(Pageable pageable) {
        Page<Sale> salesPage = this.saleRepositoryPageable.findAll(pageable);
        List<SaleRsDTO> saleRsDTOs = salesPage.stream().map(this.saleMapper::saleToSaleRsDTO).collect(Collectors.toList());
        return new PageImpl<>(saleRsDTOs, pageable, salesPage.getTotalElements());
    }

    @Override
    public List<SaleRsDTO> filters(SaleFiltersRq saleFiltersRq) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sale> cq = cb.createQuery(Sale.class);
        Root<Sale> root = cq.from(Sale.class);

        if (saleFiltersRq != null) {
            List<Predicate> predicates = new ArrayList<>();
            if (saleFiltersRq.getFechaVenta() != null) {
                predicates.add(cb.equal(root.get("fechaVenta"), saleFiltersRq.getFechaVenta()));
            }
            if (saleFiltersRq.getTotalVenta() > -1) {
                predicates.add(cb.equal(root.get("totalVenta"), saleFiltersRq.getTotalVenta()));
            }
            if (saleFiltersRq.getId() != null) {
                predicates.add(cb.equal(root.get("id"), saleFiltersRq.getId()));
            }
            if (saleFiltersRq.getIdCarro() != null) {
                predicates.add(cb.equal(root.get("idCarro"), saleFiltersRq.getIdCarro()));
            }

            if (!predicates.isEmpty()) {
                cq.where(cb.and(predicates.toArray(new Predicate[0])));
            }
            List<Sale> response = entityManager.createQuery(cq).getResultList();
            return response.stream().map(this.saleMapper::saleToSaleRsDTO).collect(Collectors.toList());
        }
        return new ArrayList<>();    }


}
