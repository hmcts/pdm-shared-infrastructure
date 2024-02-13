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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ldap.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The Class LogonController.
 *
 * @author uphillj
 */

@Controller
public class LogonController {

    /** The Constant HEADER_REQUESTED_WITH. */
    private static final String HEADER_REQUESTED_WITH = "X-Requested-With";

    /** The Constant HEADER_LOGOUT_URL. */
    private static final String HEADER_LOGOUT_URL = "X-Logout-URL";

    /** The Constant AJAX_REQUEST. */
    private static final String AJAX_REQUEST = "XMLHttpRequest";

    /** The Constant INVALID_SESSION. */
    private static final String INVALID_SESSION = "invalidSession";

    /** The Constant INVALID_TOKEN. */
    private static final String INVALID_TOKEN = "invalidToken";

    /** The Constant MAPPING_HOME. */
    private static final String MAPPING_HOME = "/home";

    /** The Constant MAPPING_LOGIN. */
    private static final String MAPPING_LOGIN = "/login";

    /** The Constant MAPPING_LOGIN_ERROR. */
    private static final String MAPPING_LOGIN_ERROR = "/loginError";

    /** The Constant MAPPING_LOGOUT_SUCCESS. */
    private static final String MAPPING_LOGOUT_SUCCESS = "/logoutSuccess";

    /** The Constant MAPPING_INVALID_SESSION. */
    private static final String MAPPING_INVALID_SESSION = "/" + INVALID_SESSION;

    /** The Constant MAPPING_INVALID_TOKEN. */
    private static final String MAPPING_INVALID_TOKEN = "/" + INVALID_TOKEN;

    /** The Constant VIEW_LOGIN. */
    private static final String VIEW_LOGIN = "/logon/signin";

    /** The Constant VIEW_LOGOUT. */
    private static final String VIEW_LOGOUT = "logon/logout";

    /** The Constant MODEL_ERROR. */
    private static final String MODEL_ERROR = "error";

    /**
     * Home.
     * 
     * @return the string
     */
    @RequestMapping(value = MAPPING_HOME, method = RequestMethod.GET)
    public String home() {
        return "redirect:dashboard/dashboard";
    }

    /**
     * Login.
     *
     * @return the string
     */
    @RequestMapping(value = MAPPING_LOGIN, method = RequestMethod.GET)
    public String login(HttpSession session, HttpServletRequest req, ModelMap model) {
        AuthenticationException ase =
            (AuthenticationException) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (ase != null) {
            model.addAttribute(MODEL_ERROR, ase.getMessage());
        }
        return VIEW_LOGIN;
    }

    /**
     * Login error.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = MAPPING_LOGIN_ERROR, method = RequestMethod.POST)
    public String loginError(final Model model) {
        model.addAttribute(MODEL_ERROR, "true");
        return VIEW_LOGIN;
    }

    /**
     * Logout success.
     *
     * @return the string
     */
    @RequestMapping(value = MAPPING_LOGOUT_SUCCESS, method = RequestMethod.GET)
    public String logoutSuccess() {
        return VIEW_LOGOUT;
    }

    /**
     * Invalid session.
     *
     * @param request the request
     * @param response the response
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = MAPPING_INVALID_SESSION, method = RequestMethod.GET)
    public String invalidSession(final HttpServletRequest request,
        final HttpServletResponse response, final Model model) {
        // If this is an ajax call, we need to set the status code to
        // 401 so the global ajax error handler can detect this event
        // and manually redirect the user to the correct logout page.
        if (isAjaxRequest(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HEADER_LOGOUT_URL, INVALID_SESSION);
        }

        model.addAttribute(MODEL_ERROR, INVALID_SESSION);
        return VIEW_LOGOUT;
    }

    /**
     * Invalid csrf token.
     *
     * @param request the request
     * @param response the response
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = MAPPING_INVALID_TOKEN, method = RequestMethod.GET)
    public String invalidToken(final HttpServletRequest request, final HttpServletResponse response,
        final Model model) {
        // If this is an ajax call, we need to set the status code to
        // 401 so the global ajax error handler can detect this event
        // and manually redirect the user to the correct logout page.
        if (isAjaxRequest(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HEADER_LOGOUT_URL, INVALID_TOKEN);
        }

        // Ensure session is invalidated
        invalidateSession(request);

        model.addAttribute(MODEL_ERROR, INVALID_TOKEN);
        return VIEW_LOGOUT;
    }

    /**
     * Invalidate the session.
     *
     * @param request the request
     */
    protected void invalidateSession(final HttpServletRequest request) {
        // Clear security context
        SecurityContextHolder.clearContext();

        // Invalidate session
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Returns true if the request is an ajax call.
     * 
     * @param request the request
     * @return true if the request is an ajax call
     */
    protected boolean isAjaxRequest(final HttpServletRequest request) {
        // Checks for the header value that jQuery inserts on all ajax calls
        return AJAX_REQUEST.equals(request.getHeader(HEADER_REQUESTED_WITH));
    }
}
