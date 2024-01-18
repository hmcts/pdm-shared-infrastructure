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
import java.util.Set;


/**
 * The Interface ICourtSite.
 *
 * @author uphillj
 */
public interface ICourtSite extends IDomainObject {

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
     * Gets the page url.
     *
     * @return the page url
     */
    String getPageUrl();

    /**
     * Sets the page url.
     *
     * @param pageUrl the new page url
     */
    void setPageUrl(String pageUrl);

    /**
     * Gets the rag status.
     *
     * @return the rag status
     */
    String getRagStatus();

    /**
     * Sets the rag status.
     *
     * @param ragStatus the new rag status
     */
    void setRagStatus(String ragStatus);

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
     * Gets the xhibit court site.
     *
     * @return the xhibit court site
     */
    IXhibitCourtSite getXhibitCourtSite();

    /**
     * Sets the xhibit court site.
     *
     * @param xhibitCourtSite the new xhibit court site
     */
    void setXhibitCourtSite(IXhibitCourtSite xhibitCourtSite);

    /**
     * Gets the schedule.
     *
     * @return the schedule
     */
    ISchedule getSchedule();

    /**
     * Sets the schedule.
     *
     * @param schedule the new schedule
     */
    void setSchedule(ISchedule schedule);

    /**
     * Gets the local proxy.
     *
     * @return the local proxy
     */
    ILocalProxy getLocalProxy();

    /**
     * Sets the local proxy.
     *
     * @param localProxy the new local proxy
     */
    void setLocalProxy(ILocalProxy localProxy);

    /**
     * Gets the cdus.
     *
     * @return the cdus
     */
    Set<ICduModel> getCdus();

    /**
     * Sets the cdus.
     *
     * @param cdus the new cdus
     */
    void setCdus(Set<ICduModel> cdus);

}
