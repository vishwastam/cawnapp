package com.cawnfig.cawnapp.service.impl;

import com.cawnfig.cawnapp.service.ConfigurablesService;
import com.cawnfig.cawnapp.domain.Configurables;
import com.cawnfig.cawnapp.repository.ConfigurablesRepository;
import com.cawnfig.cawnapp.repository.search.ConfigurablesSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Configurables.
 */
@Service
@Transactional
public class ConfigurablesServiceImpl implements ConfigurablesService{

    private final Logger log = LoggerFactory.getLogger(ConfigurablesServiceImpl.class);

    private final ConfigurablesRepository configurablesRepository;

    private final ConfigurablesSearchRepository configurablesSearchRepository;

    public ConfigurablesServiceImpl(ConfigurablesRepository configurablesRepository, ConfigurablesSearchRepository configurablesSearchRepository) {
        this.configurablesRepository = configurablesRepository;
        this.configurablesSearchRepository = configurablesSearchRepository;
    }

    /**
     * Save a configurables.
     *
     * @param configurables the entity to save
     * @return the persisted entity
     */
    @Override
    public Configurables save(Configurables configurables) {
        log.debug("Request to save Configurables : {}", configurables);
        Configurables result = configurablesRepository.save(configurables);
        configurablesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the configurables.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Configurables> findAll(Pageable pageable) {
        log.debug("Request to get all Configurables");
        return configurablesRepository.findAll(pageable);
    }

    /**
     *  Get one configurables by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Configurables findOne(Long id) {
        log.debug("Request to get Configurables : {}", id);
        return configurablesRepository.findOne(id);
    }

    /**
     *  Delete the  configurables by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Configurables : {}", id);
        configurablesRepository.delete(id);
        configurablesSearchRepository.delete(id);
    }

    /**
     * Search for the configurables corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Configurables> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Configurables for query {}", query);
        Page<Configurables> result = configurablesSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
