package com.cawnfig.cawnapp.repository.search;

import com.cawnfig.cawnapp.domain.Application;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Application entity.
 */
public interface ApplicationSearchRepository extends ElasticsearchRepository<Application, Long> {
}
