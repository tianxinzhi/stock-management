package com.hkt.btu.web.rest;

import com.hkt.btu.service.SpecService;
import com.hkt.btu.web.rest.errors.BadRequestAlertException;
import com.hkt.btu.service.dto.SpecDTO;

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
 * REST controller for managing {@link com.hkt.btu.domain.Spec}.
 */
@RestController
@RequestMapping("/api")
public class SpecResource {

    private final Logger log = LoggerFactory.getLogger(SpecResource.class);

    private static final String ENTITY_NAME = "spec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecService specService;

    public SpecResource(SpecService specService) {
        this.specService = specService;
    }

    /**
     * {@code POST  /specs} : Create a new spec.
     *
     * @param specDTO the specDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specDTO, or with status {@code 400 (Bad Request)} if the spec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specs")
    public ResponseEntity<SpecDTO> createSpec(@RequestBody SpecDTO specDTO) throws URISyntaxException {
        log.debug("REST request to save Spec : {}", specDTO);
        if (specDTO.getId() != null) {
            throw new BadRequestAlertException("A new spec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecDTO result = specService.save(specDTO);
        return ResponseEntity.created(new URI("/api/specs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specs} : Updates an existing spec.
     *
     * @param specDTO the specDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specDTO,
     * or with status {@code 400 (Bad Request)} if the specDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specs")
    public ResponseEntity<SpecDTO> updateSpec(@RequestBody SpecDTO specDTO) throws URISyntaxException {
        log.debug("REST request to update Spec : {}", specDTO);
        if (specDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpecDTO result = specService.save(specDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /specs} : get all the specs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specs in body.
     */
    @GetMapping("/specs")
    public List<SpecDTO> getAllSpecs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Specs");
        return specService.findAll();
    }

    /**
     * {@code GET  /specs/:id} : get the "id" spec.
     *
     * @param id the id of the specDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specs/{id}")
    public ResponseEntity<SpecDTO> getSpec(@PathVariable Long id) {
        log.debug("REST request to get Spec : {}", id);
        Optional<SpecDTO> specDTO = specService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specDTO);
    }

    /**
     * {@code DELETE  /specs/:id} : delete the "id" spec.
     *
     * @param id the id of the specDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specs/{id}")
    public ResponseEntity<Void> deleteSpec(@PathVariable Long id) {
        log.debug("REST request to delete Spec : {}", id);
        specService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
