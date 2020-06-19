package com.hkt.btu.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.hkt.btu.domain.StockBalance;
import com.hkt.btu.domain.*; // for static metamodels
import com.hkt.btu.repository.StockBalanceRepository;
import com.hkt.btu.service.dto.StockBalanceCriteria;

/**
 * Service for executing complex queries for {@link StockBalance} entities in the database.
 * The main input is a {@link StockBalanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockBalance} or a {@link Page} of {@link StockBalance} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockBalanceQueryService extends QueryService<StockBalance> {

    private final Logger log = LoggerFactory.getLogger(StockBalanceQueryService.class);

    private final StockBalanceRepository stockBalanceRepository;

    public StockBalanceQueryService(StockBalanceRepository stockBalanceRepository) {
        this.stockBalanceRepository = stockBalanceRepository;
    }

    /**
     * Return a {@link List} of {@link StockBalance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockBalance> findByCriteria(StockBalanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockBalance> specification = createSpecification(criteria);
        return stockBalanceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StockBalance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockBalance> findByCriteria(StockBalanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockBalance> specification = createSpecification(criteria);
        return stockBalanceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockBalanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockBalance> specification = createSpecification(criteria);
        return stockBalanceRepository.count(specification);
    }

    /**
     * Function to convert {@link StockBalanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockBalance> createSpecification(StockBalanceCriteria criteria) {
        Specification<StockBalance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockBalance_.id));
            }
            if (criteria.getSubInventory() != null) {
                specification = specification.and(buildSpecification(criteria.getSubInventory(), StockBalance_.subInventory));
            }
            if (criteria.getQuantityOnhand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityOnhand(), StockBalance_.quantityOnhand));
            }
            if (criteria.getQuantityReserved() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityReserved(), StockBalance_.quantityReserved));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(StockBalance_.stockItem, JoinType.LEFT).get(StockItem_.id)));
            }
        }
        return specification;
    }
}
