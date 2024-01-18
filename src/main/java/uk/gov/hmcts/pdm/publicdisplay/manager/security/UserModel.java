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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.keygen.KeyGenerators;

import java.util.Collection;


/**
 * The Class User which has custom user details for the application.
 *
 * @author uphillj
 */
public class UserModel extends org.springframework.security.core.userdetails.User {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2987955969032941814L;

    /** The random key for encrypting sensitive user session data. */
    private final String key = createRandomKey();

    /**
     * Gets the random session key.
     *
     * @return the random session key
     */
    public String getKey() {
        return key;
    }

    /**
     * Instantiates a new user.
     *
     * @param username the username
     * @param password the password
     * @param enabled the enabled
     * @param accountNonExpired the account non expired
     * @param credentialsNonExpired the credentials non expired
     * @param accountNonLocked the account non locked
     * @param authorities the authorities
     */
    // CHECKSTYLE:OFF Turn off overridden constructor warning
    public UserModel(final String username, final String password, final boolean enabled,
        final boolean accountNonExpired, final boolean credentialsNonExpired,
        final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities) { // CHECKSTYLE:ON

        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
            accountNonLocked, authorities);
    }

    /**
     * Instantiates a new user.
     *
     * @param username the username
     * @param password the password
     * @param authorities the authorities
     */
    // CHECKSTYLE:OFF Turn off overridden constructor warning
    public UserModel(final String username, final String password,
        final Collection<? extends GrantedAuthority> authorities) { // CHECKSTYLE:ON

        super(username, password, authorities);
    }

    /**
     * Creates a random key.
     *
     * @return the random key
     */
    private String createRandomKey() {
        // Return random 16 bytes as a hex-encoded string
        return KeyGenerators.string().generateKey() + KeyGenerators.string().generateKey();
    }

}
