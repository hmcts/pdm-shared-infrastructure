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

package uk.gov.hmcts.pdm.mockipdmanager.common.json;

/**
 * CduJson.
 * 
 * @author groenm
 *
 */
public class CduJson extends AbstractJsonObject {
    /** The cdu number. */
    private String cduNumber;

    /** The mac address. */
    private String macAddress;

    /** The title. */
    private String title;

    /** The description. */
    private String description;

    /** The location. */
    private String location;

    /** The refresh. */
    private Long refresh;

    /** The site. Foreign key */
    private Long siteId;

    /** The ip address. */
    private String ipAddress;

    /** The notification. */
    private String notification;

    /** The offline indicator (Y or N). */
    private Character offlineIndicator;

    /** The RAG status (R, A or G). */
    private Character ragStatus;

    /** The registered indicator (Y or N). */
    private Character registeredIndicator;

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
     * getMacAddress.
     * 
     * @return the macAddress
     */
    public String getMacAddress() {
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
     * getTitle.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * setTitle.
     * 
     * @param title the title to set
     */
    public void setTitle(final String title) {
        this.title = title;
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
     * getSiteId.
     * 
     * @return the site
     */
    public Long getSiteId() {
        return siteId;
    }

    /**
     * setSiteId.
     * 
     * @param site the site to set
     */
    public void setSiteId(final Long site) {
        this.siteId = site;
    }

    /**
     * getRagStatus.
     * 
     * @return the ragStatus
     */
    public Character getRagStatus() {
        return ragStatus;
    }

    /**
     * setRagStatus.
     * 
     * @param ragStatus the ragStatus to set
     */
    public void setRagStatus(final Character ragStatus) {
        this.ragStatus = ragStatus;
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
}
