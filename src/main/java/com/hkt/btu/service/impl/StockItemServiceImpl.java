package com.hkt.btu.service.impl;

import com.hkt.btu.service.StockItemService;
import com.hkt.btu.domain.StockItem;
import com.hkt.btu.repository.StockItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StockItem}.
 */
@Service
@Transactional
public class StockItemServiceImpl implements StockItemService {

    private final Logger log = LoggerFactory.getLogger(StockItemServiceImpl.class);

    private final StockItemRepository stockItemRepository;

    public StockItemServiceImpl(StockItemRepository stockItemRepository) {
        this.stockItemRepository = stockItemRepository;
    }

    /**
     * Save a stockItem.
     *
     * @param stockItem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItem save(StockItem stockItem) {
        log.debug("Request to save StockItem : {}", stockItem);
        return stockItemRepository.save(stockItem);
    }

    /**
     * Get all the stockItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockItem> findAll(Pageable pageable) {
        log.debug("Request to get all StockItems");
        return stockItemRepository.findAll(pageable);
    }

    /**
     * Get one stockItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItem> findOne(Long id) {
        log.debug("Request to get StockItem : {}", id);
        return stockItemRepository.findById(id);
    }

    /**
     * Delete the stockItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItem : {}", id);
        stockItemRepository.deleteById(id);
    }
}
