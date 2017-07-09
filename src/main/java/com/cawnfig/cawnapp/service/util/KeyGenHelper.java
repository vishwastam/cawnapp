package com.cawnfig.cawnapp.service.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyGenHelper {

	private final static Logger log = LoggerFactory.getLogger(KeyGenHelper.class);

    private static final String keyDir  = System.getProperty("java.io.tmpdir");;
    private static final String keyName = "secret.key";
	private final static String ALGO = "AES";

	public static Key getKey() throws Exception{
        return loadSymmetricAESKey(keyDir, ALGO);           
	}

	private static Key loadSymmetricAESKey(String keydir, String algo) throws Exception{
        //Read private key from file.
		log.info("loading key from " + keyDir + "/" + keyName);
        File keyFile = new File(keydir + "/" + keyName);
        FileInputStream keyfis = new FileInputStream(keyFile);
        byte[] encodedPrivateKey = new byte[(int)keyFile.length()];
        keyfis.read(encodedPrivateKey);
        keyfis.close(); 

        //Generate secret key.
        return new SecretKeySpec(encodedPrivateKey, "AES");	}

	public static void main(String args[]) throws Exception{
		generateAndSaveKey();
	}

	public static void generateAndSaveKey() throws Exception{
		Key key = generateSymmetricKey();
		writeToFile(key);
	}

	private static Key generateSymmetricKey() throws Exception {
		log.info("Generatinging a new Key");
		KeyGenerator generator = KeyGenerator.getInstance( "AES" );
		SecretKey key = generator.generateKey();
		return key;
	}

	private static void writeToFile(Key key) throws Exception{
		log.info("Saving key at location: " + keyDir + "/" + keyName);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
        		key.getEncoded());
        FileOutputStream keyfos = new FileOutputStream(keyDir + "/" + keyName);
        keyfos.write(x509EncodedKeySpec.getEncoded());
        keyfos.close();
	}

}