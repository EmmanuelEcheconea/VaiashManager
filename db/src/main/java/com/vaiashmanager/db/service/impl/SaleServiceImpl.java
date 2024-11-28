package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.SaleFiltersRq;
import com.vaiashmanager.db.entity.Sale;
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
        Iterable<Sale> productosIterable = this.saleRepository.findAll();
        List<Sale> productos = StreamSupport.stream(productosIterable.spliterator(), false)
                .collect(Collectors.toList());
        return productos;
    }

    @Override
    public Sale createSale(Sale sale) {
        return this.saleRepository.save(sale);
    }

    @Override
    public Sale updateSale(Long idSale, Sale sale) {
        final Sale retrieveProduct = this.saleRepository.findById(idSale).get();
        if(sale.getFechaVenta() != null) {
            retrieveProduct.setFechaVenta(sale.getFechaVenta());
        }
        if(sale.getTotalVenta() != null) {
            retrieveProduct.setTotalVenta(sale.getTotalVenta());
        }
        final Sale response = this.saleRepository.save(retrieveProduct);
        return response;
    }

    @Override
    public void deleteSale(Long idSale) {
        final Sale retrieveProduct = this.saleRepository.findById(idSale).get();
        this.saleRepository.delete(retrieveProduct);
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
