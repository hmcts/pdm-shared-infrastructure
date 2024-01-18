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

package uk.gov.hmcts.pdm.publicdisplay.manager.domain.api;

import uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject;

import java.time.LocalDateTime;
import java.util.List;


/**
 * The Interface ICdu.
 *
 * @author uphillj
 */
public interface ICduModel extends IDomainObject {

    /**
     * Gets the cdu number.
     *
     * @return the cdu number
     */
    String getCduNumber();

    /**
     * Sets the cdu number.
     *
     * @param cduNumber the new cdu number
     */
    void setCduNumber(String cduNumber);

    /**
     * Gets the mac address.
     *
     * @return the mac address
     */
    String getMacAddress();

    /**
     * Sets the mac address.
     *
     * @param macAddress the new mac address
     */
    void setMacAddress(String macAddress);

    /**
     * Gets the ip address.
     *
     * @return the ip address
     */
    String getIpAddress();

    /**
     * Sets the ip address.
     *
     * @param ipAddress the new ipAddress
     */
    void setIpAddress(String ipAddress);

    /**
     * Gets the title.
     *
     * @return the title
     */
    String getTitle();

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    void setTitle(String title);

    /**
     * Gets the description.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    void setDescription(String description);

    /**
     * Gets the location.
     *
     * @return the location
     */
    String getLocation();

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    void setLocation(String location);

    /**
     * Gets the notification.
     *
     * @return the notification
     */
    String getNotification();

    /**
     * Sets the notification.
     *
     * @param notification the new notification
     */
    void setNotification(String notification);

    /**
     * Gets the refresh.
     *
     * @return the refresh
     */
    Long getRefresh();

    /**
     * Sets the refresh.
     *
     * @param refresh the new refresh
     */
    void setRefresh(Long refresh);

    /**
     * Gets the weighting.
     *
     * @return the weighting
     */
    Long getWeighting();

    /**
     * Sets the weighting.
     *
     * @param weighting the new weighting
     */
    void setWeighting(Long weighting);

    /**
     * Gets the offline indicator.
     *
     * @return the offline indicator
     */
    Character getOfflineIndicator();

    /**
     * Sets the offline indicator.
     *
     * @param offlineIndicator the new offline indicator
     */
    void setOfflineIndicator(Character offlineIndicator);

    /**
     * Gets the rag status.
     *
     * @return the rag status
     */
    Character getRagStatus();

    /**
     * Sets the rag status.
     *
     * @param ragStatus the new rag status
     */
    void setRagStatus(Character ragStatus);

    /**
     * Gets the rag status date.
     *
     * @return the rag status date
     */
    LocalDateTime getRagStatusDate();

    /**
     * Sets the rag status date.
     *
     * @param ragStatusDate the new rag status date
     */
    void setRagStatusDate(LocalDateTime ragStatusDate);

    /**
     * Gets the court site.
     *
     * @return the court site
     */
    ICourtSite getCourtSite();

    /**
     * Sets the court site.
     *
     * @param courtSite the new court site
     */
    void setCourtSite(ICourtSite courtSite);

    /**
     * Gets the urls.
     *
     * @return the urls
     */
    List<IUrlModel> getUrls();

    /**
     * Sets the urls.
     *
     * @param urls the new urls
     */
    void setUrls(List<IUrlModel> urls);

}
