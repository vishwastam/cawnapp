package com.cawnfig.cawnapp.repository;

import com.cawnfig.cawnapp.domain.Configurables;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Configurables entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigurablesRepository extends JpaRepository<Configurables,Long> {

}
