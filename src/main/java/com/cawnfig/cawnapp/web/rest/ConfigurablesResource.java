package com.cawnfig.cawnapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cawnfig.cawnapp.domain.Configurables;
import com.cawnfig.cawnapp.service.ConfigurablesService;
import com.cawnfig.cawnapp.web.rest.util.HeaderUtil;
import com.cawnfig.cawnapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Configurables.
 */
@RestController
@RequestMapping("/api")
public class ConfigurablesResource {

    private final Logger log = LoggerFactory.getLogger(ConfigurablesResource.class);

    private static final String ENTITY_NAME = "configurables";

    private final ConfigurablesService configurablesService;

    public ConfigurablesResource(ConfigurablesService configurablesService) {
        this.configurablesService = configurablesService;
    }

    /**
     * POST  /configurables : Create a new configurables.
     *
     * @param configurables the configurables to create
     * @return the ResponseEntity with status 201 (Created) and with body the new configurables, or with status 400 (Bad Request) if the configurables has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/configurables")
    @Timed
    public ResponseEntity<Configurables> createConfigurables(@Valid @RequestBody Configurables configurables) throws URISyntaxException {
        log.debug("REST request to save Configurables : {}", configurables);
        if (configurables.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new configurables cannot already have an ID")).body(null);
        }
        Configurables result = configurablesService.save(configurables);
        return ResponseEntity.created(new URI("/api/configurables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /configurables : Updates an existing configurables.
     *
     * @param configurables the configurables to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated configurables,
     * or with status 400 (Bad Request) if the configurables is not valid,
     * or with status 500 (Internal Server Error) if the configurables couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/configurables")
    @Timed
    public ResponseEntity<Configurables> updateConfigurables(@Valid @RequestBody Configurables configurables) throws URISyntaxException {
        log.debug("REST request to update Configurables : {}", configurables);
        if (configurables.getId() == null) {
            return createConfigurables(configurables);
        }
        Configurables result = configurablesService.save(configurables);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, configurables.getId().toString()))
            .body(result);
    }

    /**
     * GET  /configurables : get all the configurables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of configurables in body
     */
    @GetMapping("/configurables")
    @Timed
    public ResponseEntity<List<Configurables>> getAllConfigurables(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Configurables");
        Page<Configurables> page = configurablesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/configurables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /configurables/:id : get the "id" configurables.
     *
     * @param id the id of the configurables to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the configurables, or with status 404 (Not Found)
     */
    @GetMapping("/configurables/{id}")
    @Timed
    public ResponseEntity<Configurables> getConfigurables(@PathVariable Long id) {
        log.debug("REST request to get Configurables : {}", id);
        Configurables configurables = configurablesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(configurables));
    }

    /**
     * DELETE  /configurables/:id : delete the "id" configurables.
     *
     * @param id the id of the configurables to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/configurables/{id}")
    @Timed
    public ResponseEntity<Void> deleteConfigurables(@PathVariable Long id) {
        log.debug("REST request to delete Configurables : {}", id);
        configurablesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/configurables?query=:query : search for the configurables corresponding
     * to the query.
     *
     * @param query the query of the configurables search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/configurables")
    @Timed
    public ResponseEntity<List<Configurables>> searchConfigurables(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Configurables for query {}", query);
        Page<Configurables> page = configurablesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/configurables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
