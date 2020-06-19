package com.hkt.btu.repository;

import com.hkt.btu.domain.StockItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StockItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockItemRepository extends JpaRepository<StockItem, Long>, JpaSpecificationExecutor<StockItem> {
}
