package com.cawnfig.cawnapp.repository;

import java.util.Set;

import com.cawnfig.cawnapp.domain.Application;
import com.cawnfig.cawnapp.domain.Stage;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Application entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
	@Query("from Application a where a.organisation.id = ?1")
	Set<Application> findByOrg(Long applicationId);
}
