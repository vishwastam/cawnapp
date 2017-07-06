package com.cawnfig.cawnapp.repository;

import com.cawnfig.cawnapp.domain.Stage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Stage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StageRepository extends JpaRepository<Stage,Long> {

}
