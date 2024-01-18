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

package uk.gov.hmcts.pdm.publicdisplay.manager.domain;

import uk.gov.hmcts.pdm.publicdisplay.common.domain.AbstractDomainObject;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrlModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class Cdu.
 *
 * @author uphillj
 */
public class CduModel extends AbstractDomainObject implements ICduModel {
    /** The cdu number. */
    private String cduNumber;

    /** The mac address. */
    private String macAddress;

    /** The ip address. */
    private String ipAddress;

    /** The title. */
    private String title;

    /** The description. */
    private String description;

    /** The location. */
    private String location;

    /** The refresh. */
    private Long refresh;

    /** The weighting. */
    private Long weighting;

    /** The notification. */
    private String notification;

    /** The offline indicator (Y or N). */
    private Character offlineIndicator;

    /** The RAG status (R, A or G). */
    private Character ragStatus;

    /** The RAG status date. */
    private LocalDateTime ragStatusDate;

    /** The court site. */
    private ICourtSite courtSite;

    /** The urls. */
    private List<IUrlModel> urls = new ArrayList<>();

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getCduNumber()
     */
    @Override
    public String getCduNumber() {
        return cduNumber;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setCduNumber(java.lang.
     * String)
     */
    @Override
    public void setCduNumber(final String cduNumber) {
        this.cduNumber = cduNumber;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getMacAddress()
     */
    @Override
    public String getMacAddress() {
        return macAddress;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setMacAddress(java.lang.
     * String)
     */
    @Override
    public void setMacAddress(final String macAddress) {
        this.macAddress = macAddress;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getIpAddress()
     */
    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setIpAddress(java.lang.
     * String)
     */
    @Override
    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getTitle()
     */
    @Override
    public String getTitle() {
        return title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setTitle(java.lang.String)
     */
    @Override
    public void setTitle(final String title) {
        this.title = title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setDescription(java.lang.
     * String)
     */
    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getLocation()
     */
    @Override
    public String getLocation() {
        return location;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setLocation(java.lang.
     * String)
     */
    @Override
    public void setLocation(final String location) {
        this.location = location;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getNotification()
     */
    @Override
    public String getNotification() {
        return notification;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setNotification(java.lang.
     * String)
     */
    @Override
    public void setNotification(final String notification) {
        this.notification = notification;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getRefresh()
     */
    @Override
    public Long getRefresh() {
        return refresh;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setRefresh(java.lang.Long)
     */
    @Override
    public void setRefresh(final Long refresh) {
        this.refresh = refresh;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getWeighting()
     */
    @Override
    public Long getWeighting() {
        return weighting;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setWeighting(java.lang.Long)
     */
    @Override
    public void setWeighting(final Long weighting) {
        this.weighting = weighting;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getOfflineIndicator()
     */
    @Override
    public Character getOfflineIndicator() {
        return offlineIndicator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setOfflineIndicator(java.
     * lang. Character)
     */
    @Override
    public void setOfflineIndicator(final Character offlineIndicator) {
        this.offlineIndicator = offlineIndicator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getRagStatus()
     */
    @Override
    public Character getRagStatus() {
        return ragStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setRagStatus(java.lang.
     * Character)
     */
    @Override
    public void setRagStatus(final Character ragStatus) {
        this.ragStatus = ragStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getRagStatusDate()
     */
    @Override
    public LocalDateTime getRagStatusDate() {
        return ragStatusDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setRagStatusDate(org.joda.
     * time .DateTime)
     */
    @Override
    public void setRagStatusDate(final LocalDateTime ragStatusDate) {
        this.ragStatusDate = ragStatusDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getCourtSite()
     */
    @Override
    public ICourtSite getCourtSite() {
        return courtSite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setCourtSite(uk.gov.
     * hmcts.pdm. publicdisplay.manager.domain.api.ICourtSite)
     */
    @Override
    public void setCourtSite(final ICourtSite courtSite) {
        this.courtSite = courtSite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#getUrls()
     */
    @Override
    public List<IUrlModel> getUrls() {
        return urls;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICdu#setUrls(java.util.List)
     */
    @Override
    public void setUrls(final List<IUrlModel> urls) {
        this.urls = urls;
    }

}
