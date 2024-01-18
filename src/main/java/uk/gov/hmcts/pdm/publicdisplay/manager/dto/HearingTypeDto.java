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
 * Simple transfer object used to move hearing type details around.
 * 
 * @author gittinsl
 *
 */
public class HearingTypeDto implements Serializable {
    
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @EncryptedFormat
    private Integer refHearingTypeId;

    private String hearingTypeCode;

    private String hearingTypeDesc;

    private String category;

    private Integer seqNo;

    private Integer listSequence;
    
    @EncryptedFormat
    private Integer courtId;

    public Integer getRefHearingTypeId() {
        return refHearingTypeId;
    }

    public void setRefHearingTypeId(Integer refHearingTypeId) {
        this.refHearingTypeId = refHearingTypeId;
    }

    public String getHearingTypeCode() {
        return hearingTypeCode;
    }

    public void setHearingTypeCode(String hearingTypeCode) {
        this.hearingTypeCode = hearingTypeCode;
    }

    public String getHearingTypeDesc() {
        return hearingTypeDesc;
    }

    public void setHearingTypeDesc(String hearingTypeDesc) {
        this.hearingTypeDesc = hearingTypeDesc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getListSequence() {
        return listSequence;
    }

    public void setListSequence(Integer listSequence) {
        this.listSequence = listSequence;
    }

    public Integer getCourtId() {
        return courtId;
    }

    public void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }
}
