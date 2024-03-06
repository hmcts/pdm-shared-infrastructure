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
 * The Class CourtSiteJson.
 *
 * @author uphillj
 */
public class CourtSiteJson extends AbstractJsonObject {

    /** The site id. */
    private Long siteId;

    /** The title. */
    private String title;

    /** The page url. */
    private String pageUrl;

    /** The powersave schedule. */
    private String powersaveSchedule;

    /** The welsh title. */
    private String welshTitle;

    /** The host name. */
    private String hostName;

    /** The notification displayed on all CDUs when not null. */
    private String notification;

    /**
     * Gets the site id.
     *
     * @return the site id
     */
    public Long getSiteId() {
        return siteId;
    }

    /**
     * Sets the site id.
     *
     * @param siteId the new site id
     */
    public void setSiteId(final Long siteId) {
        this.siteId = siteId;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Gets the page url.
     *
     * @return the page url
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * Sets the page url.
     *
     * @param pageUrl the new page url
     */
    public void setPageUrl(final String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * Gets the powersave schedule.
     *
     * @return the powersave schedule
     */
    public String getPowersaveSchedule() {
        return powersaveSchedule;
    }

    /**
     * Sets the powersave schedule.
     *
     * @param powersaveSchedule the new powersave schedule
     */
    public void setPowersaveSchedule(final String powersaveSchedule) {
        this.powersaveSchedule = powersaveSchedule;
    }

    /**
     * getWelshTitle.
     * 
     * @return the welshTitle
     */
    public String getWelshTitle() {
        return welshTitle;
    }

    /**
     * setWelshTitle.
     * 
     * @param welshTitle the welshTitle to set
     */
    public void setWelshTitle(final String welshTitle) {
        this.welshTitle = welshTitle;
    }

    /**
     * getHostName.
     * 
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * setHostName.
     * 
     * @param hostName the hostName to set
     */
    public void setHostName(final String hostName) {
        this.hostName = hostName;
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

}
