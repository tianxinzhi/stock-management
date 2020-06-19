package com.hkt.btu.web.rest;

import com.hkt.btu.StockManagementApp;
import com.hkt.btu.config.TestSecurityConfiguration;
import com.hkt.btu.domain.StockItem;
import com.hkt.btu.repository.StockItemRepository;
import com.hkt.btu.service.StockItemService;
import com.hkt.btu.service.dto.StockItemCriteria;
import com.hkt.btu.service.StockItemQueryService;

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

/**
 * Integration tests for the {@link StockItemResource} REST controller.
 */
@SpringBootTest(classes = { StockManagementApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class StockItemResourceIT {

    private static final String DEFAULT_ITEM_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private StockItemRepository stockItemRepository;

    @Autowired
    private StockItemService stockItemService;

    @Autowired
    private StockItemQueryService stockItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockItemMockMvc;

    private StockItem stockItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItem createEntity(EntityManager em) {
        StockItem stockItem = new StockItem()
            .itemNumber(DEFAULT_ITEM_NUMBER)
            .itemDescription(DEFAULT_ITEM_DESCRIPTION);
        return stockItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItem createUpdatedEntity(EntityManager em) {
        StockItem stockItem = new StockItem()
            .itemNumber(UPDATED_ITEM_NUMBER)
            .itemDescription(UPDATED_ITEM_DESCRIPTION);
        return stockItem;
    }

    @BeforeEach
    public void initTest() {
        stockItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItem() throws Exception {
        int databaseSizeBeforeCreate = stockItemRepository.findAll().size();

        // Create the StockItem
        restStockItemMockMvc.perform(post("/api/stock-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isCreated());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeCreate + 1);
        StockItem testStockItem = stockItemList.get(stockItemList.size() - 1);
        assertThat(testStockItem.getItemNumber()).isEqualTo(DEFAULT_ITEM_NUMBER);
        assertThat(testStockItem.getItemDescription()).isEqualTo(DEFAULT_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createStockItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemRepository.findAll().size();

        // Create the StockItem with an existing ID
        stockItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemMockMvc.perform(post("/api/stock-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isBadRequest());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkItemNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemRepository.findAll().size();
        // set the field null
        stockItem.setItemNumber(null);

        // Create the StockItem, which fails.

        restStockItemMockMvc.perform(post("/api/stock-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isBadRequest());

        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItemDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemRepository.findAll().size();
        // set the field null
        stockItem.setItemDescription(null);

        // Create the StockItem, which fails.

        restStockItemMockMvc.perform(post("/api/stock-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isBadRequest());

        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItems() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList
        restStockItemMockMvc.perform(get("/api/stock-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemNumber").value(hasItem(DEFAULT_ITEM_NUMBER)))
            .andExpect(jsonPath("$.[*].itemDescription").value(hasItem(DEFAULT_ITEM_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getStockItem() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get the stockItem
        restStockItemMockMvc.perform(get("/api/stock-items/{id}", stockItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockItem.getId().intValue()))
            .andExpect(jsonPath("$.itemNumber").value(DEFAULT_ITEM_NUMBER))
            .andExpect(jsonPath("$.itemDescription").value(DEFAULT_ITEM_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getStockItemsByIdFiltering() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        Long id = stockItem.getId();

        defaultStockItemShouldBeFound("id.equals=" + id);
        defaultStockItemShouldNotBeFound("id.notEquals=" + id);

        defaultStockItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockItemShouldNotBeFound("id.greaterThan=" + id);

        defaultStockItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemNumber equals to DEFAULT_ITEM_NUMBER
        defaultStockItemShouldBeFound("itemNumber.equals=" + DEFAULT_ITEM_NUMBER);

        // Get all the stockItemList where itemNumber equals to UPDATED_ITEM_NUMBER
        defaultStockItemShouldNotBeFound("itemNumber.equals=" + UPDATED_ITEM_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemNumber not equals to DEFAULT_ITEM_NUMBER
        defaultStockItemShouldNotBeFound("itemNumber.notEquals=" + DEFAULT_ITEM_NUMBER);

        // Get all the stockItemList where itemNumber not equals to UPDATED_ITEM_NUMBER
        defaultStockItemShouldBeFound("itemNumber.notEquals=" + UPDATED_ITEM_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemNumberIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemNumber in DEFAULT_ITEM_NUMBER or UPDATED_ITEM_NUMBER
        defaultStockItemShouldBeFound("itemNumber.in=" + DEFAULT_ITEM_NUMBER + "," + UPDATED_ITEM_NUMBER);

        // Get all the stockItemList where itemNumber equals to UPDATED_ITEM_NUMBER
        defaultStockItemShouldNotBeFound("itemNumber.in=" + UPDATED_ITEM_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemNumber is not null
        defaultStockItemShouldBeFound("itemNumber.specified=true");

        // Get all the stockItemList where itemNumber is null
        defaultStockItemShouldNotBeFound("itemNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByItemNumberContainsSomething() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemNumber contains DEFAULT_ITEM_NUMBER
        defaultStockItemShouldBeFound("itemNumber.contains=" + DEFAULT_ITEM_NUMBER);

        // Get all the stockItemList where itemNumber contains UPDATED_ITEM_NUMBER
        defaultStockItemShouldNotBeFound("itemNumber.contains=" + UPDATED_ITEM_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemNumberNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemNumber does not contain DEFAULT_ITEM_NUMBER
        defaultStockItemShouldNotBeFound("itemNumber.doesNotContain=" + DEFAULT_ITEM_NUMBER);

        // Get all the stockItemList where itemNumber does not contain UPDATED_ITEM_NUMBER
        defaultStockItemShouldBeFound("itemNumber.doesNotContain=" + UPDATED_ITEM_NUMBER);
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemDescription equals to DEFAULT_ITEM_DESCRIPTION
        defaultStockItemShouldBeFound("itemDescription.equals=" + DEFAULT_ITEM_DESCRIPTION);

        // Get all the stockItemList where itemDescription equals to UPDATED_ITEM_DESCRIPTION
        defaultStockItemShouldNotBeFound("itemDescription.equals=" + UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemDescription not equals to DEFAULT_ITEM_DESCRIPTION
        defaultStockItemShouldNotBeFound("itemDescription.notEquals=" + DEFAULT_ITEM_DESCRIPTION);

        // Get all the stockItemList where itemDescription not equals to UPDATED_ITEM_DESCRIPTION
        defaultStockItemShouldBeFound("itemDescription.notEquals=" + UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemDescription in DEFAULT_ITEM_DESCRIPTION or UPDATED_ITEM_DESCRIPTION
        defaultStockItemShouldBeFound("itemDescription.in=" + DEFAULT_ITEM_DESCRIPTION + "," + UPDATED_ITEM_DESCRIPTION);

        // Get all the stockItemList where itemDescription equals to UPDATED_ITEM_DESCRIPTION
        defaultStockItemShouldNotBeFound("itemDescription.in=" + UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemDescription is not null
        defaultStockItemShouldBeFound("itemDescription.specified=true");

        // Get all the stockItemList where itemDescription is null
        defaultStockItemShouldNotBeFound("itemDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByItemDescriptionContainsSomething() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemDescription contains DEFAULT_ITEM_DESCRIPTION
        defaultStockItemShouldBeFound("itemDescription.contains=" + DEFAULT_ITEM_DESCRIPTION);

        // Get all the stockItemList where itemDescription contains UPDATED_ITEM_DESCRIPTION
        defaultStockItemShouldNotBeFound("itemDescription.contains=" + UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList where itemDescription does not contain DEFAULT_ITEM_DESCRIPTION
        defaultStockItemShouldNotBeFound("itemDescription.doesNotContain=" + DEFAULT_ITEM_DESCRIPTION);

        // Get all the stockItemList where itemDescription does not contain UPDATED_ITEM_DESCRIPTION
        defaultStockItemShouldBeFound("itemDescription.doesNotContain=" + UPDATED_ITEM_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockItemShouldBeFound(String filter) throws Exception {
        restStockItemMockMvc.perform(get("/api/stock-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemNumber").value(hasItem(DEFAULT_ITEM_NUMBER)))
            .andExpect(jsonPath("$.[*].itemDescription").value(hasItem(DEFAULT_ITEM_DESCRIPTION)));

        // Check, that the count call also returns 1
        restStockItemMockMvc.perform(get("/api/stock-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockItemShouldNotBeFound(String filter) throws Exception {
        restStockItemMockMvc.perform(get("/api/stock-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockItemMockMvc.perform(get("/api/stock-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockItem() throws Exception {
        // Get the stockItem
        restStockItemMockMvc.perform(get("/api/stock-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItem() throws Exception {
        // Initialize the database
        stockItemService.save(stockItem);

        int databaseSizeBeforeUpdate = stockItemRepository.findAll().size();

        // Update the stockItem
        StockItem updatedStockItem = stockItemRepository.findById(stockItem.getId()).get();
        // Disconnect from session so that the updates on updatedStockItem are not directly saved in db
        em.detach(updatedStockItem);
        updatedStockItem
            .itemNumber(UPDATED_ITEM_NUMBER)
            .itemDescription(UPDATED_ITEM_DESCRIPTION);

        restStockItemMockMvc.perform(put("/api/stock-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStockItem)))
            .andExpect(status().isOk());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeUpdate);
        StockItem testStockItem = stockItemList.get(stockItemList.size() - 1);
        assertThat(testStockItem.getItemNumber()).isEqualTo(UPDATED_ITEM_NUMBER);
        assertThat(testStockItem.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItem() throws Exception {
        int databaseSizeBeforeUpdate = stockItemRepository.findAll().size();

        // Create the StockItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemMockMvc.perform(put("/api/stock-items").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItem)))
            .andExpect(status().isBadRequest());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItem() throws Exception {
        // Initialize the database
        stockItemService.save(stockItem);

        int databaseSizeBeforeDelete = stockItemRepository.findAll().size();

        // Delete the stockItem
        restStockItemMockMvc.perform(delete("/api/stock-items/{id}", stockItem.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
