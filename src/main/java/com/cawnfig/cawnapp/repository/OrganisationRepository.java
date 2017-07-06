package com.cawnfig.cawnapp.repository;

import com.cawnfig.cawnapp.domain.Organisation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Organisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisationRepository extends JpaRepository<Organisation,Long> {

}
