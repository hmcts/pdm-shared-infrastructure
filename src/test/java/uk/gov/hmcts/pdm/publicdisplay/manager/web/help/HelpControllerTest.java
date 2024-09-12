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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.help;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * The Class LogonController.
 *
 * @author boparaij
 */
@ExtendWith(EasyMockExtension.class)
@SuppressWarnings("PMD.LawOfDemeter")
class HelpControllerTest extends AbstractJUnit {

    private static final String NOT_EQUAL = "Not equal";

    private static final String FALSE = "False";

    private static final String NULL = "Null";

    /** The view help dashboard. */
    private static final String VIEW_NAME_HELP_DASHBOARD = "help/dashboard";

    /** The mock mvc. */
    private MockMvc mockMvc;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {

        // Create a new version of the class under test
        HelpController classUnderTest = new HelpController();

        // Stop circular view path error
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view");
        viewResolver.setSuffix(".jsp");

        // Setup the mock version of the modelMvc
        mockMvc =
            MockMvcBuilders.standaloneSetup(classUnderTest).setViewResolvers(viewResolver).build();
    }

    /**
     * Test logon valid.
     *
     * @throws Exception the exception
     */
    @Test
    void testDashboardHelpValid() throws Exception {
        // Perform the test
        final MvcResult results = mockMvc.perform(get("/help/dashboard")).andReturn();

        // Assert that the objects are as expected
        assertViewName(results, VIEW_NAME_HELP_DASHBOARD);
    }

    // TODO Fix this NoSuchRequestHandlingMethodException below
    // /**
    // * Test help invalid.
    // *
    // * @throws Exception the exception
    // */
    // @Test
    // void testHelpInvalid() throws Exception {
    // // Perform the test
    // final MvcResult results = mockMvc.perform(get("/help/none")).andReturn();
    //
    // // Assert that the objects are as expected
    // // TODO Fix this NoSuchRequestHandlingMethodException below
    // // assertEquals(NoSuchRequestHandlingMethodException.class,
    // // results.getResolvedException().getClass());
    //
    // }

    /**
     * Assert view name.
     *
     * @param results the results
     * @param viewName the view name
     */
    private void assertViewName(final MvcResult results, final String viewName) {
        assertNotNull(results, NULL);
        final String actualViewName = results.getModelAndView().getViewName();
        if (actualViewName.startsWith("forward:")) {
            assertTrue(actualViewName.contains(viewName), FALSE);
        } else {
            assertEquals(actualViewName, viewName, NOT_EQUAL);
        }
    }

}
