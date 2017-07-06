package com.cawnfig.cawnapp.repository.search;

import com.cawnfig.cawnapp.domain.Organisation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Organisation entity.
 */
public interface OrganisationSearchRepository extends ElasticsearchRepository<Organisation, Long> {
}
