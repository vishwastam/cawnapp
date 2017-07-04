package com.cawnfig.cawnapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cawnfig.cawnapp.domain.Key;
import com.cawnfig.cawnapp.service.KeyService;
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
 * REST controller for managing Key.
 */
@RestController
@RequestMapping("/api")
public class KeyResource {

    private final Logger log = LoggerFactory.getLogger(KeyResource.class);

    private static final String ENTITY_NAME = "key";

    private final KeyService keyService;

    public KeyResource(KeyService keyService) {
        this.keyService = keyService;
    }

    /**
     * POST  /keys : Create a new key.
     *
     * @param key the key to create
     * @return the ResponseEntity with status 201 (Created) and with body the new key, or with status 400 (Bad Request) if the key has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/keys")
    @Timed
    public ResponseEntity<Key> createKey(@Valid @RequestBody Key key) throws URISyntaxException {
        log.debug("REST request to save Key : {}", key);
        if (key.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new key cannot already have an ID")).body(null);
        }
        Key result = keyService.save(key);
        return ResponseEntity.created(new URI("/api/keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /keys : Updates an existing key.
     *
     * @param key the key to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated key,
     * or with status 400 (Bad Request) if the key is not valid,
     * or with status 500 (Internal Server Error) if the key couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/keys")
    @Timed
    public ResponseEntity<Key> updateKey(@Valid @RequestBody Key key) throws URISyntaxException {
        log.debug("REST request to update Key : {}", key);
        if (key.getId() == null) {
            return createKey(key);
        }
        Key result = keyService.save(key);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, key.getId().toString()))
            .body(result);
    }

    /**
     * GET  /keys : get all the keys.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of keys in body
     */
    @GetMapping("/keys")
    @Timed
    public ResponseEntity<List<Key>> getAllKeys(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Keys");
        Page<Key> page = keyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/keys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /keys/:id : get the "id" key.
     *
     * @param id the id of the key to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the key, or with status 404 (Not Found)
     */
    @GetMapping("/keys/{id}")
    @Timed
    public ResponseEntity<Key> getKey(@PathVariable Long id) {
        log.debug("REST request to get Key : {}", id);
        Key key = keyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(key));
    }

    /**
     * DELETE  /keys/:id : delete the "id" key.
     *
     * @param id the id of the key to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/keys/{id}")
    @Timed
    public ResponseEntity<Void> deleteKey(@PathVariable Long id) {
        log.debug("REST request to delete Key : {}", id);
        keyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/keys?query=:query : search for the key corresponding
     * to the query.
     *
     * @param query the query of the key search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/keys")
    @Timed
    public ResponseEntity<List<Key>> searchKeys(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Keys for query {}", query);
        Page<Key> page = keyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/keys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
