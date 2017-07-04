package com.cawnfig.cawnapp.repository.search;

import com.cawnfig.cawnapp.domain.Configurables;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Configurables entity.
 */
public interface ConfigurablesSearchRepository extends ElasticsearchRepository<Configurables, Long> {
}
