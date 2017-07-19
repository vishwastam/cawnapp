package com.cawnfig.cawnapp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cawnfig.cawnapp.domain.Key;
import com.cawnfig.cawnapp.domain.Stage;


/**
 * Spring Data JPA repository for the Key entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyRepository extends JpaRepository<Key,Long> {
	@Query("from Key k where k.stage.id = ?1")
	Set<Key> findByStage(Long stageId);

	@Query("from Key k where k.stage.application.id = ?1")
	Set<Key> findByApplication(Long applicationId);
}
