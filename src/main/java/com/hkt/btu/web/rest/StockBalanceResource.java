package com.hkt.btu.web.rest;

import com.hkt.btu.domain.StockBalance;
import com.hkt.btu.service.StockBalanceService;
import com.hkt.btu.web.rest.errors.BadRequestAlertException;
import com.hkt.btu.service.dto.StockBalanceCriteria;
import com.hkt.btu.service.StockBalanceQueryService;

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
 * REST controller for managing {@link com.hkt.btu.domain.StockBalance}.
 */
@RestController
@RequestMapping("/api")
public class StockBalanceResource {

    private final Logger log = LoggerFactory.getLogger(StockBalanceResource.class);

    private static final String ENTITY_NAME = "stockBalance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockBalanceService stockBalanceService;

    private final StockBalanceQueryService stockBalanceQueryService;

    public StockBalanceResource(StockBalanceService stockBalanceService, StockBalanceQueryService stockBalanceQueryService) {
        this.stockBalanceService = stockBalanceService;
        this.stockBalanceQueryService = stockBalanceQueryService;
    }

    /**
     * {@code POST  /stock-balances} : Create a new stockBalance.
     *
     * @param stockBalance the stockBalance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockBalance, or with status {@code 400 (Bad Request)} if the stockBalance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-balances")
    public ResponseEntity<StockBalance> createStockBalance(@Valid @RequestBody StockBalance stockBalance) throws URISyntaxException {
        log.debug("REST request to save StockBalance : {}", stockBalance);
        if (stockBalance.getId() != null) {
            throw new BadRequestAlertException("A new stockBalance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockBalance result = stockBalanceService.save(stockBalance);
        return ResponseEntity.created(new URI("/api/stock-balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-balances} : Updates an existing stockBalance.
     *
     * @param stockBalance the stockBalance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockBalance,
     * or with status {@code 400 (Bad Request)} if the stockBalance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockBalance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-balances")
    public ResponseEntity<StockBalance> updateStockBalance(@Valid @RequestBody StockBalance stockBalance) throws URISyntaxException {
        log.debug("REST request to update StockBalance : {}", stockBalance);
        if (stockBalance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockBalance result = stockBalanceService.save(stockBalance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockBalance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-balances} : get all the stockBalances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockBalances in body.
     */
    @GetMapping("/stock-balances")
    public ResponseEntity<List<StockBalance>> getAllStockBalances(StockBalanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StockBalances by criteria: {}", criteria);
        Page<StockBalance> page = stockBalanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stock-balances/count} : count all the stockBalances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/stock-balances/count")
    public ResponseEntity<Long> countStockBalances(StockBalanceCriteria criteria) {
        log.debug("REST request to count StockBalances by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockBalanceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stock-balances/:id} : get the "id" stockBalance.
     *
     * @param id the id of the stockBalance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockBalance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-balances/{id}")
    public ResponseEntity<StockBalance> getStockBalance(@PathVariable Long id) {
        log.debug("REST request to get StockBalance : {}", id);
        Optional<StockBalance> stockBalance = stockBalanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockBalance);
    }

    /**
     * {@code DELETE  /stock-balances/:id} : delete the "id" stockBalance.
     *
     * @param id the id of the stockBalance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-balances/{id}")
    public ResponseEntity<Void> deleteStockBalance(@PathVariable Long id) {
        log.debug("REST request to delete StockBalance : {}", id);
        stockBalanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
