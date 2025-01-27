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

import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptDecryptUtility;

import java.util.List;


/**
 * Simple transfer object used to send cdu details to the dashboard from json ajax calls. N.B. Any
 * sensitive data MUST be encrypted using the EncryptDecryptUtility because the @EncryptedFormat
 * annotation does not work with json responses.
 * 
 * @author harrism
 *
 */
public class DashboardCduDto {
    /** CDU Number. */
    private String cduNumber;

    /** ipAddress of the CDU. */
    private String ipAddress;

    /** MacAddress. */
    private String macAddress;

    /** Location. */
    private String location;

    /** The urls. */
    private List<DashboardUrlDto> urls;

    /** The RAG status (R, A or G). */
    private Character ragStatus;

    /** The offline indicator (Y or N). */
    private Character offlineIndicator;

    private static final EncryptDecryptUtility INSTANCE = EncryptDecryptUtility.INSTANCE;

    /**
     * getCduNumber.
     * 
     * @return the cduNumber
     */
    public String getCduNumber() {
        return cduNumber;
    }

    /**
     * setCduNumber.
     * 
     * @param cduNumber the cduNumber to set
     */
    public void setCduNumber(final String cduNumber) {
        this.cduNumber = cduNumber;
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
     * getMacAddress.
     * 
     * @return the macAddress
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Additional getter for returning unique encrypted identifier where it is used for selection
     * purposes, rather than for display on the page, to prevent user submitting their own.
     * 
     * @return unique encrypted identifier
     */
    public String getIdentifier() {
        return INSTANCE.encryptData(macAddress);
    }

    /**
     * setMacAddress.
     * 
     * @param macAddress the macAddress to set
     */
    public void setMacAddress(final String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * getLocation.
     * 
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * setLocation.
     * 
     * @param location the location to set
     */
    public void setLocation(final String location) {
        this.location = location;
    }

    /**
     * getRagStatus.
     * 
     * @return the ragStatus
     */
    public Character getRagStatus() {
        return ragStatus;
    }

    /**
     * setRagStatus.
     * 
     * @param ragStatus the ragStatus to set
     */
    public void setRagStatus(final Character ragStatus) {
        this.ragStatus = ragStatus;
    }

    /**
     * getOfflineIndicator.
     * 
     * @return the offlineIndicator
     */
    public Character getOfflineIndicator() {
        return offlineIndicator;
    }

    /**
     * setOfflineIndicator.
     * 
     * @param offlineIndicator the offlineIndicator to set
     */
    public void setOfflineIndicator(final Character offlineIndicator) {
        this.offlineIndicator = offlineIndicator;
    }

    /**
     * getUrls.
     * 
     * @return the urls
     */
    public List<DashboardUrlDto> getUrls() {
        return urls;
    }

    /**
     * setUrls.
     * 
     * @param urls the urls to set
     */
    public void setUrls(final List<DashboardUrlDto> urls) {
        this.urls = urls;
    }

}
