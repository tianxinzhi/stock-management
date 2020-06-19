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

import com.hkt.btu.domain.StockItem;
import com.hkt.btu.domain.*; // for static metamodels
import com.hkt.btu.repository.StockItemRepository;
import com.hkt.btu.service.dto.StockItemCriteria;

/**
 * Service for executing complex queries for {@link StockItem} entities in the database.
 * The main input is a {@link StockItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockItem} or a {@link Page} of {@link StockItem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockItemQueryService extends QueryService<StockItem> {

    private final Logger log = LoggerFactory.getLogger(StockItemQueryService.class);

    private final StockItemRepository stockItemRepository;

    public StockItemQueryService(StockItemRepository stockItemRepository) {
        this.stockItemRepository = stockItemRepository;
    }

    /**
     * Return a {@link List} of {@link StockItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockItem> findByCriteria(StockItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockItem> specification = createSpecification(criteria);
        return stockItemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StockItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockItem> findByCriteria(StockItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockItem> specification = createSpecification(criteria);
        return stockItemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockItem> specification = createSpecification(criteria);
        return stockItemRepository.count(specification);
    }

    /**
     * Function to convert {@link StockItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockItem> createSpecification(StockItemCriteria criteria) {
        Specification<StockItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockItem_.id));
            }
            if (criteria.getItemNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemNumber(), StockItem_.itemNumber));
            }
            if (criteria.getItemDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemDescription(), StockItem_.itemDescription));
            }
        }
        return specification;
    }
}
