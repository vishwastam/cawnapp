package com.cawnfig.cawnapp.service;

import com.codahale.metrics.annotation.Timed;
import com.cawnfig.cawnapp.domain.*;
import com.cawnfig.cawnapp.repository.*;
import com.cawnfig.cawnapp.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final ApplicationRepository applicationRepository;

    private final ApplicationSearchRepository applicationSearchRepository;

    private final ConfigurablesRepository configurablesRepository;

    private final ConfigurablesSearchRepository configurablesSearchRepository;

    private final KeyRepository keyRepository;

    private final KeySearchRepository keySearchRepository;

    private final OrganisationRepository organisationRepository;

    private final OrganisationSearchRepository organisationSearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        ApplicationRepository applicationRepository,
        ApplicationSearchRepository applicationSearchRepository,
        ConfigurablesRepository configurablesRepository,
        ConfigurablesSearchRepository configurablesSearchRepository,
        KeyRepository keyRepository,
        KeySearchRepository keySearchRepository,
        OrganisationRepository organisationRepository,
        OrganisationSearchRepository organisationSearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.applicationRepository = applicationRepository;
        this.applicationSearchRepository = applicationSearchRepository;
        this.configurablesRepository = configurablesRepository;
        this.configurablesSearchRepository = configurablesSearchRepository;
        this.keyRepository = keyRepository;
        this.keySearchRepository = keySearchRepository;
        this.organisationRepository = organisationRepository;
        this.organisationSearchRepository = organisationSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        reindexForClass(Application.class, applicationRepository, applicationSearchRepository);
        reindexForClass(Configurables.class, configurablesRepository, configurablesSearchRepository);
        reindexForClass(Key.class, keyRepository, keySearchRepository);
        reindexForClass(Organisation.class, organisationRepository, organisationSearchRepository);
        reindexForClass(User.class, userRepository, userSearchRepository);

        log.info("Elasticsearch: Successfully performed reindexing");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            try {
                Method m = jpaRepository.getClass().getMethod("findAllWithEagerRelationships");
                elasticsearchRepository.save((List<T>) m.invoke(jpaRepository));
            } catch (Exception e) {
                elasticsearchRepository.save(jpaRepository.findAll());
            }
        }
        log.info("Elasticsearch: Indexed all rows for " + entityClass.getSimpleName());
    }
}
