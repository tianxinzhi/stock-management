package com.hkt.btu.service.impl;

import com.hkt.btu.service.StockBalanceService;
import com.hkt.btu.service.StockTransactionService;
import com.hkt.btu.domain.StockBalance;
import com.hkt.btu.domain.StockTransaction;
import com.hkt.btu.repository.StockTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StockTransaction}.
 */
@Service
@Transactional
public class StockTransactionServiceImpl implements StockTransactionService {

    private final Logger log = LoggerFactory.getLogger(StockTransactionServiceImpl.class);

    private final StockTransactionRepository stockTransactionRepository;
    
    private final StockBalanceService stockBalanceService;

    public StockTransactionServiceImpl(StockTransactionRepository stockTransactionRepository,StockBalanceService stockBalanceService) {
        this.stockTransactionRepository = stockTransactionRepository;
        this.stockBalanceService = stockBalanceService;
    }

    /**
     * Save a stockTransaction.
     *
     * @param stockTransaction the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockTransaction save(StockTransaction stockTransaction) {
        log.debug("Request to save StockTransaction : {}", stockTransaction);
        
        
        
        /*
        System.out.println("-------------------");
        System.out.println(balance.getStockItem().getItemNumber());
        System.out.println("On hand: " + balance.getQuantityOnhand());
        System.out.println("Reserved: " + balance.getQuantityReserved());
        System.out.println("-------------------");
        */
        StockBalance toBalance,fromBalance;
        
        int transactionQuantity = stockTransaction.getTransactionQuantity();
        
        if(transactionQuantity <= 0) {
        	return null;
        }

		switch (stockTransaction.getTransactionType()) {
			case IN:
				if (stockTransaction.getSubinventoryTo() == null) {
					return null;
				}

				toBalance = stockBalanceService.findOneBySubInventory(stockTransaction.getSubinventoryTo());
	
				toBalance.setQuantityOnhand(toBalance.getQuantityOnhand() + transactionQuantity);
				break;
			case OUT:
				if (stockTransaction.getSubinventoryFrom() == null) {
					return null;
				}
	
				fromBalance = stockBalanceService.findOneBySubInventory(stockTransaction.getSubinventoryFrom());
	
				if (fromBalance.getQuantityOnhand() <= transactionQuantity) {
					return null;
				}
	
				fromBalance.setQuantityOnhand(fromBalance.getQuantityOnhand() - transactionQuantity);
				break;
			case TRANSFER:
				if (stockTransaction.getSubinventoryFrom() == null || stockTransaction.getSubinventoryTo() == null) {
					return null;
				}
	
				toBalance = stockBalanceService.findOneBySubInventory(stockTransaction.getSubinventoryTo());
				fromBalance = stockBalanceService.findOneBySubInventory(stockTransaction.getSubinventoryFrom());
	
				if (fromBalance.getQuantityOnhand() <= transactionQuantity) {
					return null;
				}
	
				fromBalance.setQuantityOnhand(fromBalance.getQuantityOnhand() - transactionQuantity);
				toBalance.setQuantityOnhand(toBalance.getQuantityOnhand() + transactionQuantity);
	
				break;
		}    
        
        return stockTransactionRepository.save(stockTransaction);
    }

    /**
     * Get all the stockTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockTransaction> findAll(Pageable pageable) {
        log.debug("Request to get all StockTransactions");
        return stockTransactionRepository.findAll(pageable);
    }

    /**
     * Get one stockTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockTransaction> findOne(Long id) {
        log.debug("Request to get StockTransaction : {}", id);
        return stockTransactionRepository.findById(id);
    }

    /**
     * Delete the stockTransaction by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockTransaction : {}", id);
        stockTransactionRepository.deleteById(id);
    }
}
