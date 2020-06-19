package com.hkt.btu.repository;

import com.hkt.btu.domain.StockBalance;
import com.hkt.btu.domain.enumeration.Subinventory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StockBalance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockBalanceRepository extends JpaRepository<StockBalance, Long>, JpaSpecificationExecutor<StockBalance> {
	StockBalance findBySubInventory(Subinventory subinventory);
}
