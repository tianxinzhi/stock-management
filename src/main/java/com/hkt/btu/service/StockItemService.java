package com.hkt.btu.service;

import com.hkt.btu.domain.StockItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link StockItem}.
 */
public interface StockItemService {

    /**
     * Save a stockItem.
     *
     * @param stockItem the entity to save.
     * @return the persisted entity.
     */
    StockItem save(StockItem stockItem);

    /**
     * Get all the stockItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockItem> findAll(Pageable pageable);

    /**
     * Get the "id" stockItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockItem> findOne(Long id);

    /**
     * Delete the "id" stockItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
