package com.hkt.btu.web.rest;

import com.hkt.btu.domain.StockItem;
import com.hkt.btu.service.StockItemService;
import com.hkt.btu.web.rest.errors.BadRequestAlertException;
import com.hkt.btu.service.dto.StockItemCriteria;
import com.hkt.btu.service.StockItemQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hkt.btu.domain.StockItem}.
 */
@RestController
@RequestMapping("/api")
public class StockItemResource {

    private final Logger log = LoggerFactory.getLogger(StockItemResource.class);

    private static final String ENTITY_NAME = "stockItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockItemService stockItemService;

    private final StockItemQueryService stockItemQueryService;

    public StockItemResource(StockItemService stockItemService, StockItemQueryService stockItemQueryService) {
        this.stockItemService = stockItemService;
        this.stockItemQueryService = stockItemQueryService;
    }

    /**
     * {@code POST  /stock-items} : Create a new stockItem.
     *
     * @param stockItem the stockItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockItem, or with status {@code 400 (Bad Request)} if the stockItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-items")
    public ResponseEntity<StockItem> createStockItem(@Valid @RequestBody StockItem stockItem) throws URISyntaxException {
        log.debug("REST request to save StockItem : {}", stockItem);
        if (stockItem.getId() != null) {
            throw new BadRequestAlertException("A new stockItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItem result = stockItemService.save(stockItem);
        return ResponseEntity.created(new URI("/api/stock-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-items} : Updates an existing stockItem.
     *
     * @param stockItem the stockItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockItem,
     * or with status {@code 400 (Bad Request)} if the stockItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-items")
    public ResponseEntity<StockItem> updateStockItem(@Valid @RequestBody StockItem stockItem) throws URISyntaxException {
        log.debug("REST request to update StockItem : {}", stockItem);
        if (stockItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItem result = stockItemService.save(stockItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-items} : get all the stockItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockItems in body.
     */
    @GetMapping("/stock-items")
    public ResponseEntity<List<StockItem>> getAllStockItems(StockItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StockItems by criteria: {}", criteria);
        Page<StockItem> page = stockItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stock-items/count} : count all the stockItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/stock-items/count")
    public ResponseEntity<Long> countStockItems(StockItemCriteria criteria) {
        log.debug("REST request to count StockItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stock-items/:id} : get the "id" stockItem.
     *
     * @param id the id of the stockItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-items/{id}")
    public ResponseEntity<StockItem> getStockItem(@PathVariable Long id) {
        log.debug("REST request to get StockItem : {}", id);
        Optional<StockItem> stockItem = stockItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItem);
    }

    /**
     * {@code DELETE  /stock-items/:id} : delete the "id" stockItem.
     *
     * @param id the id of the stockItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-items/{id}")
    public ResponseEntity<Void> deleteStockItem(@PathVariable Long id) {
    	/*
        log.debug("REST request to delete StockItem : {}", id);
        stockItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        */
    	throw new BadRequestAlertException("Invalid request", ENTITY_NAME, "invalidRequest");
    }
}
