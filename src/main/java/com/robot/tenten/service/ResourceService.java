package com.robot.tenten.service;

import com.robot.tenten.domain.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Resource}.
 */
public interface ResourceService {
    /**
     * Save a resource.
     *
     * @param resource the entity to save.
     * @return the persisted entity.
     */
    Resource save(Resource resource);

    /**
     * Updates a resource.
     *
     * @param resource the entity to update.
     * @return the persisted entity.
     */
    Resource update(Resource resource);

    /**
     * Partially updates a resource.
     *
     * @param resource the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Resource> partialUpdate(Resource resource);

    /**
     * Get all the resources.
     *
     * @return the list of entities.
     */
    List<Resource> findAll();

    /**
     * Get the "id" resource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Resource> findOne(Long id);

    /**
     * Delete the "id" resource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
