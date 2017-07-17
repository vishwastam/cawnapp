package com.cawnfig.cawnapp.repository.search;

import com.cawnfig.cawnapp.domain.C_user;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the C_user entity.
 */
public interface C_userSearchRepository extends ElasticsearchRepository<C_user, Long> {
}
