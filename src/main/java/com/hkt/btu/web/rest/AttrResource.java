package com.hkt.btu.web.rest;

import com.hkt.btu.domain.Attr;
import com.hkt.btu.repository.AttrRepository;
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
 * REST controller for managing {@link com.hkt.btu.domain.Attr}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AttrResource {

    private final Logger log = LoggerFactory.getLogger(AttrResource.class);

    private static final String ENTITY_NAME = "attr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttrRepository attrRepository;

    public AttrResource(AttrRepository attrRepository) {
        this.attrRepository = attrRepository;
    }

    /**
     * {@code POST  /attrs} : Create a new attr.
     *
     * @param attr the attr to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attr, or with status {@code 400 (Bad Request)} if the attr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attrs")
    public ResponseEntity<Attr> createAttr(@RequestBody Attr attr) throws URISyntaxException {
        log.debug("REST request to save Attr : {}", attr);
        if (attr.getId() != null) {
            throw new BadRequestAlertException("A new attr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Attr result = attrRepository.save(attr);
        return ResponseEntity.created(new URI("/api/attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attrs} : Updates an existing attr.
     *
     * @param attr the attr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attr,
     * or with status {@code 400 (Bad Request)} if the attr is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attrs")
    public ResponseEntity<Attr> updateAttr(@RequestBody Attr attr) throws URISyntaxException {
        log.debug("REST request to update Attr : {}", attr);
        if (attr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Attr result = attrRepository.save(attr);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attr.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attrs} : get all the attrs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attrs in body.
     */
    @GetMapping("/attrs")
    public List<Attr> getAllAttrs() {
        log.debug("REST request to get all Attrs");
        return attrRepository.findAll();
    }

    /**
     * {@code GET  /attrs/:id} : get the "id" attr.
     *
     * @param id the id of the attr to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attr, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attrs/{id}")
    public ResponseEntity<Attr> getAttr(@PathVariable Long id) {
        log.debug("REST request to get Attr : {}", id);
        Optional<Attr> attr = attrRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(attr);
    }

    /**
     * {@code DELETE  /attrs/:id} : delete the "id" attr.
     *
     * @param id the id of the attr to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attrs/{id}")
    public ResponseEntity<Void> deleteAttr(@PathVariable Long id) {
        log.debug("REST request to delete Attr : {}", id);
        attrRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
