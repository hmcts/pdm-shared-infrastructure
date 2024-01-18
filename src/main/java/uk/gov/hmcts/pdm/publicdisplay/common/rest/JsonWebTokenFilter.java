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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * A servlet filter to ensure a valid json web token is in the request header.
 * 
 * @author uphillj
 *
 */
public class JsonWebTokenFilter implements Filter {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWebTokenFilter.class);

    /** The init parameter for token type. */
    private static final String INIT_PARAM_TOKEN = "token";

    private static final String AUTHORIZATION_HEADER =
        JsonWebTokenUtility.REQUEST_HEADER_AUTHORIZATION;

    private static final String BEARER_HEADER = JsonWebTokenUtility.REQUEST_HEADER_BEARER;
    private static final JsonWebTokenUtility JWTU_INSTANCE = JsonWebTokenUtility.INSTANCE;

    /** The required token type. */
    private JsonWebTokenType tokenType;

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        LOGGER.info("destroy method starts");

        LOGGER.info("destroy method ends");
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        LOGGER.info("init method starts");

        tokenType = JsonWebTokenType.valueOf(filterConfig.getInitParameter(INIT_PARAM_TOKEN));

        LOGGER.info("init method ends");
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
        final FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("doFilter method starts");

        // Cast to http versions of request and response
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        // If no token in request or the one supplied is invalid, return 401
        final String requestToken = getRequestToken(httpRequest);
        if (requestToken == null || !JWTU_INSTANCE.isTokenValid(tokenType, requestToken)) {
            LOGGER.error("Unauthorized JSON request as the web token is invalid");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            // Else request is authorized and so is allowed to proceed
        } else {
            filterChain.doFilter(request, response);
        }

        LOGGER.info("doFilter method ends");
    }

    /**
     * Gets the token from the request header.
     *
     * @param httpRequest the http request
     * @return the token or null if none supplied
     */
    private String getRequestToken(final HttpServletRequest httpRequest) {
        String requestToken = null;
        final String header = httpRequest.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_HEADER)) {
            requestToken = header.substring(BEARER_HEADER.length() + 1);
        }
        return requestToken;
    }
}
