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
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcdu.XhbDispMgrCduRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcourtsite.XhbDispMgrCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrlocalproxy.XhbDispMgrLocalProxyRepository;
import uk.gov.hmcts.pdm.publicdisplay.common.json.CduStatusJson;
import uk.gov.hmcts.pdm.publicdisplay.common.json.CourtSiteStatusJson;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.CduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.CourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.LocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.XhibitCourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IRagStatusService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.easymock.EasyMock.createMock;

/**
 * The Class RagStatusServiceTest.
 *
 * @author boparaij
 */
@ExtendWith(EasyMockExtension.class)
abstract class RagStatusServiceTestBase extends AbstractJUnit {
    /** The Constant XHIBIT_COURT_SITE_ID. */
    protected static final Long XHIBIT_COURT_SITE_ID = 1L;

    /** The Constant MACADDRESS. */
    protected static final String MACADDRESS = "MACADDRESS";

    protected static final String NOT_EQUAL = "Not equal";

    protected static final String NULL = "Null";

    /** The constant for the no character. */
    protected static final Character NO_CHAR = 'N';

    /** The constant for the yes character. */
    protected static final Character YES_CHAR = 'Y';

    /** The constant for the red character. */
    protected static final Character RED_CHAR = 'R';

    /** The constant for the amber character. */
    protected static final Character AMBER_CHAR = 'A';

    /** The constant for the green character. */
    protected static final Character GREEN_CHAR = 'G';

    protected static final String RAG_STATUS_AMBER = "ragStatusOverallAmberPercent";

    protected static final String RAG_STATUS_RED = "ragStatusOverallRedPercent";

    protected static final String LOCAL_PROXY_COM_ENABLED = "localProxyCommunicationEnabled";

    /** The class under test. */
    protected IRagStatusService classUnderTest;

    /** The mock cdu repo. */
    protected XhbDispMgrCduRepository mockCduRepo;

    /** The mock court site repo. */
    protected XhbDispMgrCourtSiteRepository mockDispMgrCourtSiteRepo;

    /** The mock local proxy rest client. */
    protected LocalProxyRestClient mockLocalProxyRestClient;

    /** The mock local proxy repo. */
    protected XhbDispMgrLocalProxyRepository mockLocalProxyRepo;

    /** The mock scheduler. */
    protected Scheduler mockScheduler;

    /** The mock trigger. */
    protected Trigger mockTrigger;

    /** The test date. */
    protected final Date testDate = DateTime.parse("2010-03-22T16:36:54Z").toDate();

    /** The xhibit court site dto. */
    protected final XhibitCourtSiteDto xhibitCourtSiteDto = getTestXhibitCourtSite(1L, "Court 1");

    /** The local proxy. */
    protected final ILocalProxy localProxy = getTestLocalProxy();

    /** The cdus. */
    protected final Set<ICduModel> cdus = getTestCdusSet();

    /** The court site. */
    protected final ICourtSite courtSite = getTestCourtSite(1L, localProxy, cdus);

    /** The court site status json. */
    protected final CourtSiteStatusJson courtSiteStatusJson =
        getTestCourtSiteStatusJson(new ArrayList<>(cdus));

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new RagStatusService();

        // Setup the mock version of the called classes
        mockCduRepo = createMock(XhbDispMgrCduRepository.class);
        mockDispMgrCourtSiteRepo = createMock(XhbDispMgrCourtSiteRepository.class);
        mockLocalProxyRepo = createMock(XhbDispMgrLocalProxyRepository.class);
        mockLocalProxyRestClient = createMock(LocalProxyRestClient.class);
        mockScheduler = createMock(Scheduler.class);
        mockTrigger = createMock(Trigger.class);

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrCduRepository", mockCduRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrCourtSiteRepository", mockDispMgrCourtSiteRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrLocalProxyRepository",
            mockLocalProxyRepo);
        ReflectionTestUtils.setField(classUnderTest, "localProxyRestClient",
            mockLocalProxyRestClient);
    }

    /**
     * Gets the test xhibit court site.
     *
     * @param id the id
     * @param name the name
     * @return the test xhibit court site
     */
    private XhibitCourtSiteDto getTestXhibitCourtSite(final Long id, final String name) {
        final XhibitCourtSiteDto xhibitCourtSite = new XhibitCourtSiteDto();
        xhibitCourtSite.setId(id);
        xhibitCourtSite.setCourtSiteName(name);
        return xhibitCourtSite;
    }

    /**
     * Gets the test court site.
     *
     * @param id the id
     * @param localProxy the local proxy
     * @param cdus the cdus
     * @return the test court site
     */
    private CourtSite getTestCourtSite(final Long id, final ILocalProxy localProxy,
        final Set<ICduModel> cdus) {
        final XhibitCourtSite testXhibitCourtSite = new XhibitCourtSite();
        testXhibitCourtSite.setId(id);
        testXhibitCourtSite.setCourtSiteName("Test Court Site");

        final CourtSite testCourtSite = new CourtSite();
        testCourtSite.setId(id);
        testCourtSite.setXhibitCourtSite(testXhibitCourtSite);
        testCourtSite.setLocalProxy(localProxy);
        testCourtSite.setCdus(cdus);

        localProxy.setCourtSite(testCourtSite);

        return testCourtSite;
    }

    /**
     * Gets the test cdu.
     *
     * @param id the id
     * @param offlineIndicator the offline indicator
     * @param ragStatus the rag status
     * @return the test cdu
     */
    private ICduModel getTestCdu(final Long id, final Character offlineIndicator,
        final Character ragStatus) {
        final ICduModel cdu = new CduModel();
        cdu.setId(id);
        cdu.setMacAddress(MACADDRESS + id);
        cdu.setRagStatus(ragStatus);
        cdu.setRagStatusDate(LocalDateTime.now());
        cdu.setOfflineIndicator(offlineIndicator);
        return cdu;
    }

    /**
     * Gets the test cdus set.
     *
     * @return the test cdus
     */
    private Set<ICduModel> getTestCdusSet() {
        final Set<ICduModel> cdus = new HashSet<>();
        cdus.add(getTestCdu(1L, NO_CHAR, GREEN_CHAR));
        cdus.add(getTestCdu(2L, NO_CHAR, RED_CHAR));
        cdus.add(getTestCdu(3L, NO_CHAR, AMBER_CHAR));
        cdus.add(getTestCdu(4L, NO_CHAR, GREEN_CHAR));
        cdus.add(getTestCdu(5L, YES_CHAR, RED_CHAR));
        return cdus;
    }

    /**
     * Gets the test cdu status jsons.
     *
     * @param cdus the cdus
     * @return the test cdu jsons
     */
    private List<CduStatusJson> getTestCduStatusJsons(final List<ICduModel> cdus) {
        final List<CduStatusJson> cduJsons = new ArrayList<>();
        for (ICduModel cdu : cdus) {
            final CduStatusJson cduJson = createCduStatusJson();
            cduJson.setMacAddress(cdu.getMacAddress());
            cduJson.setRagStatus(cdu.getRagStatus());
            cduJsons.add(cduJson);
        }
        return cduJsons;
    }

    private CduStatusJson createCduStatusJson() {
        return new CduStatusJson();
    }

    /**
     * Gets the test court site status json.
     *
     * @param cdus the cdus
     * @return the test court site status json
     */
    private CourtSiteStatusJson getTestCourtSiteStatusJson(final List<ICduModel> cdus) {
        final CourtSiteStatusJson courtSiteJson = new CourtSiteStatusJson();
        courtSiteJson.setCdus(getTestCduStatusJsons(cdus));
        courtSiteJson.setRagStatus(GREEN_CHAR);
        return courtSiteJson;
    }

    /**
     * Gets the test local proxy.
     *
     * @return the test local proxy
     */
    private ILocalProxy getTestLocalProxy() {
        final ILocalProxy testLocalProxy = new LocalProxy();
        testLocalProxy.setId(1L);
        return testLocalProxy;
    }
}
