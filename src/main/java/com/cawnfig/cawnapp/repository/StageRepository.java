package com.cawnfig.cawnapp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cawnfig.cawnapp.domain.Stage;


/**
 * Spring Data JPA repository for the Stage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StageRepository extends JpaRepository<Stage,Long> {
	@Query("from Stage s where s.application.id = ?1")
	Set<Stage> findByOrg(Long applicationId);
}
