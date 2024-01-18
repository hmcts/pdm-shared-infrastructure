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

package uk.gov.hmcts.pdm.publicdisplay.manager.service.api;

import uk.gov.hmcts.pdm.publicdisplay.common.exception.RestException;
import uk.gov.hmcts.pdm.publicdisplay.common.json.CduJson;
import uk.gov.hmcts.pdm.publicdisplay.common.json.CourtSiteStatusJson;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrlModel;

import java.util.List;


/**
 * The Interface ILocalProxyRestClient.
 *
 * @author boparaij
 */
public interface ILocalProxyRestClient {

    /**
     * Save local proxy.
     *
     * @param localProxy the local proxy
     * @param updateNotification the update notification flag
     * @return the hostname of the local proxy
     * @throws RestException the rest exception
     */
    String saveLocalProxy(ILocalProxy localProxy, boolean updateNotification);

    /**
     * Save the cdu.
     *
     * @param cdu the cdu
     * @throws RestException the rest exception
     */
    void saveCdu(ICduModel cdu);

    /**
     * Save the url.
     *
     * @param url the url
     * @throws RestException the rest exception
     */
    void saveUrl(IUrlModel url);

    /**
     * Save mapping.
     *
     * @param cdu the cdu
     * @param url the url
     * @throws RestException the rest exception
     */
    void saveMapping(ICduModel cdu, IUrlModel url);

    /**
     * Gets the cdus.
     *
     * @param localProxy the local proxy being queried
     * @return the cdus
     * @throws RestException the rest exception
     */
    List<CduJson> getCdus(ILocalProxy localProxy);

    /**
     * Delete local proxy.
     *
     * @param localProxy the local proxy
     * @throws RestException the rest exception
     */
    void deleteLocalProxy(ILocalProxy localProxy);

    /**
     * Delete cdu.
     *
     * @param cdu the cdu
     * @throws RestException the rest exception
     */
    void deleteCdu(ICduModel cdu);

    /**
     * Delete url.
     *
     * @param url the url
     * @throws RestException the rest exception
     */
    void deleteUrl(IUrlModel url);

    /**
     * delete mapping.
     *
     * @param cdu the cdu
     * @param url the url
     * @throws RestException the rest exception
     */
    void deleteMapping(ICduModel cdu, IUrlModel url);

    /**
     * Gets the court site status.
     *
     * @param localProxy the local proxy
     * @return the court site status
     * @throws RestException the rest exception
     */
    CourtSiteStatusJson getCourtSiteStatus(ILocalProxy localProxy);

    /**
     * Restart cdu.
     *
     * @param localProxy the local proxy
     * @param ipAddresses the ip addresses
     * @throws RestException the rest exception
     */
    void restartCdu(ILocalProxy localProxy, List<String> ipAddresses);

    /**
     * Gets the cdu screenshot.
     *
     * @param localProxy the local proxy
     * @param ipAddress the ip address
     * @return the cdu screenshot
     * @throws RestException the rest exception
     */
    byte[] getCduScreenshot(ILocalProxy localProxy, String ipAddress);
}
