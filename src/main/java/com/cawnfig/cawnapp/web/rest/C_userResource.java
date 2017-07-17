package com.cawnfig.cawnapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cawnfig.cawnapp.domain.C_user;
import com.cawnfig.cawnapp.service.C_userService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing C_user.
 */
@RestController
@RequestMapping("/api")
public class C_userResource {

    private final Logger log = LoggerFactory.getLogger(C_userResource.class);

    private static final String ENTITY_NAME = "c_user";

    private final C_userService c_userService;

    public C_userResource(C_userService c_userService) {
        this.c_userService = c_userService;
    }

    /**
     * POST  /c-users : Create a new c_user.
     *
     * @param c_user the c_user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_user, or with status 400 (Bad Request) if the c_user has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-users")
    @Timed
    public ResponseEntity<C_user> createC_user(@RequestBody C_user c_user) throws URISyntaxException {
        log.debug("REST request to save C_user : {}", c_user);
        if (c_user.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new c_user cannot already have an ID")).body(null);
        }
        C_user result = c_userService.save(c_user);
        return ResponseEntity.created(new URI("/api/c-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-users : Updates an existing c_user.
     *
     * @param c_user the c_user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_user,
     * or with status 400 (Bad Request) if the c_user is not valid,
     * or with status 500 (Internal Server Error) if the c_user couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-users")
    @Timed
    public ResponseEntity<C_user> updateC_user(@RequestBody C_user c_user) throws URISyntaxException {
        log.debug("REST request to update C_user : {}", c_user);
        if (c_user.getId() == null) {
            return createC_user(c_user);
        }
        C_user result = c_userService.save(c_user);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, c_user.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-users : get all the c_users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_users in body
     */
    @GetMapping("/c-users")
    @Timed
    public ResponseEntity<List<C_user>> getAllC_users(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of C_users");
        Page<C_user> page = c_userService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-users/:id : get the "id" c_user.
     *
     * @param id the id of the c_user to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_user, or with status 404 (Not Found)
     */
    @GetMapping("/c-users/{id}")
    @Timed
    public ResponseEntity<C_user> getC_user(@PathVariable Long id) {
        log.debug("REST request to get C_user : {}", id);
        C_user c_user = c_userService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(c_user));
    }

    /**
     * DELETE  /c-users/:id : delete the "id" c_user.
     *
     * @param id the id of the c_user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteC_user(@PathVariable Long id) {
        log.debug("REST request to delete C_user : {}", id);
        c_userService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/c-users?query=:query : search for the c_user corresponding
     * to the query.
     *
     * @param query the query of the c_user search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/c-users")
    @Timed
    public ResponseEntity<List<C_user>> searchC_users(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of C_users for query {}", query);
        Page<C_user> page = c_userService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/c-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
