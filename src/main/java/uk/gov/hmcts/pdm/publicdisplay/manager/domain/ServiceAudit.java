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
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit;

/**
 * The Class ServiceAudit.
 * 
 * @author uphillj
 *
 */
public class ServiceAudit extends AbstractDomainObject implements IServiceAudit {

    /** The from endpoint. */
    private String fromEndpoint;

    /** The to endpoint. */
    private String toEndpoint;

    /** The service. */
    private String service;

    /** The URL. */
    private String url;

    /** The message id. */
    private String messageId;

    /** The message status. */
    private String messageStatus;

    /** The message request. */
    private String messageRequest;

    /** The message response. */
    private String messageResponse;

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#getFromEndpoint()
     */
    @Override
    public String getFromEndpoint() {
        return fromEndpoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#setFromEndpoint(
     * java. lang.String)
     */
    @Override
    public void setFromEndpoint(final String fromEndpoint) {
        this.fromEndpoint = fromEndpoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#getToEndpoint()
     */
    @Override
    public String getToEndpoint() {
        return toEndpoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#setToEndpoint(java.
     * lang.String)
     */
    @Override
    public void setToEndpoint(final String toEndpoint) {
        this.toEndpoint = toEndpoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#getService()
     */
    @Override
    public String getService() {
        return service;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#setService(java.
     * lang. String)
     */
    @Override
    public void setService(final String service) {
        this.service = service;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#getUrl()
     */
    @Override
    public String getUrl() {
        return url;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#setUrl(java.lang.
     * String)
     */
    @Override
    public void setUrl(final String url) {
        this.url = url;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#getMessageId()
     */
    @Override
    public String getMessageId() {
        return messageId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#setMessageId(java.
     * lang.String)
     */
    @Override
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#getMessageStatus()
     */
    @Override
    public String getMessageStatus() {
        return messageStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#setMessageStatus(
     * java .lang.String)
     */
    @Override
    public void setMessageStatus(final String messageStatus) {
        this.messageStatus = messageStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#getMessageRequest()
     */
    @Override
    public String getMessageRequest() {
        return messageRequest;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#setMessageRequest(
     * java.lang.String)
     */
    @Override
    public void setMessageRequest(final String messageRequest) {
        this.messageRequest = messageRequest;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#getMessageResponse(
     * )
     */
    @Override
    public String getMessageResponse() {
        return messageResponse;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IServiceAudit#setMessageResponse(
     * java.lang.String)
     */
    @Override
    public void setMessageResponse(final String messageResponse) {
        this.messageResponse = messageResponse;
    }

}
