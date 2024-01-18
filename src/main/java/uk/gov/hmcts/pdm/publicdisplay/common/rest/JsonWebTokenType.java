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

package uk.gov.hmcts.pdm.publicdisplay.common.rest;

/**
 * The Enum JsonWebTokenType.
 *
 * @author uphillj
 */
public enum JsonWebTokenType {
    /** The display manager key, subject & role. */
    DISPLAY_MANAGER("XSSsTRwj2uCnRj0OGSbC4YD0q6dQUuyNJmUy3fd", "display_manager",
        "ROLE_DISPLAY_MANAGER"),

    /** The local proxy key, subject & role. */
    LOCAL_PROXY("KU4mS1UedMnn6NE1MeXyK8MLdawabdCiOVAcFjd", "local_proxy", "ROLE_LOCAL_PROXY");

    /** The key. */
    private String key;

    /** The subject. */
    private String subject;

    /** The role. */
    private String role;

    /**
     * Instantiates a new json web token.
     *
     * @param key the key
     * @param subject the subject
     * @param role the role
     */
    JsonWebTokenType(final String key, final String subject, final String role) {
        this.key = key;
        this.subject = subject;
        this.role = role;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    String getKey() {
        return key;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    String getSubject() {
        return subject;
    }

    /**
     * Gets the role.
     *
     * @return the role
     */
    String getRole() {
        return role;
    }

}
