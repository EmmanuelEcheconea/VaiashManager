package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.request.SaleFiltersRq;
import com.vaiashmanager.db.entity.Sale;
import com.vaiashmanager.db.enums.SaleError;
import com.vaiashmanager.db.exception.CustomExceptionHandler;
import com.vaiashmanager.db.repository.SaleRepository;
import com.vaiashmanager.db.repository.SaleRepositoryPageable;
import com.vaiashmanager.db.service.SaleService;
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
public class SaleServiceImpl implements SaleService {

    private SaleRepository saleRepository;
    private EntityManager entityManager;
    private SaleRepositoryPageable saleRepositoryPageable;
    @Autowired
    public SaleServiceImpl(final SaleRepository saleRepository,
                           EntityManager entityManager,
                           SaleRepositoryPageable saleRepositoryPageable) {
        this.saleRepository = saleRepository;
        this.entityManager = entityManager;
        this.saleRepositoryPageable = saleRepositoryPageable;
    }

    @Override
    public List<Sale> retrieveAllSales() {
        Iterable<Sale> SaleIterable = this.saleRepository.findAll();
        if(!SaleIterable.iterator().hasNext()) {
            return new ArrayList<>();
        }
        return StreamSupport.stream(SaleIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Sale createSale(Sale sale) {
        if(sale != null) {
            return this.saleRepository.save(sale);
        }
        throw new CustomExceptionHandler(SaleError.PRODUCT_EMPTY.getMessage(), SaleError.PRODUCT_EMPTY.getStatus());
    }

    @Override
    public Sale updateSale(Long idSale, Sale sale) {
        final Optional<Sale> retrieveProduct = this.saleRepository.findById(idSale);
        if(retrieveProduct.isEmpty()) {
            throw new CustomExceptionHandler(SaleError.NOT_FOUND.getMessage(), SaleError.PRODUCT_EMPTY.getStatus());
        }
        if(sale.getFechaVenta() != null) {
            retrieveProduct.get().setFechaVenta(sale.getFechaVenta());
        }
        if(sale.getTotalVenta() != null) {
            retrieveProduct.get().setTotalVenta(sale.getTotalVenta());
        }
        return this.saleRepository.save(retrieveProduct.get());
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
    public Page<Sale> getVentas(Pageable pageable) {
        return this.saleRepositoryPageable.findAll(pageable);
    }

    @Override
    public List<Sale> filters(SaleFiltersRq saleFiltersRq) {
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
            return entityManager.createQuery(cq).getResultList();
        }
        return new ArrayList<>();    }


}
