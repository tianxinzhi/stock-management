package com.hkt.btu.web.rest;

import com.hkt.btu.StockManagementApp;
import com.hkt.btu.config.TestSecurityConfiguration;
import com.hkt.btu.domain.Attr;
import com.hkt.btu.repository.AttrRepository;
import com.hkt.btu.service.AttrService;
import com.hkt.btu.service.dto.AttrDTO;
import com.hkt.btu.service.mapper.AttrMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AttrResource} REST controller.
 */
@SpringBootTest(classes = { StockManagementApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttrResourceIT {

    private static final String DEFAULT_ATTR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_DESC = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_DESC = "BBBBBBBBBB";

    @Autowired
    private AttrRepository attrRepository;

    @Mock
    private AttrRepository attrRepositoryMock;

    @Autowired
    private AttrMapper attrMapper;

    @Mock
    private AttrService attrServiceMock;

    @Autowired
    private AttrService attrService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttrMockMvc;

    private Attr attr;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attr createEntity(EntityManager em) {
        Attr attr = new Attr()
            .attrName(DEFAULT_ATTR_NAME)
            .attrDesc(DEFAULT_ATTR_DESC);
        return attr;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attr createUpdatedEntity(EntityManager em) {
        Attr attr = new Attr()
            .attrName(UPDATED_ATTR_NAME)
            .attrDesc(UPDATED_ATTR_DESC);
        return attr;
    }

    @BeforeEach
    public void initTest() {
        attr = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttr() throws Exception {
        int databaseSizeBeforeCreate = attrRepository.findAll().size();

        // Create the Attr
        AttrDTO attrDTO = attrMapper.toDto(attr);
        restAttrMockMvc.perform(post("/api/attrs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attrDTO)))
            .andExpect(status().isCreated());

        // Validate the Attr in the database
        List<Attr> attrList = attrRepository.findAll();
        assertThat(attrList).hasSize(databaseSizeBeforeCreate + 1);
        Attr testAttr = attrList.get(attrList.size() - 1);
        assertThat(testAttr.getAttrName()).isEqualTo(DEFAULT_ATTR_NAME);
        assertThat(testAttr.getAttrDesc()).isEqualTo(DEFAULT_ATTR_DESC);
    }

    @Test
    @Transactional
    public void createAttrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attrRepository.findAll().size();

        // Create the Attr with an existing ID
        attr.setId(1L);
        AttrDTO attrDTO = attrMapper.toDto(attr);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttrMockMvc.perform(post("/api/attrs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attrDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attr in the database
        List<Attr> attrList = attrRepository.findAll();
        assertThat(attrList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttrs() throws Exception {
        // Initialize the database
        attrRepository.saveAndFlush(attr);

        // Get all the attrList
        restAttrMockMvc.perform(get("/api/attrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attr.getId().intValue())))
            .andExpect(jsonPath("$.[*].attrName").value(hasItem(DEFAULT_ATTR_NAME)))
            .andExpect(jsonPath("$.[*].attrDesc").value(hasItem(DEFAULT_ATTR_DESC)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAttrsWithEagerRelationshipsIsEnabled() throws Exception {
        when(attrServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAttrMockMvc.perform(get("/api/attrs?eagerload=true"))
            .andExpect(status().isOk());

        verify(attrServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAttrsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(attrServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAttrMockMvc.perform(get("/api/attrs?eagerload=true"))
            .andExpect(status().isOk());

        verify(attrServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAttr() throws Exception {
        // Initialize the database
        attrRepository.saveAndFlush(attr);

        // Get the attr
        restAttrMockMvc.perform(get("/api/attrs/{id}", attr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attr.getId().intValue()))
            .andExpect(jsonPath("$.attrName").value(DEFAULT_ATTR_NAME))
            .andExpect(jsonPath("$.attrDesc").value(DEFAULT_ATTR_DESC));
    }

    @Test
    @Transactional
    public void getNonExistingAttr() throws Exception {
        // Get the attr
        restAttrMockMvc.perform(get("/api/attrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttr() throws Exception {
        // Initialize the database
        attrRepository.saveAndFlush(attr);

        int databaseSizeBeforeUpdate = attrRepository.findAll().size();

        // Update the attr
        Attr updatedAttr = attrRepository.findById(attr.getId()).get();
        // Disconnect from session so that the updates on updatedAttr are not directly saved in db
        em.detach(updatedAttr);
        updatedAttr
            .attrName(UPDATED_ATTR_NAME)
            .attrDesc(UPDATED_ATTR_DESC);
        AttrDTO attrDTO = attrMapper.toDto(updatedAttr);

        restAttrMockMvc.perform(put("/api/attrs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attrDTO)))
            .andExpect(status().isOk());

        // Validate the Attr in the database
        List<Attr> attrList = attrRepository.findAll();
        assertThat(attrList).hasSize(databaseSizeBeforeUpdate);
        Attr testAttr = attrList.get(attrList.size() - 1);
        assertThat(testAttr.getAttrName()).isEqualTo(UPDATED_ATTR_NAME);
        assertThat(testAttr.getAttrDesc()).isEqualTo(UPDATED_ATTR_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingAttr() throws Exception {
        int databaseSizeBeforeUpdate = attrRepository.findAll().size();

        // Create the Attr
        AttrDTO attrDTO = attrMapper.toDto(attr);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttrMockMvc.perform(put("/api/attrs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attrDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attr in the database
        List<Attr> attrList = attrRepository.findAll();
        assertThat(attrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttr() throws Exception {
        // Initialize the database
        attrRepository.saveAndFlush(attr);

        int databaseSizeBeforeDelete = attrRepository.findAll().size();

        // Delete the attr
        restAttrMockMvc.perform(delete("/api/attrs/{id}", attr.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attr> attrList = attrRepository.findAll();
        assertThat(attrList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
