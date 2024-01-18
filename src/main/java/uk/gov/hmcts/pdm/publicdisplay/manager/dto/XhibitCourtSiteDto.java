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
 * @author scullionm
 *
 */
public class XhibitCourtSiteDto implements Serializable {
    
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Id of the Xhibit Court Site.
     */
    @EncryptedFormat
    private Long id;

    /**
     * Human readable name of the Court site.
     */
    private String courtSiteName;
    
    /** The court site code. */
    private String courtSiteCode;
    
    /** The court id. */
    private Integer courtId;
    
    /** The address id. */
    private Integer addressId;

    /** The rag status. */
    private String ragStatus;

    /** The registered indicator. */
    private Character registeredIndicator;

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
     * getCourtSiteName.
     * 
     * @return the courtSiteName'
     */
    public String getCourtSiteName() {
        return courtSiteName;
    }

    /**
     * setCourtSiteName.
     * 
     * @param courtSiteName the courtSiteName to set.
     */
    public void setCourtSiteName(final String courtSiteName) {
        this.courtSiteName = courtSiteName;
    }

    /**
     * getCourtSiteCode.
     * 
     * @return the courtSiteCode'
     */
    public String getCourtSiteCode() {
        return courtSiteCode;
    }

    /**
     * setCourtSiteCode.
     * 
     * @param courtSiteCode the courtSiteCode to set.
     */
    public void setCourtSiteCode(final String courtSiteCode) {
        this.courtSiteCode = courtSiteCode;
    }
    
    /**
     * getCourtId.
     * 
     * @return the courtId
     */
    public Integer getCourtId() {
        return courtId;
    }

    /**
     * setCourtId.
     * 
     * @param courtId the courtId to set.
     */
    public void setCourtId(final Integer courtId) {
        this.courtId = courtId;
    }
    
    /**
     * getAddressId.
     * 
     * @return the addressId
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     * setAddressId.
     * 
     * @param addressId the addressId to set.
     */
    public void setAddressId(final Integer addressId) {
        this.addressId = addressId;
    }
    
    /**
     * getRagStatus.
     * 
     * @return the ragStatus
     */
    public String getRagStatus() {
        return ragStatus;
    }

    /**
     * setRagStatus.
     * 
     * @param ragStatus the ragStatus to set
     */
    public void setRagStatus(final String ragStatus) {
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
