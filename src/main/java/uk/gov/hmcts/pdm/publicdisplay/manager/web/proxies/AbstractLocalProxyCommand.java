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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.proxies;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptedFormat;


/**
 * The Class AbstractLocalProxyCommand.
 * 
 * @author harrism
 *
 */
public class AbstractLocalProxyCommand {
    /**
     * Maximum length for title field.
     */
    private static final int TITLE_MAX_LENGTH = 255;

    /**
     * Maximum length for Notification field.
     */
    private static final int NOTIFICATION_MAX_LENGTH = 500;

    /** The title. */
    @NotBlank(message = "{localProxyCommand.title.notBlank}")
    @Length(max = TITLE_MAX_LENGTH, message = "{localProxyCommand.title.tooLong}")
    private String title;

    /**
     * The schedule id.
     */
    @EncryptedFormat
    @NotNull(message = "{localProxyCommand.scheduleId.notNull}")
    private Long scheduleId;

    /** The notification field. */
    @Length(max = NOTIFICATION_MAX_LENGTH, message = "{localProxyCommand.notification.tooLong}")
    private String notification;



    protected AbstractLocalProxyCommand() {
        /*
         * empty constructor
         */
    }

    /**
     * getTitle.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * setTitle.
     * @param title the title to set
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * getScheduleId.
     * @return the schedule id
     */
    public Long getScheduleId() {
        return scheduleId;
    }

    /**
     * setScheduleId.
     * @param scheduleId the scheduleId to set
     */
    public void setScheduleId(final Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * Gets the notification.
     *
     * @return the notification
     */
    public String getNotification() {
        return notification;
    }

    /**
     * Sets the notification.
     *
     * @param notification the notification to set
     */
    public void setNotification(final String notification) {
        this.notification = notification;
    }
}
