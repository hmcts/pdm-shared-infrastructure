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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.proxies;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtSiteDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.ScheduleDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The Class LocalProxyController.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
@SuppressWarnings("PMD.LawOfDemeter")
abstract class LocalProxyControllerTestBase extends AbstractJUnit {
    /** The Constant COURT_SITE_NAME. */
    protected static final String COURT_SITE_NAME = "COURTSITE";

    protected static final String NOT_EQUAL = "Not equal";

    protected static final String FALSE = "False";

    protected static final String NULL = "Null";

    protected static final String TITLE = "title";

    protected static final String NOTIFICATION = "notification";

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
            assertEquals(actualViewName, viewName, NOT_EQUAL);
        }
    }

    /**
     * Gets the test court site.
     *
     * @param courtSiteId the court site id
     * @return the test court site
     */
    protected CourtSiteDto getTestCourtSite(final Long courtSiteId) {
        final CourtSiteDto courtSite = new CourtSiteDto();
        courtSite.setId(courtSiteId);
        courtSite.setPageUrl("PageUrl");
        courtSite.setTitle(TITLE);
        courtSite.setIpAddress("IpAddress");
        courtSite.setScheduleId(1L);
        courtSite.setScheduleTitle("ScheduledTitle");
        courtSite.setNotification(NOTIFICATION);
        return courtSite;
    }

    /**
     * Gets the test xhibit court site.
     *
     * @param courtSiteName the court site name
     * @return the test site
     */
    protected XhibitCourtSiteDto getTestXhibitCourtSite(final String courtSiteName) {
        final XhibitCourtSiteDto xhibitCourtSite = new XhibitCourtSiteDto();
        xhibitCourtSite.setCourtSiteName(courtSiteName);
        return xhibitCourtSite;
    }

    /**
     * Gets the test xhibit court sites.
     *
     * @return the test sites
     */
    protected List<XhibitCourtSiteDto> getTestXhibitCourtSites() {
        final List<XhibitCourtSiteDto> sites = new ArrayList<>();
        sites.add(getTestXhibitCourtSite(COURT_SITE_NAME + "1"));
        sites.add(getTestXhibitCourtSite(COURT_SITE_NAME + "2"));
        return sites;
    }

    /**
     * Gets the test schedule.
     *
     * @param id the id
     * @return the test schedule
     */
    protected ScheduleDto getTestSchedule(final Long id) {
        final ScheduleDto schedule = new ScheduleDto();
        schedule.setId(id);
        schedule.setTitle(TITLE + id.toString());
        schedule.setType("TYPE" + id.toString());
        return schedule;
    }

    /**
     * Gets the test schedules.
     *
     * @return the test schedules
     */
    protected List<ScheduleDto> getTestSchedules() {
        final List<ScheduleDto> schedules = new ArrayList<>();
        schedules.add(getTestSchedule(1L));
        schedules.add(getTestSchedule(2L));
        return schedules;
    }

    /**
     * Assert successful.
     *
     * @param modelMap the model map
     */
    protected void assertSuccessfulMessage(final Map<String, Object> modelMap) {
        assertNotNull(modelMap.get("successMessage"), NULL);
    }
}
