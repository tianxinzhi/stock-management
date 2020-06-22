package com.hkt.btu.web.rest;

import com.hkt.btu.service.AttrService;
import com.hkt.btu.web.rest.errors.BadRequestAlertException;
import com.hkt.btu.service.dto.AttrDTO;

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
 * REST controller for managing {@link com.hkt.btu.domain.Attr}.
 */
@RestController
@RequestMapping("/api")
public class AttrResource {

    private final Logger log = LoggerFactory.getLogger(AttrResource.class);

    private static final String ENTITY_NAME = "attr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttrService attrService;

    public AttrResource(AttrService attrService) {
        this.attrService = attrService;
    }

    /**
     * {@code POST  /attrs} : Create a new attr.
     *
     * @param attrDTO the attrDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attrDTO, or with status {@code 400 (Bad Request)} if the attr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attrs")
    public ResponseEntity<AttrDTO> createAttr(@RequestBody AttrDTO attrDTO) throws URISyntaxException {
        log.debug("REST request to save Attr : {}", attrDTO);
        if (attrDTO.getId() != null) {
            throw new BadRequestAlertException("A new attr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttrDTO result = attrService.save(attrDTO);
        return ResponseEntity.created(new URI("/api/attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attrs} : Updates an existing attr.
     *
     * @param attrDTO the attrDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attrDTO,
     * or with status {@code 400 (Bad Request)} if the attrDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attrDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attrs")
    public ResponseEntity<AttrDTO> updateAttr(@RequestBody AttrDTO attrDTO) throws URISyntaxException {
        log.debug("REST request to update Attr : {}", attrDTO);
        if (attrDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttrDTO result = attrService.save(attrDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attrDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attrs} : get all the attrs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attrs in body.
     */
    @GetMapping("/attrs")
    public List<AttrDTO> getAllAttrs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Attrs");
        return attrService.findAll();
    }

    /**
     * {@code GET  /attrs/:id} : get the "id" attr.
     *
     * @param id the id of the attrDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attrDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attrs/{id}")
    public ResponseEntity<AttrDTO> getAttr(@PathVariable Long id) {
        log.debug("REST request to get Attr : {}", id);
        Optional<AttrDTO> attrDTO = attrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attrDTO);
    }

    /**
     * {@code DELETE  /attrs/:id} : delete the "id" attr.
     *
     * @param id the id of the attrDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attrs/{id}")
    public ResponseEntity<Void> deleteAttr(@PathVariable Long id) {
        log.debug("REST request to delete Attr : {}", id);
        attrService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
