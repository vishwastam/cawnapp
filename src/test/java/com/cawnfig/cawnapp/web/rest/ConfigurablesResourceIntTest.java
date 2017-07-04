package com.cawnfig.cawnapp.web.rest;

import com.cawnfig.cawnapp.CawnappApp;

import com.cawnfig.cawnapp.domain.Configurables;
import com.cawnfig.cawnapp.repository.ConfigurablesRepository;
import com.cawnfig.cawnapp.service.ConfigurablesService;
import com.cawnfig.cawnapp.repository.search.ConfigurablesSearchRepository;
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
 * Test class for the ConfigurablesResource REST controller.
 *
 * @see ConfigurablesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CawnappApp.class)
public class ConfigurablesResourceIntTest {

    private static final String DEFAULT_ORGANISATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATION = "BBBBBBBBBB";

    private static final String DEFAULT_APP = "AAAAAAAAAA";
    private static final String UPDATED_APP = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ConfigurablesRepository configurablesRepository;

    @Autowired
    private ConfigurablesService configurablesService;

    @Autowired
    private ConfigurablesSearchRepository configurablesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConfigurablesMockMvc;

    private Configurables configurables;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConfigurablesResource configurablesResource = new ConfigurablesResource(configurablesService);
        this.restConfigurablesMockMvc = MockMvcBuilders.standaloneSetup(configurablesResource)
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
    public static Configurables createEntity(EntityManager em) {
        Configurables configurables = new Configurables()
            .organisation(DEFAULT_ORGANISATION)
            .app(DEFAULT_APP)
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return configurables;
    }

    @Before
    public void initTest() {
        configurablesSearchRepository.deleteAll();
        configurables = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfigurables() throws Exception {
        int databaseSizeBeforeCreate = configurablesRepository.findAll().size();

        // Create the Configurables
        restConfigurablesMockMvc.perform(post("/api/configurables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurables)))
            .andExpect(status().isCreated());

        // Validate the Configurables in the database
        List<Configurables> configurablesList = configurablesRepository.findAll();
        assertThat(configurablesList).hasSize(databaseSizeBeforeCreate + 1);
        Configurables testConfigurables = configurablesList.get(configurablesList.size() - 1);
        assertThat(testConfigurables.getOrganisation()).isEqualTo(DEFAULT_ORGANISATION);
        assertThat(testConfigurables.getApp()).isEqualTo(DEFAULT_APP);
        assertThat(testConfigurables.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testConfigurables.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the Configurables in Elasticsearch
        Configurables configurablesEs = configurablesSearchRepository.findOne(testConfigurables.getId());
        assertThat(configurablesEs).isEqualToComparingFieldByField(testConfigurables);
    }

    @Test
    @Transactional
    public void createConfigurablesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = configurablesRepository.findAll().size();

        // Create the Configurables with an existing ID
        configurables.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigurablesMockMvc.perform(post("/api/configurables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurables)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Configurables> configurablesList = configurablesRepository.findAll();
        assertThat(configurablesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrganisationIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurablesRepository.findAll().size();
        // set the field null
        configurables.setOrganisation(null);

        // Create the Configurables, which fails.

        restConfigurablesMockMvc.perform(post("/api/configurables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurables)))
            .andExpect(status().isBadRequest());

        List<Configurables> configurablesList = configurablesRepository.findAll();
        assertThat(configurablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurablesRepository.findAll().size();
        // set the field null
        configurables.setApp(null);

        // Create the Configurables, which fails.

        restConfigurablesMockMvc.perform(post("/api/configurables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurables)))
            .andExpect(status().isBadRequest());

        List<Configurables> configurablesList = configurablesRepository.findAll();
        assertThat(configurablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurablesRepository.findAll().size();
        // set the field null
        configurables.setKey(null);

        // Create the Configurables, which fails.

        restConfigurablesMockMvc.perform(post("/api/configurables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurables)))
            .andExpect(status().isBadRequest());

        List<Configurables> configurablesList = configurablesRepository.findAll();
        assertThat(configurablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurablesRepository.findAll().size();
        // set the field null
        configurables.setValue(null);

        // Create the Configurables, which fails.

        restConfigurablesMockMvc.perform(post("/api/configurables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurables)))
            .andExpect(status().isBadRequest());

        List<Configurables> configurablesList = configurablesRepository.findAll();
        assertThat(configurablesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConfigurables() throws Exception {
        // Initialize the database
        configurablesRepository.saveAndFlush(configurables);

        // Get all the configurablesList
        restConfigurablesMockMvc.perform(get("/api/configurables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configurables.getId().intValue())))
            .andExpect(jsonPath("$.[*].organisation").value(hasItem(DEFAULT_ORGANISATION.toString())))
            .andExpect(jsonPath("$.[*].app").value(hasItem(DEFAULT_APP.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getConfigurables() throws Exception {
        // Initialize the database
        configurablesRepository.saveAndFlush(configurables);

        // Get the configurables
        restConfigurablesMockMvc.perform(get("/api/configurables/{id}", configurables.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(configurables.getId().intValue()))
            .andExpect(jsonPath("$.organisation").value(DEFAULT_ORGANISATION.toString()))
            .andExpect(jsonPath("$.app").value(DEFAULT_APP.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConfigurables() throws Exception {
        // Get the configurables
        restConfigurablesMockMvc.perform(get("/api/configurables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfigurables() throws Exception {
        // Initialize the database
        configurablesService.save(configurables);

        int databaseSizeBeforeUpdate = configurablesRepository.findAll().size();

        // Update the configurables
        Configurables updatedConfigurables = configurablesRepository.findOne(configurables.getId());
        updatedConfigurables
            .organisation(UPDATED_ORGANISATION)
            .app(UPDATED_APP)
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);

        restConfigurablesMockMvc.perform(put("/api/configurables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConfigurables)))
            .andExpect(status().isOk());

        // Validate the Configurables in the database
        List<Configurables> configurablesList = configurablesRepository.findAll();
        assertThat(configurablesList).hasSize(databaseSizeBeforeUpdate);
        Configurables testConfigurables = configurablesList.get(configurablesList.size() - 1);
        assertThat(testConfigurables.getOrganisation()).isEqualTo(UPDATED_ORGANISATION);
        assertThat(testConfigurables.getApp()).isEqualTo(UPDATED_APP);
        assertThat(testConfigurables.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testConfigurables.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the Configurables in Elasticsearch
        Configurables configurablesEs = configurablesSearchRepository.findOne(testConfigurables.getId());
        assertThat(configurablesEs).isEqualToComparingFieldByField(testConfigurables);
    }

    @Test
    @Transactional
    public void updateNonExistingConfigurables() throws Exception {
        int databaseSizeBeforeUpdate = configurablesRepository.findAll().size();

        // Create the Configurables

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConfigurablesMockMvc.perform(put("/api/configurables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurables)))
            .andExpect(status().isCreated());

        // Validate the Configurables in the database
        List<Configurables> configurablesList = configurablesRepository.findAll();
        assertThat(configurablesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConfigurables() throws Exception {
        // Initialize the database
        configurablesService.save(configurables);

        int databaseSizeBeforeDelete = configurablesRepository.findAll().size();

        // Get the configurables
        restConfigurablesMockMvc.perform(delete("/api/configurables/{id}", configurables.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean configurablesExistsInEs = configurablesSearchRepository.exists(configurables.getId());
        assertThat(configurablesExistsInEs).isFalse();

        // Validate the database is empty
        List<Configurables> configurablesList = configurablesRepository.findAll();
        assertThat(configurablesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchConfigurables() throws Exception {
        // Initialize the database
        configurablesService.save(configurables);

        // Search the configurables
        restConfigurablesMockMvc.perform(get("/api/_search/configurables?query=id:" + configurables.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configurables.getId().intValue())))
            .andExpect(jsonPath("$.[*].organisation").value(hasItem(DEFAULT_ORGANISATION.toString())))
            .andExpect(jsonPath("$.[*].app").value(hasItem(DEFAULT_APP.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Configurables.class);
        Configurables configurables1 = new Configurables();
        configurables1.setId(1L);
        Configurables configurables2 = new Configurables();
        configurables2.setId(configurables1.getId());
        assertThat(configurables1).isEqualTo(configurables2);
        configurables2.setId(2L);
        assertThat(configurables1).isNotEqualTo(configurables2);
        configurables1.setId(null);
        assertThat(configurables1).isNotEqualTo(configurables2);
    }
}
