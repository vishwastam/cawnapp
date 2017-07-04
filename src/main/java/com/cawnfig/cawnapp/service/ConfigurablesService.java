package com.cawnfig.cawnapp.service;

import com.cawnfig.cawnapp.domain.Configurables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Configurables.
 */
public interface ConfigurablesService {

    /**
     * Save a configurables.
     *
     * @param configurables the entity to save
     * @return the persisted entity
     */
    Configurables save(Configurables configurables);

    /**
     *  Get all the configurables.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Configurables> findAll(Pageable pageable);

    /**
     *  Get the "id" configurables.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Configurables findOne(Long id);

    /**
     *  Delete the "id" configurables.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the configurables corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Configurables> search(String query, Pageable pageable);
}
