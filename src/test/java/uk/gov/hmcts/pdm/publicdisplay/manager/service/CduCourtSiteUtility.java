package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import jakarta.persistence.EntityManager;
import uk.gov.hmcts.pdm.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcdu.XhbDispMgrCduRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcourtsite.XhbDispMgrCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrmapping.XhbDispMgrMappingRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrurl.XhbDispMgrUrlRepository;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.CourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.XhibitCourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICduService;

import java.util.ArrayList;
import java.util.List;

abstract class CduCourtSiteUtility extends AbstractJUnit {
    /** The Constant CDU_IDS. */
    protected static final Long[] CDU_IDS = {1L, 2L, 3L};

    /** The Constant COURTSITE_IDS. */
    protected static final Long[] COURTSITE_IDS = {1L, 2L};

    /** The Constant LOCALPROXY_IDS. */
    protected static final Long[] LOCALPROXY_IDS = {1L, 2L};

    /** The Constant URL_IDS. */
    protected static final Long[] URL_IDS = {5L, 6L, 7L, 8L};

    /** The Constant CDU_COURTSITE_ARRAY_MAPPING. */
    protected static final Integer[] CDU_COURTSITE_ARRAY_MAPPING = {0, 0, 1};

    /** The Constant XHIBITCOURT_COURTSITE_ARRAY_MAPPING. */
    protected static final Integer[] XHIBITCOURT_COURTSITE_ARRAY_MAPPING = {0, 1};

    /** The Constant LOCALPROXY_COURTSITE_ARRAY_MAPPING. */
    protected static final Integer[] LOCALPROXY_COURTSITE_ARRAY_MAPPING = {0, 1};

    /** The Constant CDU_URL_ARRAY_MAPPING (-1 used for addMappingTest). */
    protected static final Integer[] CDU_URL_ARRAY_MAPPING = {0, 1, -1, 2};

    /** The constant CDUNUMBER. */
    protected static final String CDUNUMBER = "CDUNUMBER";

    /** The constant IPADDRESS. */
    protected static final String IPADDRESS = "IPADDRESS";

    /** The constant LOCATION. */
    protected static final String LOCATION = "LOCATION";

    /** The Constant MACADDRESS. */
    protected static final String MACADDRESS = "MACADDRESS";

    /** The Constant CDU_IP_HOST_MIN. */
    protected static final Integer CDU_IP_HOST_MIN = 1;

    /** The Constant CDU_IP_HOST_MAX. */
    protected static final Integer CDU_IP_HOST_MAX = 2;

    protected static final String NOT_EQUAL = "Not equal";

    protected static final String FALSE = "False";

    protected static final String NULL = "Null";

    protected static final String LOCAL_PROXY_COMM_ENABLED = "localProxyCommunicationEnabled";

    /** The class under test. */
    protected ICduService classUnderTest;

    /** The mock cdu repo. */
    protected XhbDispMgrCduRepository mockCduRepo;

    /** The mock url repo. */
    protected XhbDispMgrUrlRepository mockUrlRepo;

    /** The mock court site repo. */
    protected XhbCourtSiteRepository mockCourtSiteRepo;

    /** The mock disp mgr court site repo. */
    protected XhbDispMgrCourtSiteRepository mockDispMgrCourtSiteRepo;

    /** The mock disp mgr mapping repo. */
    protected XhbDispMgrMappingRepository mockDispMgrMappingRepo;

    /** The mock local proxy rest client. */
    protected LocalProxyRestClient mockLocalProxyRestClient;
    
    protected EntityManager mockEntityManager;

    /** The xhibit court sites. */
    protected final List<IXhibitCourtSite> xhibitCourtSites = getTestXhibitCourtSites();

    /**
     * Gets the test xhibit court site.
     *
     * @param id the id
     * @return the test xhibit court site
     */
    private IXhibitCourtSite getTestXhibitCourtSite(final Long id) {
        final IXhibitCourtSite xhibitCourtSite = new XhibitCourtSite();
        xhibitCourtSite.setId(id);
        xhibitCourtSite.setCourtSiteName("COURTSITENAME" + id.toString());
        return xhibitCourtSite;
    }

    /**
     * Gets the test xhibit court sites.
     *
     * @return the test xhibit court sites
     */
    private List<IXhibitCourtSite> getTestXhibitCourtSites() {
        final List<IXhibitCourtSite> sites = new ArrayList<>();
        for (Long id : COURTSITE_IDS) {
            sites.add(getTestXhibitCourtSite(id));
        }
        return sites;
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
        return courtSite;
    }

}
