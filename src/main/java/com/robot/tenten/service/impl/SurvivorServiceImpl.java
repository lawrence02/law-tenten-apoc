package com.robot.tenten.service.impl;

import com.robot.tenten.domain.Survivor;
import com.robot.tenten.repository.SurvivorRepository;
import com.robot.tenten.service.SurvivorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Survivor}.
 */
@Service
@Transactional
public class SurvivorServiceImpl implements SurvivorService {

    private final Logger log = LoggerFactory.getLogger(SurvivorServiceImpl.class);

    private final SurvivorRepository survivorRepository;

    public SurvivorServiceImpl(SurvivorRepository survivorRepository) {
        this.survivorRepository = survivorRepository;
    }

    @Override
    public Survivor save(Survivor survivor) {
        log.debug("Request to save Survivor : {}", survivor);
        return survivorRepository.save(survivor);
    }

    @Override
    public Survivor update(Survivor survivor) {
        log.debug("Request to update Survivor : {}", survivor);
        return survivorRepository.save(survivor);
    }

    @Override
    public Optional<Survivor> partialUpdate(Survivor survivor) {
        log.debug("Request to partially update Survivor : {}", survivor);

        return survivorRepository
            .findById(survivor.getId())
            .map(existingSurvivor -> {
                if (survivor.getSurvivorId() != null) {
                    existingSurvivor.setSurvivorId(survivor.getSurvivorId());
                }
                if (survivor.getName() != null) {
                    existingSurvivor.setName(survivor.getName());
                }
                if (survivor.getAge() != null) {
                    existingSurvivor.setAge(survivor.getAge());
                }
                if (survivor.getGender() != null) {
                    existingSurvivor.setGender(survivor.getGender());
                }
                if (survivor.getLatitude() != null) {
                    existingSurvivor.setLatitude(survivor.getLatitude());
                }
                if (survivor.getLongitude() != null) {
                    existingSurvivor.setLongitude(survivor.getLongitude());
                }
                if (survivor.getStatus() != null) {
                    existingSurvivor.setStatus(survivor.getStatus());
                }

                return existingSurvivor;
            })
            .map(survivorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Survivor> findAll(Pageable pageable) {
        log.debug("Request to get all Survivors");
        return survivorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Survivor> findOne(Long id) {
        log.debug("Request to get Survivor : {}", id);
        return survivorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Survivor : {}", id);
        survivorRepository.deleteById(id);
    }
}
