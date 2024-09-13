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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.cdus;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtSiteDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.CduService;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.LocalProxyService;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.UrlService;

import java.util.Map;
import java.util.Random;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;



/**
 * The Class CdusControllerTestBase.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
@SuppressWarnings("PMD.LawOfDemeter")
abstract class CdusControllerTestBase extends AbstractJUnit {

    /** The mock cdu search validator. */
    protected CduSearchValidator mockCduSearchValidator;

    /** The mock cdu search selected validator. */
    protected CduSearchSelectedValidator mockCduSearchSelectedValidator;

    /** The mock cdu restart validator. */
    protected CduRestartAllValidator mockCduRestartAllValidator;

    /** The mock register cdu validator. */
    protected CduRegisterValidator mockCduRegisterValidator;

    /** The mock amend cdu validator. */
    protected CduAmendValidator mockCduAmendValidator;

    /** The mock unregister cdu validator. */
    protected CduUnregisterValidator mockCduUnregisterValidator;

    /** The mock cdu page state holder. */
    protected CduPageStateHolder mockCduPageStateHolder;

    /** The mock local proxy service. */
    protected LocalProxyService mockLocalProxyService;

    /** The mock cdu service. */
    protected CduService mockCduService;

    /** The mock url service. */
    protected UrlService mockUrlService;

    /** The mock mapping add validator. */
    protected MappingAddValidator mockMappingAddValidator;

    /** The mock mapping remove validator. */
    protected MappingRemoveValidator mockMappingRemoveValidator;

    protected static final String NOT_EQUAL = "Not equal";

    protected static final String FALSE = "False";

    protected static final String NULL = "Null";

    protected void mockObjects() {
        mockCduSearchValidator = createMock(CduSearchValidator.class);
        mockCduSearchSelectedValidator = createMock(CduSearchSelectedValidator.class);
        mockCduRestartAllValidator = createMock(CduRestartAllValidator.class);
        mockCduRegisterValidator = createMock(CduRegisterValidator.class);
        mockCduAmendValidator = createMock(CduAmendValidator.class);
        mockCduUnregisterValidator = createMock(CduUnregisterValidator.class);
        mockMappingAddValidator = createMock(MappingAddValidator.class);
        mockMappingRemoveValidator = createMock(MappingRemoveValidator.class);
        mockCduPageStateHolder = createMock(CduPageStateHolder.class);
        mockLocalProxyService = createMock(LocalProxyService.class);
        mockCduService = createMock(CduService.class);
        mockUrlService = createMock(UrlService.class);
    }

    /**
     * Gets the test site.
     *
     * @param courtSiteName the court site name
     * @return the test site
     */
    protected XhibitCourtSiteDto getTestSite(final String courtSiteName) {
        final XhibitCourtSiteDto site = new XhibitCourtSiteDto();
        site.setCourtSiteName(courtSiteName);

        return site;
    }

    /**
     * Assert view name.
     *
     * @param results the results
     * @param viewName the view name
     */
    protected void assertViewName(final MvcResult results, final String viewName) {
        assertNotNull(results, NULL);
        final String actualViewName = results.getModelAndView().getViewName();
        if (actualViewName.startsWith("forward:") || actualViewName.startsWith("redirect:")) {
            assertTrue(actualViewName.contains(viewName), FALSE);
        } else {
            assertEquals(viewName, actualViewName, NOT_EQUAL);
        }
    }

    /**
     * Assert successful.
     *
     * @param modelMap the model map
     */
    protected void assertSuccessfulMessage(final Map<String, Object> modelMap) {
        assertNotNull(modelMap.get("successMessage"), NULL);
    }

    /**
     * Gets the test byte array.
     *
     * @return the test byte array
     */
    protected byte[] getTestByteArray() {
        final byte[] bytes = new byte[100];
        new Random().nextBytes(bytes);
        return bytes;
    }

    /**
     * Gets the court site dto.
     *
     * @param courtSiteId the court site id
     * @return the court site dto
     */
    protected CourtSiteDto getTestCourtSiteDto(final Long courtSiteId) {
        final CourtSiteDto courtSiteDto = new CourtSiteDto();
        courtSiteDto.setId(courtSiteId);
        courtSiteDto.setIpAddress("TEST_IP");
        courtSiteDto.setNotification("TEST_DEFAULT_NOTIFICATION");
        courtSiteDto.setPageUrl("TEST_PAGE_URL");
        courtSiteDto.setScheduleId(1L);
        courtSiteDto.setScheduleTitle("TEST_SCHEDULE_TITLE");
        courtSiteDto.setTitle("TEST_TITLE");
        return courtSiteDto;
    }

}
