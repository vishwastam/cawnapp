package com.cawnfig.cawnapp.service;

import com.cawnfig.cawnapp.domain.C_user;
import com.cawnfig.cawnapp.repository.C_userRepository;
import com.cawnfig.cawnapp.repository.search.C_userSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing C_user.
 */
@Service
@Transactional
public class C_userService {

    private final Logger log = LoggerFactory.getLogger(C_userService.class);

    private final C_userRepository c_userRepository;

    private final C_userSearchRepository c_userSearchRepository;

    public C_userService(C_userRepository c_userRepository, C_userSearchRepository c_userSearchRepository) {
        this.c_userRepository = c_userRepository;
        this.c_userSearchRepository = c_userSearchRepository;
    }

    /**
     * Save a c_user.
     *
     * @param c_user the entity to save
     * @return the persisted entity
     */
    public C_user save(C_user c_user) {
        log.debug("Request to save C_user : {}", c_user);
        C_user result = c_userRepository.save(c_user);
        c_userSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the c_users.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<C_user> findAll(Pageable pageable) {
        log.debug("Request to get all C_users");
        return c_userRepository.findAll(pageable);
    }

    /**
     *  Get one c_user by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public C_user findOne(Long id) {
        log.debug("Request to get C_user : {}", id);
        return c_userRepository.findOne(id);
    }

    /**
     *  Delete the  c_user by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_user : {}", id);
        c_userRepository.delete(id);
        c_userSearchRepository.delete(id);
    }

    /**
     * Search for the c_user corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<C_user> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of C_users for query {}", query);
        Page<C_user> result = c_userSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
