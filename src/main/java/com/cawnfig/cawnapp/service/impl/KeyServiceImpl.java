package com.cawnfig.cawnapp.service.impl;

import com.cawnfig.cawnapp.service.KeyService;
import com.cawnfig.cawnapp.service.util.CryptoHelper;
import com.cawnfig.cawnapp.domain.Key;
import com.cawnfig.cawnapp.repository.KeyRepository;
import com.cawnfig.cawnapp.repository.search.KeySearchRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Key.
 */
@Service
@Transactional
public class KeyServiceImpl implements KeyService{

    private final Logger log = LoggerFactory.getLogger(KeyServiceImpl.class);

    private final KeyRepository keyRepository;

    private final KeySearchRepository keySearchRepository;

    public KeyServiceImpl(KeyRepository keyRepository, KeySearchRepository keySearchRepository) {
        this.keyRepository = keyRepository;
        this.keySearchRepository = keySearchRepository;
    }

    /**
     * Save a key.
     *
     * @param key the entity to save
     * @return the persisted entity
     */
    @Override
    public Key save(Key key) {
        log.debug("Request to save Key : {}", key);
        log.debug("Checking the is_secure flag. It is set to : {}", key.isIs_secure());
        if(key.isIs_secure()) {
        	encrypt(key);
        }
        Key result = keyRepository.save(key);
        keySearchRepository.save(result);
        return result;
    }

    /**
     * Encrypt the value contained in key and Save key.
     *
     * @param key the entity to save
     * @return the persisted entity
     * @throws Exception 
     */
	@Override
	public Key encryptAndsave(Key key) throws Exception {
		encrypt(key);
		return save(key);
	}

	/**
	 * Encrypt the key
	 * 
	 * @param key
	 * @throws Exception
	 */
	private void encrypt(Key key) {
		log.debug("Encrypting Key : {}", key);
		CryptoHelper cryptoHelper = new CryptoHelper();
		try {
			key.setValue(cryptoHelper.encrypt(key.getValue()));
		} catch (Exception e) {
			log.error("Failed to encrypt Key : {}", key);
		}
	}

    /**
     *  Get all the keys.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Key> findAll(Pageable pageable) {
        log.debug("Request to get all Keys");
        return keyRepository.findAll(pageable);
    }

    /**
     *  Get one key by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Key findOne(Long id) {
        log.debug("Request to get Key : {}", id);
        return keyRepository.findOne(id);
    }

    /**
     *  Delete the  key by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Key : {}", id);
        keyRepository.delete(id);
        keySearchRepository.delete(id);
    }

    /**
     * Search for the key corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Key> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Keys for query {}", query);
        Page<Key> result = keySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

}
