package com.hkt.btu.web.rest;

import com.hkt.btu.domain.AttrValue;
import com.hkt.btu.repository.AttrValueRepository;
import com.hkt.btu.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class AttrValueResource {

    private final Logger log = LoggerFactory.getLogger(AttrValueResource.class);

    private static final String ENTITY_NAME = "attrValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttrValueRepository attrValueRepository;

    public AttrValueResource(AttrValueRepository attrValueRepository) {
        this.attrValueRepository = attrValueRepository;
    }

    /**
     * {@code POST  /attr-values} : Create a new attrValue.
     *
     * @param attrValue the attrValue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attrValue, or with status {@code 400 (Bad Request)} if the attrValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attr-values")
    public ResponseEntity<AttrValue> createAttrValue(@RequestBody AttrValue attrValue) throws URISyntaxException {
        log.debug("REST request to save AttrValue : {}", attrValue);
        if (attrValue.getId() != null) {
            throw new BadRequestAlertException("A new attrValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttrValue result = attrValueRepository.save(attrValue);
        return ResponseEntity.created(new URI("/api/attr-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attr-values} : Updates an existing attrValue.
     *
     * @param attrValue the attrValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attrValue,
     * or with status {@code 400 (Bad Request)} if the attrValue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attrValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attr-values")
    public ResponseEntity<AttrValue> updateAttrValue(@RequestBody AttrValue attrValue) throws URISyntaxException {
        log.debug("REST request to update AttrValue : {}", attrValue);
        if (attrValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttrValue result = attrValueRepository.save(attrValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attrValue.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attr-values} : get all the attrValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attrValues in body.
     */
    @GetMapping("/attr-values")
    public List<AttrValue> getAllAttrValues() {
        log.debug("REST request to get all AttrValues");
        return attrValueRepository.findAll();
    }

    /**
     * {@code GET  /attr-values/:id} : get the "id" attrValue.
     *
     * @param id the id of the attrValue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attrValue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attr-values/{id}")
    public ResponseEntity<AttrValue> getAttrValue(@PathVariable Long id) {
        log.debug("REST request to get AttrValue : {}", id);
        Optional<AttrValue> attrValue = attrValueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(attrValue);
    }

    /**
     * {@code DELETE  /attr-values/:id} : delete the "id" attrValue.
     *
     * @param id the id of the attrValue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attr-values/{id}")
    public ResponseEntity<Void> deleteAttrValue(@PathVariable Long id) {
        log.debug("REST request to delete AttrValue : {}", id);
        attrValueRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
