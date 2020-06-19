package com.hkt.btu.service.impl;

import com.hkt.btu.service.StockBalanceService;
import com.hkt.btu.domain.StockBalance;
import com.hkt.btu.domain.enumeration.Subinventory;
import com.hkt.btu.repository.StockBalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StockBalance}.
 */
@Service
@Transactional
public class StockBalanceServiceImpl implements StockBalanceService {

    private final Logger log = LoggerFactory.getLogger(StockBalanceServiceImpl.class);

    private final StockBalanceRepository stockBalanceRepository;

    public StockBalanceServiceImpl(StockBalanceRepository stockBalanceRepository) {
        this.stockBalanceRepository = stockBalanceRepository;
    }

    /**
     * Save a stockBalance.
     *
     * @param stockBalance the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockBalance save(StockBalance stockBalance) {
        log.debug("Request to save StockBalance : {}", stockBalance);
        return stockBalanceRepository.save(stockBalance);
    }

    /**
     * Get all the stockBalances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockBalance> findAll(Pageable pageable) {
        log.debug("Request to get all StockBalances");
        return stockBalanceRepository.findAll(pageable);
    }

    /**
     * Get one stockBalance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockBalance> findOne(Long id) {
        log.debug("Request to get StockBalance : {}", id);
        return stockBalanceRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public StockBalance findOneBySubInventory(Subinventory subinventory) {
        log.debug("Request to get StockBalance by subinventory: {}", subinventory);
        return stockBalanceRepository.findBySubInventory(subinventory);
    }

    /**
     * Delete the stockBalance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockBalance : {}", id);
        stockBalanceRepository.deleteById(id);
    }
}
