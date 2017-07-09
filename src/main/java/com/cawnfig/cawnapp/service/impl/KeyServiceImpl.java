package com.cawnfig.cawnapp.service.impl;

import com.cawnfig.cawnapp.service.KeyService;
import com.cawnfig.cawnapp.domain.Key;
import com.cawnfig.cawnapp.repository.KeyRepository;
import com.cawnfig.cawnapp.repository.search.KeySearchRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Key.
 */
@Service
@Transactional
public class KeyServiceImpl implements KeyService{

    private final Logger log = LoggerFactory.getLogger(KeyServiceImpl.class);

    private final KeyRepository keyRepository;

    private final KeySearchRepository keySearchRepository;

    public KeyServiceImpl(KeyRepository keyRepository, KeySearchRepository keySearchRepository) {
        this.keyRepository = keyRepository;
        this.keySearchRepository = keySearchRepository;
    }

    /**
     * Save a key.
     *
     * @param key the entity to save
     * @return the persisted entity
     * @throws Exception 
     */
    @Override
    public Key save(Key key) throws Exception {
        log.debug("Request to save Key : {}", key);
        Key result = keyRepository.save(key);
        keySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the keys.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Key> findAll(Pageable pageable) {
        log.debug("Request to get all Keys");
        return keyRepository.findAll(pageable);
    }

    /**
     *  Get one key by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Key findOne(Long id) {
        log.debug("Request to get Key : {}", id);
        return keyRepository.findOne(id);
    }

    /**
     *  Delete the  key by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Key : {}", id);
        keyRepository.delete(id);
        keySearchRepository.delete(id);
    }

    /**
     * Search for the key corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Key> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Keys for query {}", query);
        Page<Key> result = keySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
