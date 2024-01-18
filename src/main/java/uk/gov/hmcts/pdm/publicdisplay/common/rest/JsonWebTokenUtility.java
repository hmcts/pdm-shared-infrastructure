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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;


/**
 * A singleton class that has utility methods for generating and parsing json web tokens.
 *
 * @author uphillj
 */
public enum JsonWebTokenUtility {
    /**
     * Indicating that this is a singleton.
     */
    INSTANCE;

    /** The Constant REQUEST_HEADER_AUTHORIZATION. */
    public static final String REQUEST_HEADER_AUTHORIZATION = "Authorization";

    /** The Constant REQUEST_HEADER_BEARER. */
    public static final String REQUEST_HEADER_BEARER = "Bearer";

    /** The LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWebTokenUtility.class);

    /** The Constant CLAIM_ROLE. */
    private static final String CLAIM_ROLE = "role";

    /**
     * Gets the current date with the supplied offset in seconds added.
     *
     * @param offset the offset in seconds
     * @return the current date plus the offset in seconds
     */
    private Date getDate(final int offset) {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, offset);
        return calendar.getTime();
    }

    /**
     * Generate token.
     *
     * @param tokenType the type of token to generate
     * @param id the id
     * @param expiry the token expiry in seconds
     * @return the token
     */
    public String generateToken(final JsonWebTokenType tokenType, final String id,
        final Integer expiry) {
        final Claims claims = Jwts.claims().setSubject(tokenType.getSubject());
        if (expiry != null) {
            claims.setId(id);
            claims.setNotBefore(getDate(-expiry));
            claims.setExpiration(getDate(expiry));
        }
        claims.put(CLAIM_ROLE, tokenType.getRole());
        return Jwts.builder().setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, tokenType.getKey()).compact();
    }

    /**
     * Checks if token is valid.
     *
     * @param tokenType the type of token
     * @param token the token
     * @return true, if token is valid
     */
    public boolean isTokenValid(final JsonWebTokenType tokenType, final String token) {
        try {
            boolean valid = false;
            final Claims body = Jwts.parser().requireSubject(tokenType.getSubject())
                .setSigningKey(tokenType.getKey()).parseClaimsJws(token).getBody();
            if (((String) body.get(CLAIM_ROLE)).equals(tokenType.getRole())) {
                valid = true;
            } else {
                LOGGER.error("JSON web token body is incorrect");
            }
            return valid;
        } catch (final JwtException ex) {
            LOGGER.error("JSON web token signature is invalid", ex);
            return false;
        } catch (final ClassCastException ex) {
            LOGGER.error("JSON web token body is invalid", ex);
            return false;
        }
    }
}
