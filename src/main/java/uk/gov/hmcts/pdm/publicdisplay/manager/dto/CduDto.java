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

package uk.gov.hmcts.pdm.publicdisplay.manager.dto;

import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptedFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple transfer object used to move cdu details around.
 * 
 * @author groenm
 *
 */

@SuppressWarnings("PMD.TooManyFields")
public class CduDto implements Serializable {

    /**
     * SerialVersionUID of the CduDto class.
     */
    private static final long serialVersionUID = 6707474093197781339L;

    /**
     * Id of the CDU.
     */
    @EncryptedFormat
    private Long id;

    /**
     * MacAddress.
     */
    private String macAddress;

    /** The court site id. */
    @EncryptedFormat
    private Long courtSiteId;

    /**
     * Name of the Court site the cdu associated to.
     */
    private String courtSiteName;

    /**
     * ipAddress of the CDU.
     */
    private String ipAddress;

    /**
     * Description.
     */
    private String description;

    /**
     * Notification.
     */
    private String notification;

    /**
     * Location.
     */
    private String location;

    /**
     * Refresh.
     */
    private Long refresh;

    /**
     * Weighting.
     */
    private Long weighting;

    /**
     * Offline indicator (Y or N).
     */
    private Character offlineIndicator;

    /**
     * CDU Number.
     */
    private String cduNumber;

    /**
     * The court site page url.
     */
    private String courtSitePageUrl;

    /**
     * The urls as a list of UrlDto objects.
     */
    private List<UrlDto> urls = new ArrayList<>();

    /**
     * The registered indicator (Y or N).
     * 
     */
    private Character registeredIndicator;

    /**
     * The registered indicator (Y or N).
     * 
     */
    private Character registeredCentrally;

    /**
     * The registered indicator (Y or N).
     * 
     */
    private Character registeredLocalProxy;

    /**
     * Xhibit Court Site Id of the CDU.
     */
    @EncryptedFormat
    private Long xhibitCourtSiteId;

    /**
     * getId.
     * 
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * setId.
     * 
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * getMacAddress.
     * 
     * @return the macAddress
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Additional getter for returning unique encrypted identifier where it is used for selection
     * purposes, rather than for display on the page, to prevent user submitting their own.
     * 
     * @return unique encrypted identifier
     */
    @EncryptedFormat
    public String getIdentifier() {
        return macAddress;
    }

    /**
     * setMacAddress.
     * 
     * @param macAddress the macAddress to set
     */
    public void setMacAddress(final String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * getCourtSiteId.
     * 
     * @return the courtSiteId
     */
    public Long getCourtSiteId() {
        return courtSiteId;
    }

    /**
     * setCourtSiteId.
     * 
     * @param courtSiteId the courtSiteId to set
     */
    public void setCourtSiteId(final Long courtSiteId) {
        this.courtSiteId = courtSiteId;
    }

    /**
     * getCourtSiteName.
     * 
     * @return the courtSiteName
     */
    public String getCourtSiteName() {
        return courtSiteName;
    }

    /**
     * setCourtSiteName.
     * 
     * @param courtSiteName the courtSiteName to set
     */
    public void setCourtSiteName(final String courtSiteName) {
        this.courtSiteName = courtSiteName;
    }

    /**
     * getIpAddress.
     * 
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * setIpAddress.
     * 
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * getDescription.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setDescription.
     * 
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * getNotification.
     * 
     * @return the notification
     */
    public String getNotification() {
        return notification;
    }

    /**
     * setNotification.
     * 
     * @param notification the notification to set
     */
    public void setNotification(final String notification) {
        this.notification = notification;
    }

    /**
     * getLocation.
     * 
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * setLocation.
     * 
     * @param location the location to set
     */
    public void setLocation(final String location) {
        this.location = location;
    }

    /**
     * getRefresh.
     * 
     * @return the refresh
     */
    public Long getRefresh() {
        return refresh;
    }

    /**
     * setRefresh.
     * 
     * @param refresh the refresh to set
     */
    public void setRefresh(final Long refresh) {
        this.refresh = refresh;
    }

    /**
     * getWeighting.
     * 
     * @return the weighting
     */
    public Long getWeighting() {
        return weighting;
    }

    /**
     * setWeighting.
     * 
     * @param weighting the weighting to set
     */
    public void setWeighting(final Long weighting) {
        this.weighting = weighting;
    }

    /**
     * getOfflineIndicator.
     * 
     * @return the offlineIndicator
     */
    public Character getOfflineIndicator() {
        return offlineIndicator;
    }

    /**
     * setOfflineIndicator.
     * 
     * @param offlineIndicator the offlineIndicator to set
     */
    public void setOfflineIndicator(final Character offlineIndicator) {
        this.offlineIndicator = offlineIndicator;
    }

    /**
     * getCduNumber.
     * 
     * @return the cduNumber
     */
    public String getCduNumber() {
        return cduNumber;
    }

    /**
     * setCduNumber.
     * 
     * @param cduNumber the cduNumber to set
     */
    public void setCduNumber(final String cduNumber) {
        this.cduNumber = cduNumber;
    }

    /**
     * getCourtSitePageUrl.
     * 
     * @return the courtSitePageUrl
     */
    public String getCourtSitePageUrl() {
        return courtSitePageUrl;
    }

    /**
     * setCourtSitePageUrl.
     * 
     * @param courtSitePageUrl the courtSitePageUrl to set
     */
    public void setCourtSitePageUrl(final String courtSitePageUrl) {
        this.courtSitePageUrl = courtSitePageUrl;
    }

    /**
     * getUrls.
     * 
     * @return the urls
     */
    public List<UrlDto> getUrls() {
        return urls;
    }

    /**
     * setUrls.
     * 
     * @param urls the urls to set
     */
    public void setUrls(final List<UrlDto> urls) {
        this.urls = urls;
    }

    /**
     * getRegisteredIndicator.
     * 
     * @return the registeredIndicator
     */
    public Character getRegisteredIndicator() {
        return registeredIndicator;
    }

    /**
     * setRegisteredIndicator.
     * 
     * @param registeredIndicator the registeredIndicator to set
     */
    public void setRegisteredIndicator(final Character registeredIndicator) {
        this.registeredIndicator = registeredIndicator;
    }

    /**
     * getRegisteredCentrally.
     * 
     * @return the registeredCentrally
     */
    public Character getRegisteredCentrally() {
        return registeredCentrally;
    }

    /**
     * setRegisteredCentrally.
     * 
     * @param registeredCentrally the registeredCentrally to set
     */
    public void setRegisteredCentrally(final Character registeredCentrally) {
        this.registeredCentrally = registeredCentrally;
    }

    /**
     * getRegisteredLocalProxy.
     * 
     * @return the registeredLocalProxy
     */
    public Character getRegisteredLocalProxy() {
        return registeredLocalProxy;
    }

    /**
     * setRegisteredLocalProxy.
     * 
     * @param registeredLocalProxy the registeredLocalProxy to set
     */
    public void setRegisteredLocalProxy(final Character registeredLocalProxy) {
        this.registeredLocalProxy = registeredLocalProxy;
    }

    /**
     * getXhibitCourtSiteId.
     * 
     * @return the xhibitCourtSiteId
     */
    public Long getXhibitCourtSiteId() {
        return xhibitCourtSiteId;
    }

    /**
     * setXhibitCourtSiteId.
     * 
     * @param xhibitCourtSiteId the xhibitCourtSiteId to set
     */
    public void setXhibitCourtSiteId(final Long xhibitCourtSiteId) {
        this.xhibitCourtSiteId = xhibitCourtSiteId;
    }

}
