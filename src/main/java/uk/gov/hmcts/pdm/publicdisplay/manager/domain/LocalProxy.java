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

package uk.gov.hmcts.pdm.publicdisplay.manager.domain;

import uk.gov.hmcts.pdm.publicdisplay.common.domain.AbstractDomainObject;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy;

import java.time.LocalDateTime;


/**
 * The Class LocalProxy.
 *
 * @author uphillj
 */
public class LocalProxy extends AbstractDomainObject implements ILocalProxy {

    /** The ip address. */
    private String ipAddress;

    /** The hostname. */
    private String hostname;

    /** The RAG status (R, A or G). */
    private String ragStatus;

    /** The RAG status date. */
    private LocalDateTime ragStatusDate;

    /** The court site. */
    private ICourtSite courtSite;

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy#getIpAddress()
     */
    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy#setIpAddress(java.
     * lang. String)
     */
    @Override
    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy#getHostname()
     */
    @Override
    public String getHostname() {
        return hostname;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy#setHostname(java.
     * lang. String)
     */
    @Override
    public void setHostname(final String hostname) {
        this.hostname = hostname;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy#getRagStatus()
     */
    @Override
    public String getRagStatus() {
        return ragStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy#setRagStatus(java.
     * lang. Character)
     */
    @Override
    public void setRagStatus(final String ragStatus) {
        this.ragStatus = ragStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy#getRagStatusDate()
     */
    @Override
    public LocalDateTime getRagStatusDate() {
        return ragStatusDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy#setRagStatusDate(
     * org.joda.time.DateTime)
     */
    @Override
    public void setRagStatusDate(final LocalDateTime ragStatusDate) {
        this.ragStatusDate = ragStatusDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy#getCourtSite()
     */
    @Override
    public ICourtSite getCourtSite() {
        return courtSite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy#setCourtSite(uk.gov.
     * hmcts.pdm. publicdisplay.manager.domain.api.ICourtSite)
     */
    @Override
    public void setCourtSite(final ICourtSite courtSite) {
        this.courtSite = courtSite;
    }

}
