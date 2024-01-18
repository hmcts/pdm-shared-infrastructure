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
/**
 * Simple transfer object used to move court site details around.
 * 
 * @author uphillj
 *
 */

public class CourtSiteDto implements Serializable {
    /**
     * SerialVersionUID of the CourtSiteDto class.
     */
    private static final long serialVersionUID = -8424685089303555647L;

    /** Id of the Court Site. */
    @EncryptedFormat
    private Long id;

    /** The page url. */
    private String pageUrl;

    /** The title. */
    private String title;

    /** The ip address. */
    private String ipAddress;

    /** The schedule id. */
    @EncryptedFormat
    private Long scheduleId;

    /** The schedule title. */
    private String scheduleTitle;

    /** The notification. */
    private String notification;

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
     * @param id the id to set.
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * getPageUrl.
     * 
     * @return the pageUrl
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * setPageUrl.
     * 
     * @param pageUrl the pageUrl to set
     */
    public void setPageUrl(final String pageUrl) {
        this.pageUrl = pageUrl;
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
     * getScheduleId.
     * 
     * @return the scheduleId
     */
    public Long getScheduleId() {
        return scheduleId;
    }

    /**
     * setScheduleId.
     * 
     * @param scheduleId the scheduleId to set
     */
    public void setScheduleId(final Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * getScheduleTitle.
     * 
     * @return the scheduleTitle
     */
    public String getScheduleTitle() {
        return scheduleTitle;
    }

    /**
     * setScheduleTitle.
     * 
     * @param scheduleTitle the scheduleTitle to set
     */
    public void setScheduleTitle(final String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
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
