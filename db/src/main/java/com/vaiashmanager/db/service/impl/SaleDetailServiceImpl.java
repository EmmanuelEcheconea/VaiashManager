package com.vaiashmanager.db.service.impl;

import com.vaiashmanager.db.dto.SaleDetailFiltersRq;
import com.vaiashmanager.db.entity.SaleDetail;
import com.vaiashmanager.db.repository.SaleDetailRepository;
import com.vaiashmanager.db.repository.SaleDetailRepositoryPageable;
import com.vaiashmanager.db.service.SaleDetailService;
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
public class SaleDetailServiceImpl implements SaleDetailService {
    private SaleDetailRepository saleDetailRepository;
    private EntityManager entityManager;
    private SaleDetailRepositoryPageable saleDetailRepositoryPageable;

    @Autowired
    public SaleDetailServiceImpl(final SaleDetailRepository saleDetailRepository,
                                 EntityManager entityManager,
                                 SaleDetailRepositoryPageable saleDetailRepositoryPageable) {
        this.saleDetailRepository = saleDetailRepository;
        this.entityManager = entityManager;
        this.saleDetailRepositoryPageable = saleDetailRepositoryPageable;
    }

    @Override
    public List<SaleDetail> retrieveAllDetalleVenta() {
        Iterable<SaleDetail> productosIterable = this.saleDetailRepository.findAll();
        List<SaleDetail> productos = StreamSupport.stream(productosIterable.spliterator(), false)
                .collect(Collectors.toList());
        return productos;
    }

    @Override
    public SaleDetail createDetalleVenta(SaleDetail saleDetail) {
        return this.saleDetailRepository.save(saleDetail);
    }

    @Override
    public SaleDetail updateDetalleVenta(Long idDetalleVenta, SaleDetail saleDetail) {
        final SaleDetail retrieveProduct = this.saleDetailRepository.findById(idDetalleVenta).get();
        if(saleDetail.getCantidad() != null) {
            retrieveProduct.setCantidad(saleDetail.getCantidad());
        }
        if(saleDetail.getSumaTotal() != null) {
            retrieveProduct.setSumaTotal(saleDetail.getSumaTotal());
        }
        if(saleDetail.getPrecioUnitario() != null) {
            retrieveProduct.setPrecioUnitario(saleDetail.getPrecioUnitario());
        }
        final SaleDetail response = this.saleDetailRepository.save(retrieveProduct);
        return response;
    }

    @Override
    public void deleteDetalleVenta(Long idDetalleVenta) {
        final SaleDetail retrieveProduct = this.saleDetailRepository.findById(idDetalleVenta).get();
        this.saleDetailRepository.delete(retrieveProduct);
    }

    @Override
    public Page<SaleDetail> getDetalleVentas(Pageable pageable) {
        return this.saleDetailRepositoryPageable.findAll(pageable);
    }

    @Override
    public List<SaleDetail> filters(SaleDetailFiltersRq saleDetailFiltersRq) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SaleDetail> cq = cb.createQuery(SaleDetail.class);
        Root<SaleDetail> root = cq.from(SaleDetail.class);

        if (saleDetailFiltersRq != null) {
            List<Predicate> predicates = new ArrayList<>();
            if (saleDetailFiltersRq.getCantidad() != null) {
                predicates.add(cb.equal(root.get("cantidad"), saleDetailFiltersRq.getCantidad()));
            }
            if (saleDetailFiltersRq.getIdVenta()  != null) {
                System.out.println("entre aca");
                predicates.add(cb.equal(root.get("idVenta"), saleDetailFiltersRq.getIdVenta()));
            }
            if (saleDetailFiltersRq.getId() != null) {
                predicates.add(cb.equal(root.get("id"), saleDetailFiltersRq.getId()));
            }
            if (saleDetailFiltersRq.getSumaTotal() != null) {
                predicates.add(cb.equal(root.get("sumaTotal"), saleDetailFiltersRq.getSumaTotal()));
            }
            if (saleDetailFiltersRq.getIdProducto() != null) {
                predicates.add(cb.equal(root.get("idProducto"), saleDetailFiltersRq.getIdProducto()));
            }
            if (saleDetailFiltersRq.getPrecioUnitario() != null) {
                predicates.add(cb.equal(root.get("precioUnitario"), saleDetailFiltersRq.getPrecioUnitario()));
            }
            if (!predicates.isEmpty()) {
                cq.where(cb.and(predicates.toArray(new Predicate[0])));
            }
            return entityManager.createQuery(cq).getResultList();
        }
        return new ArrayList<>();
    }

}
