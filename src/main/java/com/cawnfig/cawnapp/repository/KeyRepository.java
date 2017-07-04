package com.cawnfig.cawnapp.repository;

import com.cawnfig.cawnapp.domain.Key;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Key entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyRepository extends JpaRepository<Key,Long> {

}
