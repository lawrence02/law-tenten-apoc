package com.robot.tenten.service;

import com.robot.tenten.domain.Survivor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Survivor}.
 */
public interface SurvivorService {
    /**
     * Save a survivor.
     *
     * @param survivor the entity to save.
     * @return the persisted entity.
     */
    Survivor save(Survivor survivor);

    /**
     * Updates a survivor.
     *
     * @param survivor the entity to update.
     * @return the persisted entity.
     */
    Survivor update(Survivor survivor);

    /**
     * Partially updates a survivor.
     *
     * @param survivor the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Survivor> partialUpdate(Survivor survivor);

    /**
     * Get all the survivors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Survivor> findAll(Pageable pageable);

    /**
     * Get the "id" survivor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Survivor> findOne(Long id);

    /**
     * Delete the "id" survivor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
