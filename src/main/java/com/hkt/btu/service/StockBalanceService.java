package com.hkt.btu.service;

import com.hkt.btu.domain.StockBalance;
import com.hkt.btu.domain.enumeration.Subinventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link StockBalance}.
 */
public interface StockBalanceService {

    /**
     * Save a stockBalance.
     *
     * @param stockBalance the entity to save.
     * @return the persisted entity.
     */
    StockBalance save(StockBalance stockBalance);

    /**
     * Get all the stockBalances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockBalance> findAll(Pageable pageable);

    /**
     * Get the "id" stockBalance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockBalance> findOne(Long id);

    /**
     * Delete the "id" stockBalance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	StockBalance findOneBySubInventory(Subinventory subinventory);
}
