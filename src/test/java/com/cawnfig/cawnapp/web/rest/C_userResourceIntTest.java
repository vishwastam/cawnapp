package com.cawnfig.cawnapp.web.rest;

import com.cawnfig.cawnapp.CawnappApp;

import com.cawnfig.cawnapp.domain.C_user;
import com.cawnfig.cawnapp.repository.C_userRepository;
import com.cawnfig.cawnapp.service.C_userService;
import com.cawnfig.cawnapp.repository.search.C_userSearchRepository;
import com.cawnfig.cawnapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the C_userResource REST controller.
 *
 * @see C_userResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CawnappApp.class)
public class C_userResourceIntTest {

    @Autowired
    private C_userRepository c_userRepository;

    @Autowired
    private C_userService c_userService;

    @Autowired
    private C_userSearchRepository c_userSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restC_userMockMvc;

    private C_user c_user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_userResource c_userResource = new C_userResource(c_userService);
        this.restC_userMockMvc = MockMvcBuilders.standaloneSetup(c_userResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static C_user createEntity(EntityManager em) {
        C_user c_user = new C_user();
        return c_user;
    }

    @Before
    public void initTest() {
        c_userSearchRepository.deleteAll();
        c_user = createEntity(em);
    }

    @Test
    @Transactional
    public void createC_user() throws Exception {
        int databaseSizeBeforeCreate = c_userRepository.findAll().size();

        // Create the C_user
        restC_userMockMvc.perform(post("/api/c-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(c_user)))
            .andExpect(status().isCreated());

        // Validate the C_user in the database
        List<C_user> c_userList = c_userRepository.findAll();
        assertThat(c_userList).hasSize(databaseSizeBeforeCreate + 1);
        C_user testC_user = c_userList.get(c_userList.size() - 1);

        // Validate the C_user in Elasticsearch
        C_user c_userEs = c_userSearchRepository.findOne(testC_user.getId());
        assertThat(c_userEs).isEqualToComparingFieldByField(testC_user);
    }

    @Test
    @Transactional
    public void createC_userWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = c_userRepository.findAll().size();

        // Create the C_user with an existing ID
        c_user.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restC_userMockMvc.perform(post("/api/c-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(c_user)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<C_user> c_userList = c_userRepository.findAll();
        assertThat(c_userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllC_users() throws Exception {
        // Initialize the database
        c_userRepository.saveAndFlush(c_user);

        // Get all the c_userList
        restC_userMockMvc.perform(get("/api/c-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(c_user.getId().intValue())));
    }

    @Test
    @Transactional
    public void getC_user() throws Exception {
        // Initialize the database
        c_userRepository.saveAndFlush(c_user);

        // Get the c_user
        restC_userMockMvc.perform(get("/api/c-users/{id}", c_user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(c_user.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingC_user() throws Exception {
        // Get the c_user
        restC_userMockMvc.perform(get("/api/c-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_user() throws Exception {
        // Initialize the database
        c_userService.save(c_user);

        int databaseSizeBeforeUpdate = c_userRepository.findAll().size();

        // Update the c_user
        C_user updatedC_user = c_userRepository.findOne(c_user.getId());

        restC_userMockMvc.perform(put("/api/c-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedC_user)))
            .andExpect(status().isOk());

        // Validate the C_user in the database
        List<C_user> c_userList = c_userRepository.findAll();
        assertThat(c_userList).hasSize(databaseSizeBeforeUpdate);
        C_user testC_user = c_userList.get(c_userList.size() - 1);

        // Validate the C_user in Elasticsearch
        C_user c_userEs = c_userSearchRepository.findOne(testC_user.getId());
        assertThat(c_userEs).isEqualToComparingFieldByField(testC_user);
    }

    @Test
    @Transactional
    public void updateNonExistingC_user() throws Exception {
        int databaseSizeBeforeUpdate = c_userRepository.findAll().size();

        // Create the C_user

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restC_userMockMvc.perform(put("/api/c-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(c_user)))
            .andExpect(status().isCreated());

        // Validate the C_user in the database
        List<C_user> c_userList = c_userRepository.findAll();
        assertThat(c_userList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteC_user() throws Exception {
        // Initialize the database
        c_userService.save(c_user);

        int databaseSizeBeforeDelete = c_userRepository.findAll().size();

        // Get the c_user
        restC_userMockMvc.perform(delete("/api/c-users/{id}", c_user.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean c_userExistsInEs = c_userSearchRepository.exists(c_user.getId());
        assertThat(c_userExistsInEs).isFalse();

        // Validate the database is empty
        List<C_user> c_userList = c_userRepository.findAll();
        assertThat(c_userList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchC_user() throws Exception {
        // Initialize the database
        c_userService.save(c_user);

        // Search the c_user
        restC_userMockMvc.perform(get("/api/_search/c-users?query=id:" + c_user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(c_user.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(C_user.class);
        C_user c_user1 = new C_user();
        c_user1.setId(1L);
        C_user c_user2 = new C_user();
        c_user2.setId(c_user1.getId());
        assertThat(c_user1).isEqualTo(c_user2);
        c_user2.setId(2L);
        assertThat(c_user1).isNotEqualTo(c_user2);
        c_user1.setId(null);
        assertThat(c_user1).isNotEqualTo(c_user2);
    }
}
