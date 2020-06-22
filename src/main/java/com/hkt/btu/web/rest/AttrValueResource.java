package com.hkt.btu.web.rest;

import com.hkt.btu.service.AttrValueService;
import com.hkt.btu.web.rest.errors.BadRequestAlertException;
import com.hkt.btu.service.dto.AttrValueDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hkt.btu.domain.AttrValue}.
 */
@RestController
@RequestMapping("/api")
public class AttrValueResource {

    private final Logger log = LoggerFactory.getLogger(AttrValueResource.class);

    private static final String ENTITY_NAME = "attrValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttrValueService attrValueService;

    public AttrValueResource(AttrValueService attrValueService) {
        this.attrValueService = attrValueService;
    }

    /**
     * {@code POST  /attr-values} : Create a new attrValue.
     *
     * @param attrValueDTO the attrValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attrValueDTO, or with status {@code 400 (Bad Request)} if the attrValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attr-values")
    public ResponseEntity<AttrValueDTO> createAttrValue(@RequestBody AttrValueDTO attrValueDTO) throws URISyntaxException {
        log.debug("REST request to save AttrValue : {}", attrValueDTO);
        if (attrValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new attrValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttrValueDTO result = attrValueService.save(attrValueDTO);
        return ResponseEntity.created(new URI("/api/attr-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attr-values} : Updates an existing attrValue.
     *
     * @param attrValueDTO the attrValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attrValueDTO,
     * or with status {@code 400 (Bad Request)} if the attrValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attrValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attr-values")
    public ResponseEntity<AttrValueDTO> updateAttrValue(@RequestBody AttrValueDTO attrValueDTO) throws URISyntaxException {
        log.debug("REST request to update AttrValue : {}", attrValueDTO);
        if (attrValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttrValueDTO result = attrValueService.save(attrValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attrValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attr-values} : get all the attrValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attrValues in body.
     */
    @GetMapping("/attr-values")
    public List<AttrValueDTO> getAllAttrValues() {
        log.debug("REST request to get all AttrValues");
        return attrValueService.findAll();
    }

    /**
     * {@code GET  /attr-values/:id} : get the "id" attrValue.
     *
     * @param id the id of the attrValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attrValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attr-values/{id}")
    public ResponseEntity<AttrValueDTO> getAttrValue(@PathVariable Long id) {
        log.debug("REST request to get AttrValue : {}", id);
        Optional<AttrValueDTO> attrValueDTO = attrValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attrValueDTO);
    }

    /**
     * {@code DELETE  /attr-values/:id} : delete the "id" attrValue.
     *
     * @param id the id of the attrValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attr-values/{id}")
    public ResponseEntity<Void> deleteAttrValue(@PathVariable Long id) {
        log.debug("REST request to delete AttrValue : {}", id);
        attrValueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
