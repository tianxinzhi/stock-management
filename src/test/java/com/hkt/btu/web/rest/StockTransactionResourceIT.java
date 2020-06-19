package com.hkt.btu.web.rest;

import com.hkt.btu.StockManagementApp;
import com.hkt.btu.config.TestSecurityConfiguration;
import com.hkt.btu.domain.StockTransaction;
import com.hkt.btu.domain.StockItem;
import com.hkt.btu.repository.StockTransactionRepository;
import com.hkt.btu.service.StockTransactionService;
import com.hkt.btu.service.dto.StockTransactionCriteria;
import com.hkt.btu.service.StockTransactionQueryService;

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
import com.hkt.btu.domain.enumeration.Subinventory;
import com.hkt.btu.domain.enumeration.TransactionType;
/**
 * Integration tests for the {@link StockTransactionResource} REST controller.
 */
@SpringBootTest(classes = { StockManagementApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class StockTransactionResourceIT {

    private static final Subinventory DEFAULT_SUBINVENTORY_FROM = Subinventory.FG;
    private static final Subinventory UPDATED_SUBINVENTORY_FROM = Subinventory.STAGING;

    private static final Subinventory DEFAULT_SUBINVENTORY_TO = Subinventory.FG;
    private static final Subinventory UPDATED_SUBINVENTORY_TO = Subinventory.STAGING;

    private static final TransactionType DEFAULT_TRANSACTION_TYPE = TransactionType.IN;
    private static final TransactionType UPDATED_TRANSACTION_TYPE = TransactionType.OUT;

    private static final Integer DEFAULT_TRANSACTION_QUANTITY = 1;
    private static final Integer UPDATED_TRANSACTION_QUANTITY = 2;
    private static final Integer SMALLER_TRANSACTION_QUANTITY = 1 - 1;

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    private StockTransactionService stockTransactionService;

    @Autowired
    private StockTransactionQueryService stockTransactionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockTransactionMockMvc;

    private StockTransaction stockTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockTransaction createEntity(EntityManager em) {
        StockTransaction stockTransaction = new StockTransaction()
            .subinventoryFrom(DEFAULT_SUBINVENTORY_FROM)
            .subinventoryTo(DEFAULT_SUBINVENTORY_TO)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .transactionQuantity(DEFAULT_TRANSACTION_QUANTITY)
            .reference(DEFAULT_REFERENCE);
        // Add required entity
        StockItem stockItem;
        if (TestUtil.findAll(em, StockItem.class).isEmpty()) {
            stockItem = StockItemResourceIT.createEntity(em);
            em.persist(stockItem);
            em.flush();
        } else {
            stockItem = TestUtil.findAll(em, StockItem.class).get(0);
        }
        stockTransaction.setStockItem(stockItem);
        return stockTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockTransaction createUpdatedEntity(EntityManager em) {
        StockTransaction stockTransaction = new StockTransaction()
            .subinventoryFrom(UPDATED_SUBINVENTORY_FROM)
            .subinventoryTo(UPDATED_SUBINVENTORY_TO)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionQuantity(UPDATED_TRANSACTION_QUANTITY)
            .reference(UPDATED_REFERENCE);
        // Add required entity
        StockItem stockItem;
        if (TestUtil.findAll(em, StockItem.class).isEmpty()) {
            stockItem = StockItemResourceIT.createUpdatedEntity(em);
            em.persist(stockItem);
            em.flush();
        } else {
            stockItem = TestUtil.findAll(em, StockItem.class).get(0);
        }
        stockTransaction.setStockItem(stockItem);
        return stockTransaction;
    }

    @BeforeEach
    public void initTest() {
        stockTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockTransaction() throws Exception {
        int databaseSizeBeforeCreate = stockTransactionRepository.findAll().size();

        // Create the StockTransaction
        restStockTransactionMockMvc.perform(post("/api/stock-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isCreated());

        // Validate the StockTransaction in the database
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        StockTransaction testStockTransaction = stockTransactionList.get(stockTransactionList.size() - 1);
        assertThat(testStockTransaction.getSubinventoryFrom()).isEqualTo(DEFAULT_SUBINVENTORY_FROM);
        assertThat(testStockTransaction.getSubinventoryTo()).isEqualTo(DEFAULT_SUBINVENTORY_TO);
        assertThat(testStockTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testStockTransaction.getTransactionQuantity()).isEqualTo(DEFAULT_TRANSACTION_QUANTITY);
        assertThat(testStockTransaction.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createStockTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockTransactionRepository.findAll().size();

        // Create the StockTransaction with an existing ID
        stockTransaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockTransactionMockMvc.perform(post("/api/stock-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the StockTransaction in the database
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockTransactionRepository.findAll().size();
        // set the field null
        stockTransaction.setTransactionType(null);

        // Create the StockTransaction, which fails.

        restStockTransactionMockMvc.perform(post("/api/stock-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isBadRequest());

        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockTransactionRepository.findAll().size();
        // set the field null
        stockTransaction.setTransactionQuantity(null);

        // Create the StockTransaction, which fails.

        restStockTransactionMockMvc.perform(post("/api/stock-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isBadRequest());

        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockTransactions() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList
        restStockTransactionMockMvc.perform(get("/api/stock-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].subinventoryFrom").value(hasItem(DEFAULT_SUBINVENTORY_FROM.toString())))
            .andExpect(jsonPath("$.[*].subinventoryTo").value(hasItem(DEFAULT_SUBINVENTORY_TO.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].transactionQuantity").value(hasItem(DEFAULT_TRANSACTION_QUANTITY)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));
    }
    
    @Test
    @Transactional
    public void getStockTransaction() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get the stockTransaction
        restStockTransactionMockMvc.perform(get("/api/stock-transactions/{id}", stockTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockTransaction.getId().intValue()))
            .andExpect(jsonPath("$.subinventoryFrom").value(DEFAULT_SUBINVENTORY_FROM.toString()))
            .andExpect(jsonPath("$.subinventoryTo").value(DEFAULT_SUBINVENTORY_TO.toString()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()))
            .andExpect(jsonPath("$.transactionQuantity").value(DEFAULT_TRANSACTION_QUANTITY))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE));
    }


    @Test
    @Transactional
    public void getStockTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        Long id = stockTransaction.getId();

        defaultStockTransactionShouldBeFound("id.equals=" + id);
        defaultStockTransactionShouldNotBeFound("id.notEquals=" + id);

        defaultStockTransactionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockTransactionShouldNotBeFound("id.greaterThan=" + id);

        defaultStockTransactionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockTransactionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockTransactionsBySubinventoryFromIsEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where subinventoryFrom equals to DEFAULT_SUBINVENTORY_FROM
        defaultStockTransactionShouldBeFound("subinventoryFrom.equals=" + DEFAULT_SUBINVENTORY_FROM);

        // Get all the stockTransactionList where subinventoryFrom equals to UPDATED_SUBINVENTORY_FROM
        defaultStockTransactionShouldNotBeFound("subinventoryFrom.equals=" + UPDATED_SUBINVENTORY_FROM);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsBySubinventoryFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where subinventoryFrom not equals to DEFAULT_SUBINVENTORY_FROM
        defaultStockTransactionShouldNotBeFound("subinventoryFrom.notEquals=" + DEFAULT_SUBINVENTORY_FROM);

        // Get all the stockTransactionList where subinventoryFrom not equals to UPDATED_SUBINVENTORY_FROM
        defaultStockTransactionShouldBeFound("subinventoryFrom.notEquals=" + UPDATED_SUBINVENTORY_FROM);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsBySubinventoryFromIsInShouldWork() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where subinventoryFrom in DEFAULT_SUBINVENTORY_FROM or UPDATED_SUBINVENTORY_FROM
        defaultStockTransactionShouldBeFound("subinventoryFrom.in=" + DEFAULT_SUBINVENTORY_FROM + "," + UPDATED_SUBINVENTORY_FROM);

        // Get all the stockTransactionList where subinventoryFrom equals to UPDATED_SUBINVENTORY_FROM
        defaultStockTransactionShouldNotBeFound("subinventoryFrom.in=" + UPDATED_SUBINVENTORY_FROM);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsBySubinventoryFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where subinventoryFrom is not null
        defaultStockTransactionShouldBeFound("subinventoryFrom.specified=true");

        // Get all the stockTransactionList where subinventoryFrom is null
        defaultStockTransactionShouldNotBeFound("subinventoryFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockTransactionsBySubinventoryToIsEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where subinventoryTo equals to DEFAULT_SUBINVENTORY_TO
        defaultStockTransactionShouldBeFound("subinventoryTo.equals=" + DEFAULT_SUBINVENTORY_TO);

        // Get all the stockTransactionList where subinventoryTo equals to UPDATED_SUBINVENTORY_TO
        defaultStockTransactionShouldNotBeFound("subinventoryTo.equals=" + UPDATED_SUBINVENTORY_TO);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsBySubinventoryToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where subinventoryTo not equals to DEFAULT_SUBINVENTORY_TO
        defaultStockTransactionShouldNotBeFound("subinventoryTo.notEquals=" + DEFAULT_SUBINVENTORY_TO);

        // Get all the stockTransactionList where subinventoryTo not equals to UPDATED_SUBINVENTORY_TO
        defaultStockTransactionShouldBeFound("subinventoryTo.notEquals=" + UPDATED_SUBINVENTORY_TO);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsBySubinventoryToIsInShouldWork() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where subinventoryTo in DEFAULT_SUBINVENTORY_TO or UPDATED_SUBINVENTORY_TO
        defaultStockTransactionShouldBeFound("subinventoryTo.in=" + DEFAULT_SUBINVENTORY_TO + "," + UPDATED_SUBINVENTORY_TO);

        // Get all the stockTransactionList where subinventoryTo equals to UPDATED_SUBINVENTORY_TO
        defaultStockTransactionShouldNotBeFound("subinventoryTo.in=" + UPDATED_SUBINVENTORY_TO);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsBySubinventoryToIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where subinventoryTo is not null
        defaultStockTransactionShouldBeFound("subinventoryTo.specified=true");

        // Get all the stockTransactionList where subinventoryTo is null
        defaultStockTransactionShouldNotBeFound("subinventoryTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionType equals to DEFAULT_TRANSACTION_TYPE
        defaultStockTransactionShouldBeFound("transactionType.equals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the stockTransactionList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultStockTransactionShouldNotBeFound("transactionType.equals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionType not equals to DEFAULT_TRANSACTION_TYPE
        defaultStockTransactionShouldNotBeFound("transactionType.notEquals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the stockTransactionList where transactionType not equals to UPDATED_TRANSACTION_TYPE
        defaultStockTransactionShouldBeFound("transactionType.notEquals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionType in DEFAULT_TRANSACTION_TYPE or UPDATED_TRANSACTION_TYPE
        defaultStockTransactionShouldBeFound("transactionType.in=" + DEFAULT_TRANSACTION_TYPE + "," + UPDATED_TRANSACTION_TYPE);

        // Get all the stockTransactionList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultStockTransactionShouldNotBeFound("transactionType.in=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionType is not null
        defaultStockTransactionShouldBeFound("transactionType.specified=true");

        // Get all the stockTransactionList where transactionType is null
        defaultStockTransactionShouldNotBeFound("transactionType.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionQuantity equals to DEFAULT_TRANSACTION_QUANTITY
        defaultStockTransactionShouldBeFound("transactionQuantity.equals=" + DEFAULT_TRANSACTION_QUANTITY);

        // Get all the stockTransactionList where transactionQuantity equals to UPDATED_TRANSACTION_QUANTITY
        defaultStockTransactionShouldNotBeFound("transactionQuantity.equals=" + UPDATED_TRANSACTION_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionQuantity not equals to DEFAULT_TRANSACTION_QUANTITY
        defaultStockTransactionShouldNotBeFound("transactionQuantity.notEquals=" + DEFAULT_TRANSACTION_QUANTITY);

        // Get all the stockTransactionList where transactionQuantity not equals to UPDATED_TRANSACTION_QUANTITY
        defaultStockTransactionShouldBeFound("transactionQuantity.notEquals=" + UPDATED_TRANSACTION_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionQuantity in DEFAULT_TRANSACTION_QUANTITY or UPDATED_TRANSACTION_QUANTITY
        defaultStockTransactionShouldBeFound("transactionQuantity.in=" + DEFAULT_TRANSACTION_QUANTITY + "," + UPDATED_TRANSACTION_QUANTITY);

        // Get all the stockTransactionList where transactionQuantity equals to UPDATED_TRANSACTION_QUANTITY
        defaultStockTransactionShouldNotBeFound("transactionQuantity.in=" + UPDATED_TRANSACTION_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionQuantity is not null
        defaultStockTransactionShouldBeFound("transactionQuantity.specified=true");

        // Get all the stockTransactionList where transactionQuantity is null
        defaultStockTransactionShouldNotBeFound("transactionQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionQuantity is greater than or equal to DEFAULT_TRANSACTION_QUANTITY
        defaultStockTransactionShouldBeFound("transactionQuantity.greaterThanOrEqual=" + DEFAULT_TRANSACTION_QUANTITY);

        // Get all the stockTransactionList where transactionQuantity is greater than or equal to UPDATED_TRANSACTION_QUANTITY
        defaultStockTransactionShouldNotBeFound("transactionQuantity.greaterThanOrEqual=" + UPDATED_TRANSACTION_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionQuantity is less than or equal to DEFAULT_TRANSACTION_QUANTITY
        defaultStockTransactionShouldBeFound("transactionQuantity.lessThanOrEqual=" + DEFAULT_TRANSACTION_QUANTITY);

        // Get all the stockTransactionList where transactionQuantity is less than or equal to SMALLER_TRANSACTION_QUANTITY
        defaultStockTransactionShouldNotBeFound("transactionQuantity.lessThanOrEqual=" + SMALLER_TRANSACTION_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionQuantity is less than DEFAULT_TRANSACTION_QUANTITY
        defaultStockTransactionShouldNotBeFound("transactionQuantity.lessThan=" + DEFAULT_TRANSACTION_QUANTITY);

        // Get all the stockTransactionList where transactionQuantity is less than UPDATED_TRANSACTION_QUANTITY
        defaultStockTransactionShouldBeFound("transactionQuantity.lessThan=" + UPDATED_TRANSACTION_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByTransactionQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where transactionQuantity is greater than DEFAULT_TRANSACTION_QUANTITY
        defaultStockTransactionShouldNotBeFound("transactionQuantity.greaterThan=" + DEFAULT_TRANSACTION_QUANTITY);

        // Get all the stockTransactionList where transactionQuantity is greater than SMALLER_TRANSACTION_QUANTITY
        defaultStockTransactionShouldBeFound("transactionQuantity.greaterThan=" + SMALLER_TRANSACTION_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllStockTransactionsByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where reference equals to DEFAULT_REFERENCE
        defaultStockTransactionShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the stockTransactionList where reference equals to UPDATED_REFERENCE
        defaultStockTransactionShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where reference not equals to DEFAULT_REFERENCE
        defaultStockTransactionShouldNotBeFound("reference.notEquals=" + DEFAULT_REFERENCE);

        // Get all the stockTransactionList where reference not equals to UPDATED_REFERENCE
        defaultStockTransactionShouldBeFound("reference.notEquals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultStockTransactionShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the stockTransactionList where reference equals to UPDATED_REFERENCE
        defaultStockTransactionShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where reference is not null
        defaultStockTransactionShouldBeFound("reference.specified=true");

        // Get all the stockTransactionList where reference is null
        defaultStockTransactionShouldNotBeFound("reference.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockTransactionsByReferenceContainsSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where reference contains DEFAULT_REFERENCE
        defaultStockTransactionShouldBeFound("reference.contains=" + DEFAULT_REFERENCE);

        // Get all the stockTransactionList where reference contains UPDATED_REFERENCE
        defaultStockTransactionShouldNotBeFound("reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllStockTransactionsByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        stockTransactionRepository.saveAndFlush(stockTransaction);

        // Get all the stockTransactionList where reference does not contain DEFAULT_REFERENCE
        defaultStockTransactionShouldNotBeFound("reference.doesNotContain=" + DEFAULT_REFERENCE);

        // Get all the stockTransactionList where reference does not contain UPDATED_REFERENCE
        defaultStockTransactionShouldBeFound("reference.doesNotContain=" + UPDATED_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllStockTransactionsByStockItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        StockItem stockItem = stockTransaction.getStockItem();
        stockTransactionRepository.saveAndFlush(stockTransaction);
        Long stockItemId = stockItem.getId();

        // Get all the stockTransactionList where stockItem equals to stockItemId
        defaultStockTransactionShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the stockTransactionList where stockItem equals to stockItemId + 1
        defaultStockTransactionShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockTransactionShouldBeFound(String filter) throws Exception {
        restStockTransactionMockMvc.perform(get("/api/stock-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].subinventoryFrom").value(hasItem(DEFAULT_SUBINVENTORY_FROM.toString())))
            .andExpect(jsonPath("$.[*].subinventoryTo").value(hasItem(DEFAULT_SUBINVENTORY_TO.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].transactionQuantity").value(hasItem(DEFAULT_TRANSACTION_QUANTITY)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));

        // Check, that the count call also returns 1
        restStockTransactionMockMvc.perform(get("/api/stock-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockTransactionShouldNotBeFound(String filter) throws Exception {
        restStockTransactionMockMvc.perform(get("/api/stock-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockTransactionMockMvc.perform(get("/api/stock-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockTransaction() throws Exception {
        // Get the stockTransaction
        restStockTransactionMockMvc.perform(get("/api/stock-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockTransaction() throws Exception {
        // Initialize the database
        stockTransactionService.save(stockTransaction);

        int databaseSizeBeforeUpdate = stockTransactionRepository.findAll().size();

        // Update the stockTransaction
        StockTransaction updatedStockTransaction = stockTransactionRepository.findById(stockTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedStockTransaction are not directly saved in db
        em.detach(updatedStockTransaction);
        updatedStockTransaction
            .subinventoryFrom(UPDATED_SUBINVENTORY_FROM)
            .subinventoryTo(UPDATED_SUBINVENTORY_TO)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionQuantity(UPDATED_TRANSACTION_QUANTITY)
            .reference(UPDATED_REFERENCE);

        restStockTransactionMockMvc.perform(put("/api/stock-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStockTransaction)))
            .andExpect(status().isOk());

        // Validate the StockTransaction in the database
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeUpdate);
        StockTransaction testStockTransaction = stockTransactionList.get(stockTransactionList.size() - 1);
        assertThat(testStockTransaction.getSubinventoryFrom()).isEqualTo(UPDATED_SUBINVENTORY_FROM);
        assertThat(testStockTransaction.getSubinventoryTo()).isEqualTo(UPDATED_SUBINVENTORY_TO);
        assertThat(testStockTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testStockTransaction.getTransactionQuantity()).isEqualTo(UPDATED_TRANSACTION_QUANTITY);
        assertThat(testStockTransaction.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingStockTransaction() throws Exception {
        int databaseSizeBeforeUpdate = stockTransactionRepository.findAll().size();

        // Create the StockTransaction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockTransactionMockMvc.perform(put("/api/stock-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the StockTransaction in the database
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockTransaction() throws Exception {
        // Initialize the database
        stockTransactionService.save(stockTransaction);

        int databaseSizeBeforeDelete = stockTransactionRepository.findAll().size();

        // Delete the stockTransaction
        restStockTransactionMockMvc.perform(delete("/api/stock-transactions/{id}", stockTransaction.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAll();
        assertThat(stockTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
