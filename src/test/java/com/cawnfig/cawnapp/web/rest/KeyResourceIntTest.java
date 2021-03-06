package com.cawnfig.cawnapp.web.rest;

import com.cawnfig.cawnapp.CawnappApp;

import com.cawnfig.cawnapp.domain.Key;
import com.cawnfig.cawnapp.repository.KeyRepository;
import com.cawnfig.cawnapp.repository.search.KeySearchRepository;
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
 * Test class for the KeyResource REST controller.
 *
 * @see KeyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CawnappApp.class)
public class KeyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SECURE = false;
    private static final Boolean UPDATED_IS_SECURE = true;

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private KeySearchRepository keySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKeyMockMvc;

    private Key key;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KeyResource keyResource = new KeyResource(keyRepository, keySearchRepository);
        this.restKeyMockMvc = MockMvcBuilders.standaloneSetup(keyResource)
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
    public static Key createEntity(EntityManager em) {
        Key key = new Key()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .is_secure(DEFAULT_IS_SECURE);
        return key;
    }

    @Before
    public void initTest() {
        keySearchRepository.deleteAll();
        key = createEntity(em);
    }

    @Test
    @Transactional
    public void createKey() throws Exception {
        int databaseSizeBeforeCreate = keyRepository.findAll().size();

        // Create the Key
        restKeyMockMvc.perform(post("/api/keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(key)))
            .andExpect(status().isCreated());

        // Validate the Key in the database
        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeCreate + 1);
        Key testKey = keyList.get(keyList.size() - 1);
        assertThat(testKey.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKey.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testKey.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testKey.isIs_secure()).isEqualTo(DEFAULT_IS_SECURE);

        // Validate the Key in Elasticsearch
        Key keyEs = keySearchRepository.findOne(testKey.getId());
        assertThat(keyEs).isEqualToComparingFieldByField(testKey);
    }

    @Test
    @Transactional
    public void createKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keyRepository.findAll().size();

        // Create the Key with an existing ID
        key.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyMockMvc.perform(post("/api/keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(key)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKeys() throws Exception {
        // Initialize the database
        keyRepository.saveAndFlush(key);

        // Get all the keyList
        restKeyMockMvc.perform(get("/api/keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(key.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].is_secure").value(hasItem(DEFAULT_IS_SECURE.booleanValue())));
    }

    @Test
    @Transactional
    public void getKey() throws Exception {
        // Initialize the database
        keyRepository.saveAndFlush(key);

        // Get the key
        restKeyMockMvc.perform(get("/api/keys/{id}", key.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(key.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.is_secure").value(DEFAULT_IS_SECURE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingKey() throws Exception {
        // Get the key
        restKeyMockMvc.perform(get("/api/keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKey() throws Exception {
        // Initialize the database
        keyRepository.saveAndFlush(key);
        keySearchRepository.save(key);
        int databaseSizeBeforeUpdate = keyRepository.findAll().size();

        // Update the key
        Key updatedKey = keyRepository.findOne(key.getId());
        updatedKey
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .is_secure(UPDATED_IS_SECURE);

        restKeyMockMvc.perform(put("/api/keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKey)))
            .andExpect(status().isOk());

        // Validate the Key in the database
        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeUpdate);
        Key testKey = keyList.get(keyList.size() - 1);
        assertThat(testKey.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKey.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testKey.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testKey.isIs_secure()).isEqualTo(UPDATED_IS_SECURE);

        // Validate the Key in Elasticsearch
        Key keyEs = keySearchRepository.findOne(testKey.getId());
        assertThat(keyEs).isEqualToComparingFieldByField(testKey);
    }

    @Test
    @Transactional
    public void updateNonExistingKey() throws Exception {
        int databaseSizeBeforeUpdate = keyRepository.findAll().size();

        // Create the Key

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKeyMockMvc.perform(put("/api/keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(key)))
            .andExpect(status().isCreated());

        // Validate the Key in the database
        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKey() throws Exception {
        // Initialize the database
        keyRepository.saveAndFlush(key);
        keySearchRepository.save(key);
        int databaseSizeBeforeDelete = keyRepository.findAll().size();

        // Get the key
        restKeyMockMvc.perform(delete("/api/keys/{id}", key.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean keyExistsInEs = keySearchRepository.exists(key.getId());
        assertThat(keyExistsInEs).isFalse();

        // Validate the database is empty
        List<Key> keyList = keyRepository.findAll();
        assertThat(keyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchKey() throws Exception {
        // Initialize the database
        keyRepository.saveAndFlush(key);
        keySearchRepository.save(key);

        // Search the key
        restKeyMockMvc.perform(get("/api/_search/keys?query=id:" + key.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(key.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].is_secure").value(hasItem(DEFAULT_IS_SECURE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Key.class);
        Key key1 = new Key();
        key1.setId(1L);
        Key key2 = new Key();
        key2.setId(key1.getId());
        assertThat(key1).isEqualTo(key2);
        key2.setId(2L);
        assertThat(key1).isNotEqualTo(key2);
        key1.setId(null);
        assertThat(key1).isNotEqualTo(key2);
    }
}
