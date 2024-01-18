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

/**
 * The Interface IServiceAudit.
 *
 * @author uphillj
 */
public interface IServiceAudit extends IDomainObject {

    /**
     * Gets the from endpoint.
     *
     * @return the from endpoint
     */
    String getFromEndpoint();

    /**
     * Sets the from endpoint.
     *
     * @param fromEndpoint the new from endpoint
     */
    void setFromEndpoint(String fromEndpoint);

    /**
     * Gets the to endpoint.
     *
     * @return the to endpoint
     */
    String getToEndpoint();

    /**
     * Sets the to endpoint.
     *
     * @param toEndpoint the new to endpoint
     */
    void setToEndpoint(String toEndpoint);

    /**
     * Gets the service.
     *
     * @return the service
     */
    String getService();

    /**
     * Sets the service.
     *
     * @param service the new service
     */
    void setService(String service);

    /**
     * Gets the url.
     *
     * @return the url
     */
    String getUrl();

    /**
     * Sets the url.
     *
     * @param url the new url
     */
    void setUrl(String url);

    /**
     * Gets the message id.
     *
     * @return the message id
     */
    String getMessageId();

    /**
     * Sets the message id.
     *
     * @param messageId the new message id
     */
    void setMessageId(String messageId);

    /**
     * Gets the message status.
     *
     * @return the message status
     */
    String getMessageStatus();

    /**
     * Sets the message status.
     *
     * @param messageStatus the new message status
     */
    void setMessageStatus(String messageStatus);

    /**
     * Gets the message request.
     *
     * @return the message request
     */
    String getMessageRequest();

    /**
     * Sets the message request.
     *
     * @param messageRequest the new message request
     */
    void setMessageRequest(String messageRequest);

    /**
     * Gets the message response.
     *
     * @return the message response
     */
    String getMessageResponse();

    /**
     * Sets the message response.
     *
     * @param messageResponse the new message response
     */
    void setMessageResponse(String messageResponse);
}
