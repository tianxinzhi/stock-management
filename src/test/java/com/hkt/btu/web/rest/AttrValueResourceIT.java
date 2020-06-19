package com.hkt.btu.web.rest;

import com.hkt.btu.StockManagementApp;
import com.hkt.btu.config.TestSecurityConfiguration;
import com.hkt.btu.domain.AttrValue;
import com.hkt.btu.repository.AttrValueRepository;

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
 * Integration tests for the {@link AttrValueResource} REST controller.
 */
@SpringBootTest(classes = { StockManagementApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class AttrValueResourceIT {

    private static final String DEFAULT_ATTR_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_OF_MEASURE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_OF_MEASURE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_FROM = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_TO = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TO = "BBBBBBBBBB";

    @Autowired
    private AttrValueRepository attrValueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttrValueMockMvc;

    private AttrValue attrValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttrValue createEntity(EntityManager em) {
        AttrValue attrValue = new AttrValue()
            .attrValue(DEFAULT_ATTR_VALUE)
            .unitOfMeasure(DEFAULT_UNIT_OF_MEASURE)
            .valueFrom(DEFAULT_VALUE_FROM)
            .valueTo(DEFAULT_VALUE_TO);
        return attrValue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttrValue createUpdatedEntity(EntityManager em) {
        AttrValue attrValue = new AttrValue()
            .attrValue(UPDATED_ATTR_VALUE)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .valueFrom(UPDATED_VALUE_FROM)
            .valueTo(UPDATED_VALUE_TO);
        return attrValue;
    }

    @BeforeEach
    public void initTest() {
        attrValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttrValue() throws Exception {
        int databaseSizeBeforeCreate = attrValueRepository.findAll().size();

        // Create the AttrValue
        restAttrValueMockMvc.perform(post("/api/attr-values").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attrValue)))
            .andExpect(status().isCreated());

        // Validate the AttrValue in the database
        List<AttrValue> attrValueList = attrValueRepository.findAll();
        assertThat(attrValueList).hasSize(databaseSizeBeforeCreate + 1);
        AttrValue testAttrValue = attrValueList.get(attrValueList.size() - 1);
        assertThat(testAttrValue.getAttrValue()).isEqualTo(DEFAULT_ATTR_VALUE);
        assertThat(testAttrValue.getUnitOfMeasure()).isEqualTo(DEFAULT_UNIT_OF_MEASURE);
        assertThat(testAttrValue.getValueFrom()).isEqualTo(DEFAULT_VALUE_FROM);
        assertThat(testAttrValue.getValueTo()).isEqualTo(DEFAULT_VALUE_TO);
    }

    @Test
    @Transactional
    public void createAttrValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attrValueRepository.findAll().size();

        // Create the AttrValue with an existing ID
        attrValue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttrValueMockMvc.perform(post("/api/attr-values").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attrValue)))
            .andExpect(status().isBadRequest());

        // Validate the AttrValue in the database
        List<AttrValue> attrValueList = attrValueRepository.findAll();
        assertThat(attrValueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttrValues() throws Exception {
        // Initialize the database
        attrValueRepository.saveAndFlush(attrValue);

        // Get all the attrValueList
        restAttrValueMockMvc.perform(get("/api/attr-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attrValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].attrValue").value(hasItem(DEFAULT_ATTR_VALUE)))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE)))
            .andExpect(jsonPath("$.[*].valueFrom").value(hasItem(DEFAULT_VALUE_FROM)))
            .andExpect(jsonPath("$.[*].valueTo").value(hasItem(DEFAULT_VALUE_TO)));
    }
    
    @Test
    @Transactional
    public void getAttrValue() throws Exception {
        // Initialize the database
        attrValueRepository.saveAndFlush(attrValue);

        // Get the attrValue
        restAttrValueMockMvc.perform(get("/api/attr-values/{id}", attrValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attrValue.getId().intValue()))
            .andExpect(jsonPath("$.attrValue").value(DEFAULT_ATTR_VALUE))
            .andExpect(jsonPath("$.unitOfMeasure").value(DEFAULT_UNIT_OF_MEASURE))
            .andExpect(jsonPath("$.valueFrom").value(DEFAULT_VALUE_FROM))
            .andExpect(jsonPath("$.valueTo").value(DEFAULT_VALUE_TO));
    }

    @Test
    @Transactional
    public void getNonExistingAttrValue() throws Exception {
        // Get the attrValue
        restAttrValueMockMvc.perform(get("/api/attr-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttrValue() throws Exception {
        // Initialize the database
        attrValueRepository.saveAndFlush(attrValue);

        int databaseSizeBeforeUpdate = attrValueRepository.findAll().size();

        // Update the attrValue
        AttrValue updatedAttrValue = attrValueRepository.findById(attrValue.getId()).get();
        // Disconnect from session so that the updates on updatedAttrValue are not directly saved in db
        em.detach(updatedAttrValue);
        updatedAttrValue
            .attrValue(UPDATED_ATTR_VALUE)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .valueFrom(UPDATED_VALUE_FROM)
            .valueTo(UPDATED_VALUE_TO);

        restAttrValueMockMvc.perform(put("/api/attr-values").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttrValue)))
            .andExpect(status().isOk());

        // Validate the AttrValue in the database
        List<AttrValue> attrValueList = attrValueRepository.findAll();
        assertThat(attrValueList).hasSize(databaseSizeBeforeUpdate);
        AttrValue testAttrValue = attrValueList.get(attrValueList.size() - 1);
        assertThat(testAttrValue.getAttrValue()).isEqualTo(UPDATED_ATTR_VALUE);
        assertThat(testAttrValue.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testAttrValue.getValueFrom()).isEqualTo(UPDATED_VALUE_FROM);
        assertThat(testAttrValue.getValueTo()).isEqualTo(UPDATED_VALUE_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingAttrValue() throws Exception {
        int databaseSizeBeforeUpdate = attrValueRepository.findAll().size();

        // Create the AttrValue

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttrValueMockMvc.perform(put("/api/attr-values").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attrValue)))
            .andExpect(status().isBadRequest());

        // Validate the AttrValue in the database
        List<AttrValue> attrValueList = attrValueRepository.findAll();
        assertThat(attrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttrValue() throws Exception {
        // Initialize the database
        attrValueRepository.saveAndFlush(attrValue);

        int databaseSizeBeforeDelete = attrValueRepository.findAll().size();

        // Delete the attrValue
        restAttrValueMockMvc.perform(delete("/api/attr-values/{id}", attrValue.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttrValue> attrValueList = attrValueRepository.findAll();
        assertThat(attrValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
