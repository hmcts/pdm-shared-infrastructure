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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.display;

import jakarta.validation.constraints.NotNull;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptedFormat;

/**
 * The Class AbstractDisplayCommand.
 * 
 * @author harrism
 *
 */
public class AbstractDisplayCommand {

    /** The display type field. */
    @EncryptedFormat
    @NotNull(message = "{displayAmendCommand.displayTypeId.notNull}")
    private Integer displayTypeId;
    
    /** The ID of the CourtSite. */
    @EncryptedFormat
    @NotNull(message = "{displayAmendCommand.xhibitCourtSiteId.notNull}")
    private Long xhibitCourtSiteId;
    
    /** The rotation set id field. */
    @EncryptedFormat
    @NotNull(message = "{displayAmendCommand.rotationSetId.notNull}")
    private Integer rotationSetId;

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
     * @param xhibitCourtSiteId the xhibitCourtSiteId to set.
     */
    public void setXhibitCourtSiteId(final Long xhibitCourtSiteId) {
        this.xhibitCourtSiteId = xhibitCourtSiteId;
    }
    
    /**
     * Gets the rotation set id.
     *
     * @return the rotation set id
     */
    public Integer getRotationSetId() {
        return rotationSetId;
    }

    /**
     * Sets the rotation set id.
     *
     * @param rotationSetId the rotation to set
     */
    public void setRotationSetId(final Integer rotationSetId) {
        this.rotationSetId = rotationSetId;
    }
    
    /**
     * Gets the display type id.
     *
     * @return the display type id
     */
    public Integer getDisplayTypeId() {
        return displayTypeId;
    }

    /**
     * Sets the display type id.
     *
     * @param displayTypeId the display type id to set
     */
    public void setDisplayTypeId(final Integer displayTypeId) {
        this.displayTypeId = displayTypeId;
    }
}
