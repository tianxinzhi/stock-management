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

import com.hkt.btu.domain.StockTransaction;
import com.hkt.btu.domain.*; // for static metamodels
import com.hkt.btu.repository.StockTransactionRepository;
import com.hkt.btu.service.dto.StockTransactionCriteria;

/**
 * Service for executing complex queries for {@link StockTransaction} entities in the database.
 * The main input is a {@link StockTransactionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockTransaction} or a {@link Page} of {@link StockTransaction} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockTransactionQueryService extends QueryService<StockTransaction> {

    private final Logger log = LoggerFactory.getLogger(StockTransactionQueryService.class);

    private final StockTransactionRepository stockTransactionRepository;

    public StockTransactionQueryService(StockTransactionRepository stockTransactionRepository) {
        this.stockTransactionRepository = stockTransactionRepository;
    }

    /**
     * Return a {@link List} of {@link StockTransaction} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockTransaction> findByCriteria(StockTransactionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockTransaction> specification = createSpecification(criteria);
        return stockTransactionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StockTransaction} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockTransaction> findByCriteria(StockTransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockTransaction> specification = createSpecification(criteria);
        return stockTransactionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockTransactionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockTransaction> specification = createSpecification(criteria);
        return stockTransactionRepository.count(specification);
    }

    /**
     * Function to convert {@link StockTransactionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockTransaction> createSpecification(StockTransactionCriteria criteria) {
        Specification<StockTransaction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockTransaction_.id));
            }
            if (criteria.getSubinventoryFrom() != null) {
                specification = specification.and(buildSpecification(criteria.getSubinventoryFrom(), StockTransaction_.subinventoryFrom));
            }
            if (criteria.getSubinventoryTo() != null) {
                specification = specification.and(buildSpecification(criteria.getSubinventoryTo(), StockTransaction_.subinventoryTo));
            }
            if (criteria.getTransactionType() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionType(), StockTransaction_.transactionType));
            }
            if (criteria.getTransactionQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionQuantity(), StockTransaction_.transactionQuantity));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), StockTransaction_.reference));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(StockTransaction_.stockItem, JoinType.LEFT).get(StockItem_.id)));
            }
        }
        return specification;
    }
}
