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

package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.pdm.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcdu.XhbDispMgrCduRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcourtsite.XhbDispMgrCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrlocalproxy.XhbDispMgrLocalProxyRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrschedule.XhbDispMgrScheduleRepository;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.CduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.CourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.LocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.Schedule;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.UrlModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.XhibitCourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ISchedule;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrlModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyRestClient;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.easymock.EasyMock.createMock;

/**
 * The Class LocalProxyServiceTest.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
abstract class LocalProxyServiceTestBase extends AbstractJUnit {
    /** The Constant IPADDRESS. */
    protected static final String IPADDRESS = "IPADDRESS";

    /** The Constant RAG_STATUS_DATE. */
    protected static final LocalDateTime RAG_STATUS_DATE = LocalDateTime.now();

    protected static final String NOT_EQUAL = "Not equal";

    protected static final String NULL = "Null";

    protected static final String TEST_HOSTNAME = "TESTHOSTNAME";

    protected static final String LOCAL_PROXY_COMM_ENABLED = "localProxyCommunicationEnabled";

    /** The constant for the red character. */
    public static final Character RED_CHAR = 'R';

    /** The constant for the amber character. */
    public static final Character AMBER_CHAR = 'A';

    /** The class under test. */
    protected ILocalProxyService classUnderTest;

    /** The mock court site repo. */
    protected XhbCourtSiteRepository mockCourtSiteRepo;

    /** The mock disp mgr court site repo. */
    protected XhbDispMgrCourtSiteRepository mockDispMgrCourtSiteRepo;

    /** The mock local proxy repo. */
    protected XhbDispMgrLocalProxyRepository mockLocalProxyRepo;

    /** The mock schedule repo. */
    protected XhbDispMgrScheduleRepository mockScheduleRepo;

    /** The mock cdu repo. */
    protected XhbDispMgrCduRepository mockCduRepo;

    /** The mock local proxy rest client. */
    protected ILocalProxyRestClient mockLocalProxyRestClient;

    /** The xhibit court sites with local proxies. */
    protected final List<IXhibitCourtSite> courtSitesWithLocalProxies =
        getTestXhibitCourtSites(true);

    /** The xhibit court sites without local proxies. */
    protected final List<IXhibitCourtSite> courtSitesWithoutLocalProxies =
        getTestXhibitCourtSites(false);

    /** The xhibit court sites without local proxies. */
    protected final List<ISchedule> powerSaveSchedules = getPowersaveSchedules();

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new LocalProxyService();

        // Setup the mock version of the called classes
        mockCduRepo = createMock(XhbDispMgrCduRepository.class);
        mockCourtSiteRepo = createMock(XhbCourtSiteRepository.class);
        mockDispMgrCourtSiteRepo = createMock(XhbDispMgrCourtSiteRepository.class);
        mockLocalProxyRepo = createMock(XhbDispMgrLocalProxyRepository.class);
        mockScheduleRepo = createMock(XhbDispMgrScheduleRepository.class);
        mockLocalProxyRestClient = createMock(LocalProxyRestClient.class);

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrCduRepository", mockCduRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbCourtSiteRepository", mockCourtSiteRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrCourtSiteRepository",
            mockDispMgrCourtSiteRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrLocalProxyRepository",
            mockLocalProxyRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrScheduleRepository",
            mockScheduleRepo);
        ReflectionTestUtils.setField(classUnderTest, "localProxyRestClient",
            mockLocalProxyRestClient);
    }

    /**
     * Gets the test local proxy.
     *
     * @param id the id
     * @param courtSite the court site
     * @return the test local proxy
     */
    private ILocalProxy getTestLocalProxy(final Long id, final ICourtSite courtSite) {
        final ILocalProxy localProxy = new LocalProxy();
        localProxy.setId(id);
        localProxy.setIpAddress(IPADDRESS);
        localProxy.setRagStatus(AMBER_CHAR.toString());
        localProxy.setRagStatusDate(RAG_STATUS_DATE);
        localProxy.setCourtSite(courtSite);

        return localProxy;
    }

    /**
     * Gets the test court site.
     *
     * @param id the id
     * @return the test court site
     */
    protected ICourtSite getTestCourtSite(final Long id) {
        final ICourtSite courtSite = new CourtSite();
        courtSite.setId(id);

        courtSite.setRagStatus(RED_CHAR.toString());
        courtSite.setRagStatusDate(RAG_STATUS_DATE);
        courtSite.setXhibitCourtSite(getTestXhibitCourtSite(1L, false));
        courtSite.setSchedule(getTestSchedule(1L));
        courtSite.setNotification("TEST_NOTIFICATION");
        courtSite.setLocalProxy(getTestLocalProxy(id, courtSite));
        courtSite.setCdus(getTestCdusSet());

        return courtSite;
    }

    /**
     * Gets the test cdu.
     *
     * @param id the id
     * @param ragStatus the rag status
     * @return the test cdu
     */
    private ICduModel getTestCdu(final Long id, final Character ragStatus) {
        final ICduModel cdu = new CduModel();
        cdu.setId(id);
        cdu.setMacAddress("MACADDRESS" + id);
        cdu.setLocation("Location" + id);
        cdu.setRagStatus(ragStatus);
        cdu.setRagStatusDate(LocalDateTime.now());
        cdu.setOfflineIndicator(AppConstants.NO_CHAR);
        final List<IUrlModel> urls = new ArrayList<>();
        final IUrlModel url = new UrlModel();
        url.setId(id);
        urls.add(url);
        cdu.setUrls(urls);
        return cdu;
    }

    /**
     * Gets the test cdus set.
     *
     * @return the test cdus
     */
    private Set<ICduModel> getTestCdusSet() {
        final Set<ICduModel> cdus = new HashSet<>();
        cdus.add(getTestCdu(1L, AppConstants.GREEN_CHAR));
        cdus.add(getTestCdu(2L, AppConstants.RED_CHAR));
        return cdus;
    }

    /**
     * Gets the test xhibit court site.
     *
     * @param id the id
     * @param includeCourtSite the include court site
     * @return the test xhibit court site
     */
    private IXhibitCourtSite getTestXhibitCourtSite(final Long id, final boolean includeCourtSite) {
        final IXhibitCourtSite xhibitCourtSite = new XhibitCourtSite();
        xhibitCourtSite.setId(id);
        if (includeCourtSite) {
            xhibitCourtSite.setCourtSite(getTestCourtSite(id));
        }
        return xhibitCourtSite;
    }

    /**
     * Gets the test xhibit court sites.
     *
     * @param includeCourtSite the include court site
     * @return the test xhibit court sites
     */
    private List<IXhibitCourtSite> getTestXhibitCourtSites(final boolean includeCourtSite) {
        final List<IXhibitCourtSite> sites = new ArrayList<>();
        sites.add(getTestXhibitCourtSite(1L, includeCourtSite));
        sites.add(getTestXhibitCourtSite(2L, includeCourtSite));
        return sites;
    }

    /**
     * Gets the test schedule.
     *
     * @param id the id
     * @return the test schedule
     */
    protected ISchedule getTestSchedule(final Long id) {
        final ISchedule schedule = new Schedule();
        schedule.setId(id);
        schedule.setTitle("TITLE");
        schedule.setType("TYPE");
        schedule.setDetail("DETAIL");
        return schedule;
    }

    /**
     * Gets the powersave schedules.
     *
     * @return the powersave schedules
     */
    private List<ISchedule> getPowersaveSchedules() {
        final List<ISchedule> schedules = new ArrayList<>();
        for (long l = 1L; l < 3; l++) {
            final ISchedule schedule = getTestSchedule(l);
            schedules.add(schedule);
        }
        return schedules;
    }

}
