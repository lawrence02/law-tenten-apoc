package com.robot.tenten.repository;

import com.robot.tenten.domain.Survivor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Survivor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurvivorRepository extends JpaRepository<Survivor, Long> {}
