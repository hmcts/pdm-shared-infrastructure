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
 * Simple transfer object used to move court room details around.
 * 
 * @author harrism
 *
 */
public class CourtRoomDto implements Serializable {
    /**
     * SerialVersionUID of the CourtRoomDto class.
     */
    private static final long serialVersionUID = -8424685089303555646L;

    /** Id of the Court Room. */
    @EncryptedFormat
    private Long id;

    /** The courtRoomName. */
    private String courtRoomName;

    /** The description. */
    private String description;
    
    /** The courtRoomNo. */
    private Integer courtRoomNo;
    
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
     * getCourtRoomName.
     * 
     * @return the courtRoomName
     */
    public String getCourtRoomName() {
        return courtRoomName;
    }

    /**
     * setCourtRoomName.
     * 
     * @param courtRoomName the courtRoomName to set
     */
    public void setCourtRoomName(final String courtRoomName) {
        this.courtRoomName = courtRoomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCourtRoomNo() {
        return courtRoomNo;
    }

    public void setCourtRoomNo(Integer courtRoomNo) {
        this.courtRoomNo = courtRoomNo;
    }
}
