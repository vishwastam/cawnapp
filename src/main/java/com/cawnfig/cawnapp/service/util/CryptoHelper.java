package com.cawnfig.cawnapp.service.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util to help encrypt an entity.
 * Reference: https://gist.github.com/twuni/5668121
 */
public class CryptoHelper {

	protected final Logger log = LoggerFactory.getLogger(CryptoHelper.class);

	public String encrypt( String plaintext ) throws Exception {
		return encrypt( generateIV(), plaintext );
	}

	private byte [] generateIV() {
		SecureRandom random = new SecureRandom();
		byte [] iv = new byte [16];
		random.nextBytes( iv );
		return iv;
	}

	private String encrypt( byte [] iv, String plaintext ) throws Exception {

		byte [] decrypted = plaintext.getBytes();
		byte [] encrypted = encrypt( iv, decrypted );

		return base64Encode(iv, encrypted);
	}

	private byte [] encrypt( byte [] iv, byte [] plaintext ) throws Exception {
		Cipher cipher = Cipher.getInstance( getKey().getAlgorithm() + "/CBC/PKCS5Padding" );
		cipher.init( Cipher.ENCRYPT_MODE, getKey(), new IvParameterSpec( iv ) );
		return cipher.doFinal( plaintext );
	}

	private Key getKey() throws Exception {
		return KeyGenHelper.getKey();
	}

	private String base64Encode(byte[] iv, byte[] encrypted) {
		StringBuilder ciphertext = new StringBuilder();

		ciphertext.append( Base64.encodeBase64String( iv ) );
		ciphertext.append( ":" );
		ciphertext.append( Base64.encodeBase64String( encrypted ) );

		return ciphertext.toString();
	}

	// TODO: Move to SDK
	public String decrypt( String ciphertext ) throws Exception {
		String [] parts = ciphertext.split( ":" );
		byte [] iv = Base64.decodeBase64( parts[0] );
		byte [] encrypted = Base64.decodeBase64( parts[1] );
		byte [] decrypted = decrypt( iv, encrypted );
		return new String( decrypted );
	}

	private byte [] decrypt( byte [] iv, byte [] ciphertext ) throws Exception {
		Cipher cipher = Cipher.getInstance( getKey().getAlgorithm() + "/CBC/PKCS5Padding" );
		cipher.init( Cipher.DECRYPT_MODE, getKey(), new IvParameterSpec( iv ) );
		return cipher.doFinal( ciphertext );
	}

}
