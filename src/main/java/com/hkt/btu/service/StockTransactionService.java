package com.hkt.btu.service;

import com.hkt.btu.domain.StockTransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link StockTransaction}.
 */
public interface StockTransactionService {

    /**
     * Save a stockTransaction.
     *
     * @param stockTransaction the entity to save.
     * @return the persisted entity.
     */
    StockTransaction save(StockTransaction stockTransaction);

    /**
     * Get all the stockTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockTransaction> findAll(Pageable pageable);

    /**
     * Get the "id" stockTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockTransaction> findOne(Long id);

    /**
     * Delete the "id" stockTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
