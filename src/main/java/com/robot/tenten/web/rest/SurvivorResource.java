package com.robot.tenten.web.rest;

import com.robot.tenten.domain.Survivor;
import com.robot.tenten.repository.SurvivorRepository;
import com.robot.tenten.service.SurvivorService;
import com.robot.tenten.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.robot.tenten.domain.Survivor}.
 */
@RestController
@RequestMapping("/api")
public class SurvivorResource {

    private final Logger log = LoggerFactory.getLogger(SurvivorResource.class);

    private static final String ENTITY_NAME = "survivor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SurvivorService survivorService;

    private final SurvivorRepository survivorRepository;

    public SurvivorResource(SurvivorService survivorService, SurvivorRepository survivorRepository) {
        this.survivorService = survivorService;
        this.survivorRepository = survivorRepository;
    }

    /**
     * {@code POST  /survivors} : Create a new survivor.
     *
     * @param survivor the survivor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new survivor, or with status {@code 400 (Bad Request)} if the survivor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/survivors")
    public ResponseEntity<Survivor> createSurvivor(@RequestBody Survivor survivor) throws URISyntaxException {
        log.debug("REST request to save Survivor : {}", survivor);
        if (survivor.getId() != null) {
            throw new BadRequestAlertException("A new survivor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Survivor result = survivorService.save(survivor);
        return ResponseEntity
            .created(new URI("/api/survivors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /survivors/:id} : Updates an existing survivor.
     *
     * @param id the id of the survivor to save.
     * @param survivor the survivor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated survivor,
     * or with status {@code 400 (Bad Request)} if the survivor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the survivor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/survivors/{id}")
    public ResponseEntity<Survivor> updateSurvivor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Survivor survivor
    ) throws URISyntaxException {
        log.debug("REST request to update Survivor : {}, {}", id, survivor);
        if (survivor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, survivor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!survivorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Survivor result = survivorService.update(survivor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, survivor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /survivors/:id} : Partial updates given fields of an existing survivor, field will ignore if it is null
     *
     * @param id the id of the survivor to save.
     * @param survivor the survivor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated survivor,
     * or with status {@code 400 (Bad Request)} if the survivor is not valid,
     * or with status {@code 404 (Not Found)} if the survivor is not found,
     * or with status {@code 500 (Internal Server Error)} if the survivor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/survivors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Survivor> partialUpdateSurvivor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Survivor survivor
    ) throws URISyntaxException {
        log.debug("REST request to partial update Survivor partially : {}, {}", id, survivor);
        if (survivor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, survivor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!survivorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Survivor> result = survivorService.partialUpdate(survivor);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, survivor.getId().toString())
        );
    }

    /**
     * {@code GET  /survivors} : get all the survivors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of survivors in body.
     */
    @GetMapping("/survivors")
    public ResponseEntity<List<Survivor>> getAllSurvivors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Survivors");
        Page<Survivor> page = survivorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /survivors/:id} : get the "id" survivor.
     *
     * @param id the id of the survivor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the survivor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/survivors/{id}")
    public ResponseEntity<Survivor> getSurvivor(@PathVariable Long id) {
        log.debug("REST request to get Survivor : {}", id);
        Optional<Survivor> survivor = survivorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(survivor);
    }

    /**
     * {@code DELETE  /survivors/:id} : delete the "id" survivor.
     *
     * @param id the id of the survivor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/survivors/{id}")
    public ResponseEntity<Void> deleteSurvivor(@PathVariable Long id) {
        log.debug("REST request to delete Survivor : {}", id);
        survivorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
