package com.cawnfig.cawnapp.repository.search;

import com.cawnfig.cawnapp.domain.Key;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Key entity.
 */
public interface KeySearchRepository extends ElasticsearchRepository<Key, Long> {
}
