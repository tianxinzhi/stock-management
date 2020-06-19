package com.hkt.btu.web.rest;

import com.hkt.btu.StockManagementApp;
import com.hkt.btu.config.TestSecurityConfiguration;
import com.hkt.btu.domain.StockBalance;
import com.hkt.btu.domain.StockItem;
import com.hkt.btu.repository.StockBalanceRepository;
import com.hkt.btu.service.StockBalanceService;
import com.hkt.btu.service.dto.StockBalanceCriteria;
import com.hkt.btu.service.StockBalanceQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hkt.btu.domain.enumeration.Subinventory;
/**
 * Integration tests for the {@link StockBalanceResource} REST controller.
 */
@SpringBootTest(classes = { StockManagementApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class StockBalanceResourceIT {

    private static final Subinventory DEFAULT_SUB_INVENTORY = Subinventory.FG;
    private static final Subinventory UPDATED_SUB_INVENTORY = Subinventory.STAGING;

    private static final Integer DEFAULT_QUANTITY_ONHAND = 1;
    private static final Integer UPDATED_QUANTITY_ONHAND = 2;
    private static final Integer SMALLER_QUANTITY_ONHAND = 1 - 1;

    private static final Integer DEFAULT_QUANTITY_RESERVED = 1;
    private static final Integer UPDATED_QUANTITY_RESERVED = 2;
    private static final Integer SMALLER_QUANTITY_RESERVED = 1 - 1;

    @Autowired
    private StockBalanceRepository stockBalanceRepository;

    @Autowired
    private StockBalanceService stockBalanceService;

    @Autowired
    private StockBalanceQueryService stockBalanceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockBalanceMockMvc;

    private StockBalance stockBalance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockBalance createEntity(EntityManager em) {
        StockBalance stockBalance = new StockBalance()
            .subInventory(DEFAULT_SUB_INVENTORY)
            .quantityOnhand(DEFAULT_QUANTITY_ONHAND)
            .quantityReserved(DEFAULT_QUANTITY_RESERVED);
        // Add required entity
        StockItem stockItem;
        if (TestUtil.findAll(em, StockItem.class).isEmpty()) {
            stockItem = StockItemResourceIT.createEntity(em);
            em.persist(stockItem);
            em.flush();
        } else {
            stockItem = TestUtil.findAll(em, StockItem.class).get(0);
        }
        stockBalance.setStockItem(stockItem);
        return stockBalance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockBalance createUpdatedEntity(EntityManager em) {
        StockBalance stockBalance = new StockBalance()
            .subInventory(UPDATED_SUB_INVENTORY)
            .quantityOnhand(UPDATED_QUANTITY_ONHAND)
            .quantityReserved(UPDATED_QUANTITY_RESERVED);
        // Add required entity
        StockItem stockItem;
        if (TestUtil.findAll(em, StockItem.class).isEmpty()) {
            stockItem = StockItemResourceIT.createUpdatedEntity(em);
            em.persist(stockItem);
            em.flush();
        } else {
            stockItem = TestUtil.findAll(em, StockItem.class).get(0);
        }
        stockBalance.setStockItem(stockItem);
        return stockBalance;
    }

    @BeforeEach
    public void initTest() {
        stockBalance = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockBalance() throws Exception {
        int databaseSizeBeforeCreate = stockBalanceRepository.findAll().size();

        // Create the StockBalance
        restStockBalanceMockMvc.perform(post("/api/stock-balances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockBalance)))
            .andExpect(status().isCreated());

        // Validate the StockBalance in the database
        List<StockBalance> stockBalanceList = stockBalanceRepository.findAll();
        assertThat(stockBalanceList).hasSize(databaseSizeBeforeCreate + 1);
        StockBalance testStockBalance = stockBalanceList.get(stockBalanceList.size() - 1);
        assertThat(testStockBalance.getSubInventory()).isEqualTo(DEFAULT_SUB_INVENTORY);
        assertThat(testStockBalance.getQuantityOnhand()).isEqualTo(DEFAULT_QUANTITY_ONHAND);
        assertThat(testStockBalance.getQuantityReserved()).isEqualTo(DEFAULT_QUANTITY_RESERVED);
    }

    @Test
    @Transactional
    public void createStockBalanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockBalanceRepository.findAll().size();

        // Create the StockBalance with an existing ID
        stockBalance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockBalanceMockMvc.perform(post("/api/stock-balances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockBalance)))
            .andExpect(status().isBadRequest());

        // Validate the StockBalance in the database
        List<StockBalance> stockBalanceList = stockBalanceRepository.findAll();
        assertThat(stockBalanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSubInventoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockBalanceRepository.findAll().size();
        // set the field null
        stockBalance.setSubInventory(null);

        // Create the StockBalance, which fails.

        restStockBalanceMockMvc.perform(post("/api/stock-balances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockBalance)))
            .andExpect(status().isBadRequest());

        List<StockBalance> stockBalanceList = stockBalanceRepository.findAll();
        assertThat(stockBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityOnhandIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockBalanceRepository.findAll().size();
        // set the field null
        stockBalance.setQuantityOnhand(null);

        // Create the StockBalance, which fails.

        restStockBalanceMockMvc.perform(post("/api/stock-balances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockBalance)))
            .andExpect(status().isBadRequest());

        List<StockBalance> stockBalanceList = stockBalanceRepository.findAll();
        assertThat(stockBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityReservedIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockBalanceRepository.findAll().size();
        // set the field null
        stockBalance.setQuantityReserved(null);

        // Create the StockBalance, which fails.

        restStockBalanceMockMvc.perform(post("/api/stock-balances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockBalance)))
            .andExpect(status().isBadRequest());

        List<StockBalance> stockBalanceList = stockBalanceRepository.findAll();
        assertThat(stockBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockBalances() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList
        restStockBalanceMockMvc.perform(get("/api/stock-balances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].subInventory").value(hasItem(DEFAULT_SUB_INVENTORY.toString())))
            .andExpect(jsonPath("$.[*].quantityOnhand").value(hasItem(DEFAULT_QUANTITY_ONHAND)))
            .andExpect(jsonPath("$.[*].quantityReserved").value(hasItem(DEFAULT_QUANTITY_RESERVED)));
    }
    
    @Test
    @Transactional
    public void getStockBalance() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get the stockBalance
        restStockBalanceMockMvc.perform(get("/api/stock-balances/{id}", stockBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockBalance.getId().intValue()))
            .andExpect(jsonPath("$.subInventory").value(DEFAULT_SUB_INVENTORY.toString()))
            .andExpect(jsonPath("$.quantityOnhand").value(DEFAULT_QUANTITY_ONHAND))
            .andExpect(jsonPath("$.quantityReserved").value(DEFAULT_QUANTITY_RESERVED));
    }


    @Test
    @Transactional
    public void getStockBalancesByIdFiltering() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        Long id = stockBalance.getId();

        defaultStockBalanceShouldBeFound("id.equals=" + id);
        defaultStockBalanceShouldNotBeFound("id.notEquals=" + id);

        defaultStockBalanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockBalanceShouldNotBeFound("id.greaterThan=" + id);

        defaultStockBalanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockBalanceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockBalancesBySubInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where subInventory equals to DEFAULT_SUB_INVENTORY
        defaultStockBalanceShouldBeFound("subInventory.equals=" + DEFAULT_SUB_INVENTORY);

        // Get all the stockBalanceList where subInventory equals to UPDATED_SUB_INVENTORY
        defaultStockBalanceShouldNotBeFound("subInventory.equals=" + UPDATED_SUB_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllStockBalancesBySubInventoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where subInventory not equals to DEFAULT_SUB_INVENTORY
        defaultStockBalanceShouldNotBeFound("subInventory.notEquals=" + DEFAULT_SUB_INVENTORY);

        // Get all the stockBalanceList where subInventory not equals to UPDATED_SUB_INVENTORY
        defaultStockBalanceShouldBeFound("subInventory.notEquals=" + UPDATED_SUB_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllStockBalancesBySubInventoryIsInShouldWork() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where subInventory in DEFAULT_SUB_INVENTORY or UPDATED_SUB_INVENTORY
        defaultStockBalanceShouldBeFound("subInventory.in=" + DEFAULT_SUB_INVENTORY + "," + UPDATED_SUB_INVENTORY);

        // Get all the stockBalanceList where subInventory equals to UPDATED_SUB_INVENTORY
        defaultStockBalanceShouldNotBeFound("subInventory.in=" + UPDATED_SUB_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllStockBalancesBySubInventoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where subInventory is not null
        defaultStockBalanceShouldBeFound("subInventory.specified=true");

        // Get all the stockBalanceList where subInventory is null
        defaultStockBalanceShouldNotBeFound("subInventory.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityOnhandIsEqualToSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityOnhand equals to DEFAULT_QUANTITY_ONHAND
        defaultStockBalanceShouldBeFound("quantityOnhand.equals=" + DEFAULT_QUANTITY_ONHAND);

        // Get all the stockBalanceList where quantityOnhand equals to UPDATED_QUANTITY_ONHAND
        defaultStockBalanceShouldNotBeFound("quantityOnhand.equals=" + UPDATED_QUANTITY_ONHAND);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityOnhandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityOnhand not equals to DEFAULT_QUANTITY_ONHAND
        defaultStockBalanceShouldNotBeFound("quantityOnhand.notEquals=" + DEFAULT_QUANTITY_ONHAND);

        // Get all the stockBalanceList where quantityOnhand not equals to UPDATED_QUANTITY_ONHAND
        defaultStockBalanceShouldBeFound("quantityOnhand.notEquals=" + UPDATED_QUANTITY_ONHAND);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityOnhandIsInShouldWork() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityOnhand in DEFAULT_QUANTITY_ONHAND or UPDATED_QUANTITY_ONHAND
        defaultStockBalanceShouldBeFound("quantityOnhand.in=" + DEFAULT_QUANTITY_ONHAND + "," + UPDATED_QUANTITY_ONHAND);

        // Get all the stockBalanceList where quantityOnhand equals to UPDATED_QUANTITY_ONHAND
        defaultStockBalanceShouldNotBeFound("quantityOnhand.in=" + UPDATED_QUANTITY_ONHAND);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityOnhandIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityOnhand is not null
        defaultStockBalanceShouldBeFound("quantityOnhand.specified=true");

        // Get all the stockBalanceList where quantityOnhand is null
        defaultStockBalanceShouldNotBeFound("quantityOnhand.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityOnhandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityOnhand is greater than or equal to DEFAULT_QUANTITY_ONHAND
        defaultStockBalanceShouldBeFound("quantityOnhand.greaterThanOrEqual=" + DEFAULT_QUANTITY_ONHAND);

        // Get all the stockBalanceList where quantityOnhand is greater than or equal to UPDATED_QUANTITY_ONHAND
        defaultStockBalanceShouldNotBeFound("quantityOnhand.greaterThanOrEqual=" + UPDATED_QUANTITY_ONHAND);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityOnhandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityOnhand is less than or equal to DEFAULT_QUANTITY_ONHAND
        defaultStockBalanceShouldBeFound("quantityOnhand.lessThanOrEqual=" + DEFAULT_QUANTITY_ONHAND);

        // Get all the stockBalanceList where quantityOnhand is less than or equal to SMALLER_QUANTITY_ONHAND
        defaultStockBalanceShouldNotBeFound("quantityOnhand.lessThanOrEqual=" + SMALLER_QUANTITY_ONHAND);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityOnhandIsLessThanSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityOnhand is less than DEFAULT_QUANTITY_ONHAND
        defaultStockBalanceShouldNotBeFound("quantityOnhand.lessThan=" + DEFAULT_QUANTITY_ONHAND);

        // Get all the stockBalanceList where quantityOnhand is less than UPDATED_QUANTITY_ONHAND
        defaultStockBalanceShouldBeFound("quantityOnhand.lessThan=" + UPDATED_QUANTITY_ONHAND);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityOnhandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityOnhand is greater than DEFAULT_QUANTITY_ONHAND
        defaultStockBalanceShouldNotBeFound("quantityOnhand.greaterThan=" + DEFAULT_QUANTITY_ONHAND);

        // Get all the stockBalanceList where quantityOnhand is greater than SMALLER_QUANTITY_ONHAND
        defaultStockBalanceShouldBeFound("quantityOnhand.greaterThan=" + SMALLER_QUANTITY_ONHAND);
    }


    @Test
    @Transactional
    public void getAllStockBalancesByQuantityReservedIsEqualToSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityReserved equals to DEFAULT_QUANTITY_RESERVED
        defaultStockBalanceShouldBeFound("quantityReserved.equals=" + DEFAULT_QUANTITY_RESERVED);

        // Get all the stockBalanceList where quantityReserved equals to UPDATED_QUANTITY_RESERVED
        defaultStockBalanceShouldNotBeFound("quantityReserved.equals=" + UPDATED_QUANTITY_RESERVED);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityReservedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityReserved not equals to DEFAULT_QUANTITY_RESERVED
        defaultStockBalanceShouldNotBeFound("quantityReserved.notEquals=" + DEFAULT_QUANTITY_RESERVED);

        // Get all the stockBalanceList where quantityReserved not equals to UPDATED_QUANTITY_RESERVED
        defaultStockBalanceShouldBeFound("quantityReserved.notEquals=" + UPDATED_QUANTITY_RESERVED);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityReservedIsInShouldWork() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityReserved in DEFAULT_QUANTITY_RESERVED or UPDATED_QUANTITY_RESERVED
        defaultStockBalanceShouldBeFound("quantityReserved.in=" + DEFAULT_QUANTITY_RESERVED + "," + UPDATED_QUANTITY_RESERVED);

        // Get all the stockBalanceList where quantityReserved equals to UPDATED_QUANTITY_RESERVED
        defaultStockBalanceShouldNotBeFound("quantityReserved.in=" + UPDATED_QUANTITY_RESERVED);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityReservedIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityReserved is not null
        defaultStockBalanceShouldBeFound("quantityReserved.specified=true");

        // Get all the stockBalanceList where quantityReserved is null
        defaultStockBalanceShouldNotBeFound("quantityReserved.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityReservedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityReserved is greater than or equal to DEFAULT_QUANTITY_RESERVED
        defaultStockBalanceShouldBeFound("quantityReserved.greaterThanOrEqual=" + DEFAULT_QUANTITY_RESERVED);

        // Get all the stockBalanceList where quantityReserved is greater than or equal to UPDATED_QUANTITY_RESERVED
        defaultStockBalanceShouldNotBeFound("quantityReserved.greaterThanOrEqual=" + UPDATED_QUANTITY_RESERVED);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityReservedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityReserved is less than or equal to DEFAULT_QUANTITY_RESERVED
        defaultStockBalanceShouldBeFound("quantityReserved.lessThanOrEqual=" + DEFAULT_QUANTITY_RESERVED);

        // Get all the stockBalanceList where quantityReserved is less than or equal to SMALLER_QUANTITY_RESERVED
        defaultStockBalanceShouldNotBeFound("quantityReserved.lessThanOrEqual=" + SMALLER_QUANTITY_RESERVED);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityReservedIsLessThanSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityReserved is less than DEFAULT_QUANTITY_RESERVED
        defaultStockBalanceShouldNotBeFound("quantityReserved.lessThan=" + DEFAULT_QUANTITY_RESERVED);

        // Get all the stockBalanceList where quantityReserved is less than UPDATED_QUANTITY_RESERVED
        defaultStockBalanceShouldBeFound("quantityReserved.lessThan=" + UPDATED_QUANTITY_RESERVED);
    }

    @Test
    @Transactional
    public void getAllStockBalancesByQuantityReservedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockBalanceRepository.saveAndFlush(stockBalance);

        // Get all the stockBalanceList where quantityReserved is greater than DEFAULT_QUANTITY_RESERVED
        defaultStockBalanceShouldNotBeFound("quantityReserved.greaterThan=" + DEFAULT_QUANTITY_RESERVED);

        // Get all the stockBalanceList where quantityReserved is greater than SMALLER_QUANTITY_RESERVED
        defaultStockBalanceShouldBeFound("quantityReserved.greaterThan=" + SMALLER_QUANTITY_RESERVED);
    }


    @Test
    @Transactional
    public void getAllStockBalancesByStockItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        StockItem stockItem = stockBalance.getStockItem();
        stockBalanceRepository.saveAndFlush(stockBalance);
        Long stockItemId = stockItem.getId();

        // Get all the stockBalanceList where stockItem equals to stockItemId
        defaultStockBalanceShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the stockBalanceList where stockItem equals to stockItemId + 1
        defaultStockBalanceShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockBalanceShouldBeFound(String filter) throws Exception {
        restStockBalanceMockMvc.perform(get("/api/stock-balances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].subInventory").value(hasItem(DEFAULT_SUB_INVENTORY.toString())))
            .andExpect(jsonPath("$.[*].quantityOnhand").value(hasItem(DEFAULT_QUANTITY_ONHAND)))
            .andExpect(jsonPath("$.[*].quantityReserved").value(hasItem(DEFAULT_QUANTITY_RESERVED)));

        // Check, that the count call also returns 1
        restStockBalanceMockMvc.perform(get("/api/stock-balances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockBalanceShouldNotBeFound(String filter) throws Exception {
        restStockBalanceMockMvc.perform(get("/api/stock-balances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockBalanceMockMvc.perform(get("/api/stock-balances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockBalance() throws Exception {
        // Get the stockBalance
        restStockBalanceMockMvc.perform(get("/api/stock-balances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockBalance() throws Exception {
        // Initialize the database
        stockBalanceService.save(stockBalance);

        int databaseSizeBeforeUpdate = stockBalanceRepository.findAll().size();

        // Update the stockBalance
        StockBalance updatedStockBalance = stockBalanceRepository.findById(stockBalance.getId()).get();
        // Disconnect from session so that the updates on updatedStockBalance are not directly saved in db
        em.detach(updatedStockBalance);
        updatedStockBalance
            .subInventory(UPDATED_SUB_INVENTORY)
            .quantityOnhand(UPDATED_QUANTITY_ONHAND)
            .quantityReserved(UPDATED_QUANTITY_RESERVED);

        restStockBalanceMockMvc.perform(put("/api/stock-balances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStockBalance)))
            .andExpect(status().isOk());

        // Validate the StockBalance in the database
        List<StockBalance> stockBalanceList = stockBalanceRepository.findAll();
        assertThat(stockBalanceList).hasSize(databaseSizeBeforeUpdate);
        StockBalance testStockBalance = stockBalanceList.get(stockBalanceList.size() - 1);
        assertThat(testStockBalance.getSubInventory()).isEqualTo(UPDATED_SUB_INVENTORY);
        assertThat(testStockBalance.getQuantityOnhand()).isEqualTo(UPDATED_QUANTITY_ONHAND);
        assertThat(testStockBalance.getQuantityReserved()).isEqualTo(UPDATED_QUANTITY_RESERVED);
    }

    @Test
    @Transactional
    public void updateNonExistingStockBalance() throws Exception {
        int databaseSizeBeforeUpdate = stockBalanceRepository.findAll().size();

        // Create the StockBalance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockBalanceMockMvc.perform(put("/api/stock-balances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockBalance)))
            .andExpect(status().isBadRequest());

        // Validate the StockBalance in the database
        List<StockBalance> stockBalanceList = stockBalanceRepository.findAll();
        assertThat(stockBalanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockBalance() throws Exception {
        // Initialize the database
        stockBalanceService.save(stockBalance);

        int databaseSizeBeforeDelete = stockBalanceRepository.findAll().size();

        // Delete the stockBalance
        restStockBalanceMockMvc.perform(delete("/api/stock-balances/{id}", stockBalance.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockBalance> stockBalanceList = stockBalanceRepository.findAll();
        assertThat(stockBalanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
