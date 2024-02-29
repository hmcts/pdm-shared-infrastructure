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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.pdm.publicdisplay.common.exception.ServiceException;
import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.CourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.LocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ISchedule;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtSiteDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.DashboardCduDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.DashboardCourtSiteDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.DashboardLocalProxyDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.UserRole;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyRestClient;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyService;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.proxies.LocalProxyAmendCommand;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.proxies.LocalProxyRegisterCommand;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


/**
 * The Class LocalProxyService.
 *
 * @author scullionm
 */
@Component
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class LocalProxyService extends LocalProxyServiceFinder implements ILocalProxyService {

    /**
     * Set up our logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalProxyService.class);
    private static final String METHOD = "Method ";
    private static final String THREE_PARAMS = "{}{}{}";
    private static final String FOUR_PARAMS = "{}{}{}{}";
    private static final String FIVE_PARAMS = "{}{}{}{}{}";
    private static final String STARTS = " - starts";
    private static final String ENDS = " - ends";
    private final Character greenChar = AppConstants.GREEN_CHAR;
    private static final String TEST_HOSTNAME = "TEST_HOSTNAME_";

    /** The page url. */
    @Value("#{applicationConfiguration.xhibitPublicDisplayUrl}")
    private String pageUrl;

    /**
     * Set up the local proxy rest client.
     */
    @Autowired
    private ILocalProxyRestClient localProxyRestClient;

    @Value("${localproxy.communication.enabled}")
    private Boolean localProxyCommunicationEnabled;

    /**
     * Gets the xhibit court sites.
     *
     * @param xhibitCourtSiteList the court site list
     * @return the xhibit court sites
     */
    private List<XhibitCourtSiteDto> getXhibitCourtSites(
        final List<IXhibitCourtSite> xhibitCourtSiteList) {
        final String methodName = "getXhibitCourtSites";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        final List<XhibitCourtSiteDto> resultList = new ArrayList<>();
        LOGGER.debug(FOUR_PARAMS, METHOD, methodName, " - Court sites returned : ",
            xhibitCourtSiteList.size());

        if (!xhibitCourtSiteList.isEmpty()) {
            // Transfer each court site to a dto and save in resultList
            for (IXhibitCourtSite xhibitCourtSite : xhibitCourtSiteList) {
                LOGGER.debug(THREE_PARAMS, METHOD, methodName, " - transferring court site to dto");
                final XhibitCourtSiteDto dto = createXhibitCourtSiteDto();

                // need the court site details from the main court site in 'xhb_court_site' table
                dto.setId(xhibitCourtSite.getId());
                dto.setCourtSiteName(xhibitCourtSite.getCourtSiteName());

                // set properties only available when local proxy is registered
                final ILocalProxy localProxy = getLocalProxy(xhibitCourtSite);
                if (localProxy != null) {
                    dto.setRagStatus(xhibitCourtSite.getCourtSite().getRagStatus());
                    dto.setRegisteredIndicator(AppConstants.YES_CHAR);
                } else {
                    dto.setRegisteredIndicator(AppConstants.NO_CHAR);
                }

                LOGGER.debug("dto id : {}", dto.getId());
                LOGGER.debug("dto courtSiteName: {}", dto.getCourtSiteName());
                LOGGER.debug("dto ragStatus: {}", dto.getRagStatus());
                LOGGER.debug("dto registered: {}", dto.getRegisteredIndicator().toString());

                resultList.add(dto);
            }
        }
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return resultList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyService#
     * getXhibitCourtSitesWithLocalProxy ()
     */
    @Override
    public List<XhibitCourtSiteDto> getXhibitCourtSitesWithLocalProxy() {
        final String methodName = "getXhibitCourtSitesWithLocalProxy";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        final List<IXhibitCourtSite> courtSiteList =
            getXhbCourtSiteRepository().findCourtSitesWithLocalProxy();
        final List<XhibitCourtSiteDto> resultList = getXhibitCourtSites(courtSiteList);
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return resultList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyService#
     * getXhibitCourtSitesWithoutLocalProxy()
     */
    @Override
    public List<XhibitCourtSiteDto> getXhibitCourtSitesWithoutLocalProxy() {
        final String methodName = "getXhibitCourtSitesWithoutLocalProxy";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        final List<IXhibitCourtSite> courtSiteList =
            getXhbCourtSiteRepository().findCourtSitesWithoutLocalProxy();
        final List<XhibitCourtSiteDto> resultList = getXhibitCourtSites(courtSiteList);
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return resultList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyService#
     * getXhibitCourtSitesOrderedByRagStatus()
     */
    @Override
    public List<XhibitCourtSiteDto> getXhibitCourtSitesOrderedByRagStatus() {
        final String methodName = "getXhibitCourtSitesOrderedByRagStatus";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        final List<IXhibitCourtSite> courtSiteList =
            getXhbCourtSiteRepository().findXhibitCourtSitesOrderedByRagStatus();
        final List<XhibitCourtSiteDto> resultList = getXhibitCourtSites(courtSiteList);
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return resultList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyService#
     * getCourtSiteByXhibitCourtSiteId( java.lang.Long)
     */
    @Override
    public CourtSiteDto getCourtSiteByXhibitCourtSiteId(final Long xhibitCourtSiteId) {
        final String methodName = "getCourtSiteByXhibitCourtSiteId";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        final ICourtSite courtSite = getXhbCourtSiteRepository()
            .findCourtSiteByXhibitCourtSiteId(xhibitCourtSiteId.intValue());
        final CourtSiteDto dto = new CourtSiteDto();
        dto.setId(courtSite.getXhibitCourtSite().getId());
        dto.setPageUrl(courtSite.getPageUrl());
        dto.setTitle(courtSite.getTitle());
        dto.setIpAddress(courtSite.getLocalProxy().getIpAddress());
        dto.setScheduleId(courtSite.getSchedule().getId());
        dto.setScheduleTitle(courtSite.getSchedule().getTitle());
        dto.setNotification(courtSite.getNotification());
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return dto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyService#
     * getDashboardCourtSiteByXhibitCourtSiteId( java.lang.Long)
     */
    @Override
    public DashboardCourtSiteDto getDashboardCourtSiteByXhibitCourtSiteId(
        final Long xhibitCourtSiteId) {
        final String methodName = "getDashboardCourtSiteByXhibitCourtSiteId";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        final ICourtSite courtSite = getXhbCourtSiteRepository()
            .findCourtSiteByXhibitCourtSiteId(xhibitCourtSiteId.intValue());
        final DashboardCourtSiteDto courtSiteDto = new DashboardCourtSiteDto();
        final DashboardLocalProxyDto localProxyDto = new DashboardLocalProxyDto();
        localProxyDto.setRagStatus(courtSite.getLocalProxy().getRagStatus());
        courtSiteDto.setLocalProxy(localProxyDto);
        courtSiteDto.setCdus(getDashboardCduDtos(courtSite.getCdus()));
        courtSiteDto.setRagStatus(courtSite.getRagStatus());
        courtSiteDto.setLastRefreshDate(courtSite.getRagStatusDate());
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return courtSiteDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.
     * ILocalProxyService#deleteLocalProxy(java.lang.Long)
     */
    @Override
    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void unregisterLocalProxy(final Long courtSiteId) {
        final String methodName = "unregisterLocalProxy";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        // Get the court site object associated with courtSiteId
        LOGGER.debug(FOUR_PARAMS, METHOD, methodName, " - retrieving court site with id ",
            courtSiteId);
        final ICourtSite courtSite =
            getXhbCourtSiteRepository().findByCourtSiteId(courtSiteId.intValue());

        if (courtSite == null) {
            throw new ServiceException("Unable to return court site based on id " + courtSiteId);
        }

        // Now get the associated local proxy and use the local proxy dao to delete it
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, " - retrieving local proxy");
        final ILocalProxy localProxy = courtSite.getLocalProxy();

        if (localProxy == null) {
            throw new ServiceException(
                "Unable to return local proxy based on courtSiteId " + courtSiteId);
        }

        // remove the CDUs centrally as part of unregister Local Proxy - NB The actual local proxy
        // server will remain
        // unchanged
        LOGGER.debug(THREE_PARAMS, METHOD, methodName,
            " - about to delete CDUs on the local proxy");
        for (ICduModel cdu : courtSite.getCdus()) {
            getXhbDispMgrCduRepository().deleteDaoFromBasicValue(cdu);
        }

        // Show Some proxy details to indicate we have retrieved it
        LOGGER.debug(THREE_PARAMS, METHOD, methodName,
            " - Showing proxy IP details " + localProxy.getIpAddress());

        LOGGER.debug(THREE_PARAMS, METHOD, methodName, " - about to delete local proxy");
        getXhbDispMgrLocalProxyRepository().deleteDaoFromBasicValue(localProxy);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);

    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyService#
     * registerLocalProxy(uk.gov. hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy)
     */
    @Override
    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void registerLocalProxy(final LocalProxyRegisterCommand localProxyRegisterCommand) {
        final String methodName = "registerLocalProxy";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        /*
         * Attempt to grab the courtsite (from the xhb_disp_mgr_court_site table) we wish to link
         * to, this may/may not exist
         */
        ICourtSite courtSite = getXhbCourtSiteRepository().findCourtSiteByXhibitCourtSiteId(
            localProxyRegisterCommand.getXhibitCourtSiteId().intValue());
        String updatedMessage = " newly created ";

        if (courtSite == null) {
            // Not found so we need to add one
            LOGGER.debug(THREE_PARAMS, METHOD, methodName, " court site not found for id "
                + localProxyRegisterCommand.getXhibitCourtSiteId() + "  - creating new court site");
            courtSite = new CourtSite();

            // Need to link it back to Xhibit Court Site
            final IXhibitCourtSite xhibitCourtSite =
                getXhbDispMgrCourtSiteRepository().findByXhibitCourtSiteId(
                    localProxyRegisterCommand.getXhibitCourtSiteId().intValue());
            courtSite.setXhibitCourtSite(xhibitCourtSite);
            LOGGER.debug(THREE_PARAMS, METHOD, methodName, " court site created ");
        } else {
            LOGGER.debug(THREE_PARAMS, METHOD, methodName,
                " court site found for id " + localProxyRegisterCommand.getXhibitCourtSiteId());
            updatedMessage = " updated ";
        }

        // Set the required power save schedule on court site
        final ISchedule schedule = getXhbDispMgrScheduleRepository()
            .findByScheduleId(localProxyRegisterCommand.getScheduleId().intValue());
        courtSite.setSchedule(schedule);

        // Set the other court site fields
        courtSite.setPageUrl(pageUrl);
        courtSite.setTitle(localProxyRegisterCommand.getTitle());
        courtSite.setNotification(localProxyRegisterCommand.getNotification());
        courtSite.setRagStatus(greenChar.toString());
        courtSite.setRagStatusDate(LocalDateTime.now());

        LOGGER.debug(FIVE_PARAMS, METHOD, methodName, " saving ", updatedMessage, " court site");
        getXhbCourtSiteRepository().updateDaoFromBasicValue(courtSite);

        LOGGER.debug(THREE_PARAMS, METHOD, methodName, " create new localProxy");
        final LocalProxy localProxy = new LocalProxy();
        localProxy.setIpAddress(localProxyRegisterCommand.getIpAddress());
        localProxy.setCourtSite(courtSite);
        localProxy.setRagStatus(greenChar.toString());
        localProxy.setRagStatusDate(LocalDateTime.now());

        // set the flag to determine if the notification value has changed
        // for registerLocalProxy it will always be true
        final boolean updateNotification = true;

        if (localProxyCommunicationEnabled) {
            // Rest call to send court site data and retrieve the localproxy hostname
            final String localProxyHostname =
                localProxyRestClient.saveLocalProxy(localProxy, updateNotification);

            // Set the local proxy hostname and save.
            localProxy.setHostname(localProxyHostname);
        } else {
            localProxy.setHostname(TEST_HOSTNAME + courtSite.getTitle());
        }

        LOGGER.debug(THREE_PARAMS, METHOD, methodName, " About to save new localProxy");
        getXhbDispMgrLocalProxyRepository().saveDaoFromBasicValue(localProxy);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ILocalProxyService#
     * updateLocalProxy(uk.gov. hmcts.pdm.publicdisplay.manager.web.proxies.CourtSiteDto,
     * uk.gov.hmcts.pdm.publicdisplay.manager.web.proxies.LocalProxyAmendCommand)
     */
    @Override
    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateLocalProxy(final CourtSiteDto courtSiteDto,
        final LocalProxyAmendCommand localProxyAmendCommand) {

        final String methodName = "updateLocalProxy";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        // Get the court site
        final ICourtSite courtSite =
            getXhbCourtSiteRepository().findByCourtSiteId(courtSiteDto.getId().intValue());

        if (courtSite == null) {
            throw new ServiceException("Unable to update court site. Court site does not exist "
                + courtSiteDto.getTitle());
        }

        // Update the editable data
        final ISchedule schedule = getXhbDispMgrScheduleRepository()
            .findByScheduleId(localProxyAmendCommand.getScheduleId().intValue());
        if (schedule == null) {
            throw new ServiceException("Unable to update court site as schedule does not exist ");
        }

        // set the flag to determine if the notification value has changed
        boolean updateNotification = true;
        if (localProxyAmendCommand.getNotification().equals(courtSite.getNotification())) {
            updateNotification = false;
        }

        courtSite.setSchedule(schedule);
        courtSite.setTitle(localProxyAmendCommand.getTitle());
        if (updateNotification) {
            courtSite.setNotification(localProxyAmendCommand.getNotification());
            for (ICduModel cdu : courtSite.getCdus()) {
                cdu.setNotification(localProxyAmendCommand.getNotification());
                getXhbDispMgrCduRepository().updateDaoFromBasicValue(cdu);
            }
        }

        // save the court site
        getXhbCourtSiteRepository().updateDaoFromBasicValue(courtSite);

        // sync updates on local proxy server
        if (localProxyCommunicationEnabled) {
            localProxyRestClient.saveLocalProxy(courtSite.getLocalProxy(), updateNotification);

        }

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }

    /**
     * Gets the dashboard cdu dtos.
     *
     * @param cdus the cdus
     * @return the dashboard cdu dtos
     */
    private List<DashboardCduDto> getDashboardCduDtos(final Set<ICduModel> cdus) {
        final List<DashboardCduDto> dtos = new ArrayList<>();
        DashboardCduDto dto;
        for (ICduModel cdu : cdus) {
            dto = createDashboardCduDto();
            dto.setCduNumber(cdu.getCduNumber());
            dto.setIpAddress(cdu.getIpAddress());
            dto.setMacAddress(cdu.getMacAddress());
            dto.setLocation(cdu.getLocation());
            dto.setUrls(getDashboardUrls(cdu.getUrls()));
            dto.setRagStatus(cdu.getRagStatus());
            dto.setOfflineIndicator(cdu.getOfflineIndicator());
            dtos.add(dto);
        }
        Collections.sort(dtos, (cdu1, cdu2) -> String.CASE_INSENSITIVE_ORDER
            .compare(cdu1.getLocation(), cdu2.getLocation()));
        return dtos;
    }

}
