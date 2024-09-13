/*
 * Copyrights and Licenses
 * 
 * Copyright (c) 2015-2016 by the Ministry of Justice. All rights reserved. Redistribution and use
 * in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer. - Redistributions in binary form
 * must reproduce the above copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the distribution. - Products derived
 * from this software may not be called "XHIBIT Public Display Manager" nor may
 * "XHIBIT Public Display Manager" appear in their names without prior written permission of the
 * Ministry of Justice. - Redistributions of any form whatsoever must retain the following
 * acknowledgment: "This product includes XHIBIT Public Display Manager." This software is provided
 * "as is" and any expressed or implied warranties, including, but not limited to, the implied
 * warranties of merchantability and fitness for a particular purpose are disclaimed. In no event
 * shall the Ministry of Justice or its contributors be liable for any direct, indirect, incidental,
 * special, exemplary, or consequential damages (including, but not limited to, procurement of
 * substitute goods or services; loss of use, data, or profits; or business interruption). However
 * caused any on any theory of liability, whether in contract, strict liability, or tort (including
 * negligence or otherwise) arising in any way out of the use of this software, even if advised of
 * the possibility of such damage.
 */

package uk.gov.hmcts.pdm.publicdisplay.manager.security;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.SaltGenerator;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.keygen.KeyGenerators;

/**
 * A singleton class that has utility methods for encrypting and decrypting session data.
 * 
 * @author uphillj
 *
 */
@SuppressWarnings("PMD.LawOfDemeter")
public enum EncryptDecryptUtility {
    /**
     * Indicating that this is a singleton.
     */
    INSTANCE;
    
    private static final Logger LOG = LoggerFactory.getLogger(EncryptDecryptUtility.class);

    /** The encryption algorithm. */
    private static final String ALGORITHM = "PBEWithSHA1AndDESede";

    /** The encryption output. */
    private static final String STRING_OUTPUT = "hexadecimal";

    /** The encryption iterations. */
    private static final int ITERATIONS = 1000;

    /** The salt generator which is fixed so encrypted values are same for session. */
    private static final SaltGenerator SALT_GENERATOR =
        new StringFixedSaltGenerator(createRandomSalt());

    /**
     * Creates a random salt.
     *
     * @return the random salt
     */
    private static String createRandomSalt() {
        // Return random 16 bytes as a hex-encoded string
        return KeyGenerators.string().generateKey() + KeyGenerators.string().generateKey();
    }

    /**
     * Encrypt the supplied text with the random session key of the user.
     * 
     * @param textValue the value to be encrypted.
     * @return the encrypted text value.
     */
    public String encryptData(final String textValue) {
        LOG.debug("encryptData()");
        if (textValue == null) {
            throw new IllegalArgumentException("Null value passed in for encryption");
        }

        final StringEncryptor encryptor = createStringEncryptor(getKey());

        return encryptor.encrypt(textValue);
    }

    /**
     * Decrypt the supplied text with the random session key of the user.
     * 
     * @param textValue the value to be decrypted.
     * @return the decrypted text value.
     */
    public String decryptData(final String textValue) {
        LOG.debug("decryptData()");
        if (textValue == null) {
            throw new IllegalArgumentException("Null value passed in for decryption");
        }

        final StringEncryptor encryptor = createStringEncryptor(getKey());

        return encryptor.decrypt(textValue);
    }

    /**
     * Create string encryptor to encrypt sensitive data which uses a Jasypt encryptor as an
     * algorithm which does not require the unlimited strength JCE jars can be chosen.
     *
     * @param key the key
     * @return the string encryptor
     */
    private StringEncryptor createStringEncryptor(final String key) {
        final StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
        stringEncryptor.setPassword(key);
        stringEncryptor.setAlgorithm(ALGORITHM);
        stringEncryptor.setSaltGenerator(SALT_GENERATOR);
        stringEncryptor.setKeyObtentionIterations(ITERATIONS);
        stringEncryptor.setStringOutputType(STRING_OUTPUT);
        return stringEncryptor;
    }

    private String getKey() {
        return Integer.toString(
            SecurityContextHolder.getContext().getAuthentication().getPrincipal().hashCode());
    }

}
