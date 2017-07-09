package com.cawnfig.cawnapp.service;

import com.cawnfig.cawnapp.domain.Key;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Key.
 */
public interface KeyService {

    /**
     * Save a key.
     *
     * @param key the entity to save
     * @return the persisted entity
     */
    Key save(Key key);

    /**
     * Encrypt the value contained in key and Save key.
     *
     * @param key the entity to save
     * @return the persisted entity
     * @throws Exception 
     */
    Key encryptAndsave(Key key) throws Exception;

    /**
     *  Get all the keys.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Key> findAll(Pageable pageable);

    /**
     *  Get the "id" key.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Key findOne(Long id);

    /**
     *  Delete the "id" key.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the key corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Key> search(String query, Pageable pageable);
}
