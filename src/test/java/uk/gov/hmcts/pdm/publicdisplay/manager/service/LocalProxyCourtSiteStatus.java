package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdm.publicdisplay.common.json.CduJson;
import uk.gov.hmcts.pdm.publicdisplay.common.json.CourtSiteStatusJson;
import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.CourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.Schedule;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.UrlModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.XhibitCourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.XhibitCourtSiteWelsh;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ISchedule;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrlModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSiteWelsh;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyRestClient;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IServiceAuditService;

import java.util.Arrays;
import java.util.List;

/**
 * The Class LocalProxyCourtSiteStatusTest.
 */
@ExtendWith(EasyMockExtension.class)
abstract class LocalProxyCourtSiteStatus extends LocalProxyJsonRequest {

    protected static final String NOT_EQUAL = "Not equal";

    protected static final String FALSE = "False";

    /** The class under test. */
    protected ILocalProxyRestClient classUnderTest;

    /** The mock service audit service. */
    protected IServiceAuditService mockServiceAuditService;

    /** The test cdu jsons. */
    protected final CduJson[] testCduJsons = getTestCduJsons();

    /** The test court site status json. */
    protected final CourtSiteStatusJson testCourtSiteStatusJsons =
        getTestCourtSiteStatusJson(Arrays.asList(testCduJsons));

    /** The test local proxy with court site. */
    protected final ILocalProxy testLocalProxyWithCourtSite = getTestLocalProxyWithCourtSite();

    /** The test court site. */
    protected final ICourtSite testCourtSiteWithLocalProxy = getTestCourtSiteWithLocalProxy();

    /** The test cdu. */
    protected final ICduModel testCdu = getTestCdu(testCourtSiteWithLocalProxy);

    /** The test url. */
    protected final IUrlModel testUrl = getTestUrl(testCourtSiteWithLocalProxy);

    /**
     * Gets the test court site status json.
     *
     * @param cduJsons the cdu jsons
     * @return the test court site status json
     */
    protected CourtSiteStatusJson getTestCourtSiteStatusJson(final List<CduJson> cduJsons) {
        final CourtSiteStatusJson courtSiteJson = new CourtSiteStatusJson();
        courtSiteJson.setCdus(getTestCduStatusJsons(cduJsons));
        courtSiteJson.setRagStatus(AppConstants.GREEN_CHAR);
        return courtSiteJson;
    }

    /**
     * Gets the test court site with local proxy.
     *
     * @return the test court site with local proxy
     */
    protected ICourtSite getTestCourtSiteWithLocalProxy() {
        final ILocalProxy testLocalProxy = getTestLocalProxy();
        final ICourtSite testCourtSite = getTestCourtSite();
        testLocalProxy.setCourtSite(testCourtSite);
        testCourtSite.setLocalProxy(testLocalProxy);
        return testCourtSite;
    }

    /**
     * Gets the test local proxy with court site.
     *
     * @return the test local proxy with court site
     */
    protected ILocalProxy getTestLocalProxyWithCourtSite() {
        final ILocalProxy testLocalProxy = getTestLocalProxy();
        final ICourtSite testCourtSite = getTestCourtSite();
        testLocalProxy.setCourtSite(testCourtSite);
        testCourtSite.setLocalProxy(testLocalProxy);
        return testLocalProxy;
    }

    /**
     * Gets the test court site.
     *
     * @return the test court site
     */
    protected ICourtSite getTestCourtSite() {
        final IXhibitCourtSite testXhibitCourtSite = getTestXhibitCourtSite();
        final ICourtSite testCourtSite = new CourtSite();
        testXhibitCourtSite.setCourtSite(testCourtSite);
        testCourtSite.setId(1L);
        testCourtSite.setTitle("TEST_COURTSITE");
        testCourtSite.setPageUrl("TEST_PAGEURL");
        testCourtSite.setSchedule(getTestSchedule());
        testCourtSite.setNotification("TEST_NOTIFICATION");
        testCourtSite.setXhibitCourtSite(testXhibitCourtSite);
        return testCourtSite;
    }

    /**
     * Gets the test schedule.
     *
     * @return the test schedule
     */
    protected ISchedule getTestSchedule() {
        final ISchedule testSchedule = new Schedule();
        testSchedule.setId(1L);
        testSchedule.setDetail("TEST_SCHEDULE");
        return testSchedule;
    }

    /**
     * Gets the test xhibit court site.
     *
     * @return the test xhibit court site
     */
    protected IXhibitCourtSite getTestXhibitCourtSite() {
        final IXhibitCourtSite testCourtSite = new XhibitCourtSite();
        testCourtSite.setCourtSiteName("TEST_XHIBITCOURTSITE");
        final IXhibitCourtSiteWelsh xhibitCourtSiteWelsh = new XhibitCourtSiteWelsh();
        testCourtSite.setXhibitCourtSiteWelsh(xhibitCourtSiteWelsh);
        return testCourtSite;
    }

    /**
     * Gets the test url.
     *
     * @param courtSite the court site
     * @return the test url
     */
    private IUrlModel getTestUrl(final ICourtSite courtSite) {
        final IUrlModel testUrl = new UrlModel();
        testUrl.setId(1L);
        testUrl.setUrl("TEST_URL");
        testUrl.setDescription("TEST_DESCRIPTION");
        testUrl.setXhibitCourtSite(courtSite.getXhibitCourtSite());
        return testUrl;
    }
}
