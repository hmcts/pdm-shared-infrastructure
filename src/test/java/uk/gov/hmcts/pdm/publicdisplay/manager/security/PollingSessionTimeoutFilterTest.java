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

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.easymock.EasyMockExtension;
import org.joda.time.DateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.support.GenericWebApplicationContext;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * The class PollingSessionTimeoutFilterTest.
 * 
 * @author uphillj
 *
 */
@ExtendWith(EasyMockExtension.class)
abstract class PollingSessionTimeoutFilterTest extends AbstractJUnit {

    /** The base url for the test. */
    protected static final String BASE_URL = "/testurl";

    /** The polling url for the test. */
    protected static final String POLLING_URL = BASE_URL + "?polling";

    /** The polling test date. */
    protected static final DateTime TEST_DATE = DateTime.now().minusMinutes(15);

    protected static final String NOT_EQUAL = "Not equal";

    protected static final String NOT_NULL = "Not null";

    protected static final String FALSE = "False";

    protected static final String POLLING_LAST_ACCESSED_TIME = "POLLING_LAST_ACCESSED_TIME";

    /** The class under test. */
    protected PollingSessionTimeoutFilter classUnderTest;

    /** The mock mvc. */
    protected MockMvc mockMvc;

    /** The mock session. */
    protected MockHttpSession mockSession;

    GenericWebApplicationContext webContext;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new PollingSessionTimeoutFilter();

        // Setup test polling url
        final List<String> urls = getTestUrlList();
        classUnderTest.setUrls(urls);

        // Create mock objects required for web app context setup
        final ServletContext servletContext = (ServletContext) new MockServletContext();
        mockSession = new MockHttpSession();
        mockSession.setMaxInactiveInterval(1800);

        // Create a web application context from mock servlet context
        webContext = new GenericWebApplicationContext((ServletContext) servletContext);
        webContext.refresh();

        // Setup the mock version of the modelMvc with the filter
        mockMvc =
            MockMvcBuilders.webAppContextSetup(webContext).addFilter(classUnderTest, "/*").build();
    }

    /**
     * Teardown.
     */
    @AfterEach
    public void teardown() {
        webContext.close();
    }

    /**
     * Test get urls.
     */
    @Test
    void testGetUrls() {
        final List<String> urls = getTestUrlList();

        // Test the get for the same urls populated in setup
        final List<String> results = classUnderTest.getUrls();

        assertEquals(urls.size(), results.size(), NOT_EQUAL);
    }

    /**
     * Test no session.
     *
     * @throws Exception the exception
     */
    @Test
    void testNoSession() throws Exception {
        // Perform the test
        final MvcResult results = mockMvc.perform(get(POLLING_URL)).andReturn();

        // Assert there is no session
        assertNull(results.getRequest().getSession(false), NOT_NULL);
    }

    /**
     * Test non-polling url.
     *
     * @throws Exception the exception
     */
    @Test
    void testNonPollingUrlCall() throws Exception {
        // Setup test objects
        mockSession.setAttribute(POLLING_LAST_ACCESSED_TIME, TEST_DATE.getMillis());

        // Perform the test
        final MvcResult results = mockMvc.perform(get(BASE_URL).session(mockSession)).andReturn();

        // Assert there is no last accessed time anymore
        assertNull(getPollingLastAccessedTime(results), NOT_NULL);
    }

    /**
     * Gets the polling last accessed time from session if present or null if no last access time
     * exists.
     *
     * @param results the results
     * @return the polling last accessed time
     */
    protected DateTime getPollingLastAccessedTime(final MvcResult results) {
        DateTime dateTime = null;

        // Retrieve date time millis from session
        final HttpSession session = (HttpSession) results.getRequest().getSession();
        final Long lastAccessMillis = (Long) session.getAttribute(POLLING_LAST_ACCESSED_TIME);

        // If date time millis is in session, create date time object
        if (lastAccessMillis != null) {
            dateTime = new DateTime(lastAccessMillis);
        }

        // Return date time
        return dateTime;
    }

    /**
     * Gets the test url list.
     *
     * @return the test url list
     */
    private List<String> getTestUrlList() {
        final List<String> urls = new ArrayList<>();
        urls.add(POLLING_URL);
        return urls;
    }
}
