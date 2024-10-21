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

import jakarta.persistence.EntityManager;
import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.pdm.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcdu.XhbDispMgrCduRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrcourtsite.XhbDispMgrCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrmapping.XhbDispMgrMappingRepository;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgrurl.XhbDispMgrUrlRepository;
import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.CduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.LocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.UrlModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrlModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CduDto;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.createMock;

/**
 * The Class CduServiceTestBase.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
abstract class CduServiceTestBase extends CduCourtSiteUtility {

    /** The localproxies. */
    protected final List<ILocalProxy> localProxies = getTestLocalProxies();

    /** The court sites. */
    protected final List<ICourtSite> courtSites = getTestCourtSites();

    /** The url. */
    protected final List<IUrlModel> urls = getTestUrls();

    /** The cdus. */
    protected final List<ICduModel> cdus = getTestCdus();

    /** The cdu dtos. */
    protected final List<CduDto> cduDtos = getTestCduDtos();

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test (CduService)
        classUnderTest = new CduService();

        // Setup the mock version of the called classes
        mockCduRepo = createMock(XhbDispMgrCduRepository.class);
        mockUrlRepo = createMock(XhbDispMgrUrlRepository.class);
        mockCourtSiteRepo = createMock(XhbCourtSiteRepository.class);
        mockDispMgrCourtSiteRepo = createMock(XhbDispMgrCourtSiteRepository.class);
        mockDispMgrMappingRepo = createMock(XhbDispMgrMappingRepository.class);
        mockLocalProxyRestClient = createMock(LocalProxyRestClient.class);
        mockEntityManager = createMock(EntityManager.class);

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrCduRepository", mockCduRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrUrlRepository", mockUrlRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbCourtSiteRepository", mockCourtSiteRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrCourtSiteRepository",
            mockDispMgrCourtSiteRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbDispMgrMappingRepository",
            mockDispMgrMappingRepo);
        ReflectionTestUtils.setField(classUnderTest, "localProxyRestClient",
            mockLocalProxyRestClient);
        ReflectionTestUtils.setField(classUnderTest, "cduIpHostMin", CDU_IP_HOST_MIN);
        ReflectionTestUtils.setField(classUnderTest, "cduIpHostMax", CDU_IP_HOST_MAX);
    }

    /**
     * Gets the test local proxy.
     *
     * @param id the id
     * @return the test local proxy
     */
    private ILocalProxy getTestLocalProxy(final Long id) {
        final ILocalProxy localProxy = new LocalProxy();
        localProxy.setId(id);
        return localProxy;
    }

    /**
     * Gets the test local proxies.
     *
     * @return the test local proxies
     */
    private List<ILocalProxy> getTestLocalProxies() {
        final List<ILocalProxy> localProxies = new ArrayList<>();
        for (Long id : LOCALPROXY_IDS) {
            localProxies.add(getTestLocalProxy(id));
        }
        return localProxies;
    }

    /**
     * Gets the test court sites.
     *
     * @return the test court sites
     */
    private List<ICourtSite> getTestCourtSites() {
        final List<ICourtSite> courtSites = new ArrayList<>();
        for (Long id : COURTSITE_IDS) {
            final IXhibitCourtSite xhibitCourtSite =
                xhibitCourtSites.get(XHIBITCOURT_COURTSITE_ARRAY_MAPPING[id.intValue() - 1]);
            final ILocalProxy localProxy =
                localProxies.get(LOCALPROXY_COURTSITE_ARRAY_MAPPING[id.intValue() - 1]);
            final ICourtSite courtSite = getTestCourtSite(id);
            courtSite.setXhibitCourtSite(xhibitCourtSite);
            courtSite.setLocalProxy(localProxy);
            courtSites.add(courtSite);
        }
        return courtSites;
    }

    /**
     * Gets the test cdu.
     *
     * @param id the id
     * @param courtSite the court site
     * @return the test cdu
     */
    private ICduModel getTestCdu(final Long id, final ICourtSite courtSite) {
        final ICduModel cdu = new CduModel();
        cdu.setId(id);
        cdu.setCduNumber(CDUNUMBER + id.toString());
        cdu.setMacAddress(MACADDRESS + id.toString());
        cdu.setLocation(LOCATION);
        cdu.setIpAddress(IPADDRESS + id.toString());
        cdu.setCourtSite(courtSite);
        return cdu;
    }

    /**
     * Gets the test cdus.
     *
     * @return the test cdus
     */
    private List<ICduModel> getTestCdus() {
        final List<ICduModel> cdus = new ArrayList<>();
        for (Long id : CDU_IDS) {
            final int cduArrayPosition = id.intValue() - 1;
            final ICourtSite courtSite =
                courtSites.get(CDU_COURTSITE_ARRAY_MAPPING[cduArrayPosition]);
            final ICduModel cdu = getTestCdu(id, courtSite);
            for (int urlPos = 0; urlPos < urls.size(); urlPos++) {
                if (CDU_URL_ARRAY_MAPPING[urlPos].equals(cduArrayPosition)) {
                    cdu.getUrls().add(urls.get(urlPos));
                }
            }
            cdus.add(cdu);
            courtSite.getCdus().add(cdu);
        }
        return cdus;
    }

    /**
     * Gets the test url.
     *
     * @param id the id
     * @return the test url
     */
    private IUrlModel getTestUrl(final Long id) {
        final IUrlModel url = new UrlModel();
        url.setId(id);
        url.setUrl("url" + id.toString());
        return url;
    }

    /**
     * Gets the test urls.
     *
     * @return the test urls
     */
    private List<IUrlModel> getTestUrls() {
        final List<IUrlModel> urls = new ArrayList<>();
        for (Long id : URL_IDS) {
            urls.add(getTestUrl(id));
        }
        return urls;
    }

    /**
     * Gets the test cdu dtos.
     *
     * @return the test cdu dtos
     */
    protected List<CduDto> getTestCduDtos() {
        final List<CduDto> cduDtos = new ArrayList<>();
        for (ICduModel cdu : cdus) {
            final CduDto cduDto = createCduDto();
            cduDto.setId(cdu.getId());
            cduDto.setMacAddress(MACADDRESS + cdu.getId());
            cduDto.setIpAddress(cdu.getIpAddress());
            cduDto.setCourtSiteId(cdu.getCourtSite().getId());
            cduDto.setOfflineIndicator(AppConstants.NO_CHAR);
            cduDtos.add(cduDto);
        }

        return cduDtos;
    }

    private CduDto createCduDto() {
        return new CduDto();
    }

}
