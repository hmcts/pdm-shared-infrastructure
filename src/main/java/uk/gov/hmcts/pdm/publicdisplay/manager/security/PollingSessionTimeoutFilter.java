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

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Filter class to prevent background polling requests extending the session timeout indefinitely.
 * 
 * @author uphillj
 *
 */
public class PollingSessionTimeoutFilter extends GenericFilterBean {
    /** The logger. */
    private static final Logger LOG = LoggerFactory.getLogger(PollingSessionTimeoutFilter.class);

    /** The session attribute for storing the time of the last normal request. */
    private static final String POLLING_LAST_ACCESSED_TIME = "POLLING_LAST_ACCESSED_TIME";

    /** The url path helper instance. */
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    /** The list of polling urls. */
    private List<String> urls = new ArrayList<>();

    private final TimeUnit seconds = TimeUnit.SECONDS;

    /**
     * getUrls.
     * 
     * @return the urls
     */
    public List<String> getUrls() {
        return urls;
    }

    /**
     * setUrls.
     * 
     * @param urls the urls to set
     */
    public void setUrls(final List<String> urls) {
        this.urls = urls;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
        final FilterChain chain) throws IOException, ServletException {
        // Cast to http version of request
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        // If a http session exists then ok to check whether timeout reached
        final HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            // If this is a polling request, must check for session timeout
            if (urls.contains(getApplicationUrl(httpRequest))) {
                LOG.info("detected request");

                // If this is the first polling request since normal request,
                // store the time of the last normal request in the session
                Long lastAccess = (Long) session.getAttribute(POLLING_LAST_ACCESSED_TIME);
                if (lastAccess == null) {
                    lastAccess = session.getLastAccessedTime();
                    session.setAttribute(POLLING_LAST_ACCESSED_TIME, lastAccess);
                    LOG.info("stored last accessed time - {}", new Date(lastAccess));
                }

                // If the session timeout has passed since the last normal request,
                // must manually trigger a session expiration to timeout session
                if ((seconds.toMillis(session.getMaxInactiveInterval())
                    - (System.currentTimeMillis() - lastAccess)) < 0) {
                    session.invalidate();
                    LOG.info("expired session");
                }

                // Else normal request resets timeout so remove session attribute
            } else {
                session.removeAttribute(POLLING_LAST_ACCESSED_TIME);
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * Return the request uri minus the context path, with the query string appended.
     *
     * @param request the request
     * @return the application url
     */
    private String getApplicationUrl(final HttpServletRequest request) {
        // Get the application path component of the request uri and the query string
        final String applicationPath = urlPathHelper.getPathWithinApplication(request);
        final String queryString = request.getQueryString();

        // Create url from application path and query string if present
        final StringBuilder url = new StringBuilder(applicationPath);
        if (queryString != null) {
            url.append('?');
            url.append(queryString);
        }
        return url.toString();
    }
}
