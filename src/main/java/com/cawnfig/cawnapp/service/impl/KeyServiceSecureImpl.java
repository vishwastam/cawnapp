package com.cawnfig.cawnapp.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cawnfig.cawnapp.domain.Key;
import com.cawnfig.cawnapp.repository.KeyRepository;
import com.cawnfig.cawnapp.repository.search.KeySearchRepository;
import com.cawnfig.cawnapp.service.util.CryptoHelper;

/**
 * Service Implementation for managing a Key in secure manner.
 */
@Service
@Transactional
public class KeyServiceSecureImpl extends KeyServiceImpl{

	private CryptoHelper crypto;

	public KeyServiceSecureImpl(KeyRepository keyRepository,
			KeySearchRepository keySearchRepository) throws Exception {
		super(keyRepository, keySearchRepository);
		crypto = new CryptoHelper();
	}

	@Override
	public Key save(Key key) throws Exception {
        crypto.encrypt(key.getValue());
        return super.save(key);
	}

}
