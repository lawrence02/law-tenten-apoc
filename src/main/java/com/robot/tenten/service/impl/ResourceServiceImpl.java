package com.robot.tenten.service.impl;

import com.robot.tenten.domain.Resource;
import com.robot.tenten.repository.ResourceRepository;
import com.robot.tenten.service.ResourceService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Resource}.
 */
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

    private final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Resource save(Resource resource) {
        log.debug("Request to save Resource : {}", resource);
        return resourceRepository.save(resource);
    }

    @Override
    public Resource update(Resource resource) {
        log.debug("Request to update Resource : {}", resource);
        return resourceRepository.save(resource);
    }

    @Override
    public Optional<Resource> partialUpdate(Resource resource) {
        log.debug("Request to partially update Resource : {}", resource);

        return resourceRepository
            .findById(resource.getId())
            .map(existingResource -> {
                if (resource.getResourceType() != null) {
                    existingResource.setResourceType(resource.getResourceType());
                }
                if (resource.getQuantity() != null) {
                    existingResource.setQuantity(resource.getQuantity());
                }

                return existingResource;
            })
            .map(resourceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> findAll() {
        log.debug("Request to get all Resources");
        return resourceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resource> findOne(Long id) {
        log.debug("Request to get Resource : {}", id);
        return resourceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resource : {}", id);
        resourceRepository.deleteById(id);
    }
}
