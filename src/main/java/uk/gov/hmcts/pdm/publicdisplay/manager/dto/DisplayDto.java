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
 * Simple transfer object used to move display details around.
 * 
 * @author harrism
 *
 */
public class DisplayDto implements Serializable {
    
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @EncryptedFormat
    private Integer displayId;

    @EncryptedFormat
    private Integer displayTypeId;

    @EncryptedFormat
    private Integer displayLocationId;

    @EncryptedFormat
    private Integer rotationSetId;

    private String descriptionCode;

    private String locale;

    private String showUnassignedYn;
    
    // Detail objects (for display only - ie in delete screen)
    
    private DisplayTypeDto displayType;
    
    private XhibitCourtSiteDto courtSite;
    
    private RotationSetsDto rotationSet;

    public Integer getDisplayId() {
        return displayId;
    }

    public final void setDisplayId(Integer displayId) {
        this.displayId = displayId;
    }

    public Integer getDisplayTypeId() {
        return displayTypeId;
    }

    public final void setDisplayTypeId(Integer displayTypeId) {
        this.displayTypeId = displayTypeId;
    }

    public Integer getDisplayLocationId() {
        return displayLocationId;
    }

    public final void setDisplayLocationId(Integer displayLocationId) {
        this.displayLocationId = displayLocationId;
    }

    public Integer getRotationSetId() {
        return rotationSetId;
    }

    public final void setRotationSetId(Integer rotationSetId) {
        this.rotationSetId = rotationSetId;
    }

    public String getDescriptionCode() {
        return descriptionCode;
    }

    public final void setDescriptionCode(String descriptionCode) {
        this.descriptionCode = descriptionCode;
    }

    public String getLocale() {
        return locale;
    }

    public final void setLocale(String locale) {
        this.locale = locale;
    }

    public String getShowUnassignedYn() {
        return showUnassignedYn;
    }

    public final void setShowUnassignedYn(String showUnassignedYn) {
        this.showUnassignedYn = showUnassignedYn;
    }

    public DisplayTypeDto getDisplayType() {
        return displayType;
    }

    public void setDisplayType(DisplayTypeDto displayType) {
        this.displayType = displayType;
    }

    public XhibitCourtSiteDto getCourtSite() {
        return courtSite;
    }

    public void setCourtSite(XhibitCourtSiteDto courtSite) {
        this.courtSite = courtSite;
    }

    public RotationSetsDto getRotationSet() {
        return rotationSet;
    }

    public void setRotationSet(RotationSetsDto rotationSet) {
        this.rotationSet = rotationSet;
    }

}
