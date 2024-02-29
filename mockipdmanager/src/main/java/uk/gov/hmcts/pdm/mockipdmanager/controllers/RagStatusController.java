/* Copyrights and Licenses
 * 
 * Copyright (c) 2015-2016 by the Ministry of Justice. All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
 * - Products derived from this software may not be called "XHIBIT Public Display Manager" nor may
 * "XHIBIT Public Display Manager" appear in their names without prior written permission of the
 * Ministry of Justice.
 * - Redistributions of any form whatsoever must retain the following acknowledgment: "This
 * product includes XHIBIT Public Display Manager."
 * This software is provided "as is" and any expressed or implied warranties, including, but
 * not limited to, the implied warranties of merchantability and fitness for a particular purpose are
 * disclaimed. In no event shall the Ministry of Justice or its contributors be liable for any
 * direct, indirect, incidental, special, exemplary, or consequential damages (including, but
 * not limited to, procurement of substitute goods or services; loss of use, data, or profits;
 * or business interruption). However caused any on any theory of liability, whether in contract,
 * strict liability, or tort (including negligence or otherwise) arising in any way out of the use of this
 * software, even if advised of the possibility of such damage. */

package uk.gov.hmcts.pdm.mockipdmanager.controllers;

import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.pdm.mockipdmanager.common.json.CduStatusJson;
import uk.gov.hmcts.pdm.mockipdmanager.common.json.CourtSiteJson;
import uk.gov.hmcts.pdm.mockipdmanager.common.json.CourtSiteStatusJson;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class RagStatusController.
 *
 * @author toftn
 */
@RestController
public class RagStatusController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RagStatusController.class);
    private static final String METHOD = "Method ";
    private static final String THREE_PARAMS = "{} {} {}";
    private static final String STARTS = " - starts";
    private static final String ENDS = " - ends";
    private static final String GENERATED_BY = "XHIBIT";

    /**
     * Gets the court site status.
     *
     * @param courtSiteJson the court site json
     * @return the court site status
     */
    @RequestMapping(value = "/ragstatus", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity getCourtSiteStatus(@RequestBody final CourtSiteJson courtSiteJson) {
        final String methodName = "getCourtSiteStatus";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        final List<CduStatusJson> cduList = getFakeCduStatusJson();
        final Character localProxyRagstatus = 'G';

        final CourtSiteStatusJson courtSiteStatusJson = new CourtSiteStatusJson();
        courtSiteStatusJson.setRagStatus(localProxyRagstatus);
        courtSiteStatusJson.setCdus(cduList);
        LOGGER.info("Refreshing RAG status for: {}", courtSiteJson.getSiteId());
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return ResponseEntity.ok(courtSiteStatusJson);
    }

    /**
     * Gets a fake cdu status list.
     * @return the fake cdu status list.
     */
    public List<CduStatusJson> getFakeCduStatusJson() {
        final String methodName = "getFakeCduStatusJson";
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, STARTS);
        final List<CduStatusJson> cduList = new ArrayList<>();
        cduList.add(createCduStatusJson(GENERATED_BY, "10:00:00:00:00:01", 'G'));
        cduList.add(createCduStatusJson(GENERATED_BY, "10:00:00:00:00:02", 'G'));
        cduList.add(createCduStatusJson(GENERATED_BY, "10:00:00:00:00:03", 'G'));
        cduList.add(createCduStatusJson(GENERATED_BY, "10:00:00:00:00:04", 'A'));
        cduList.add(createCduStatusJson(GENERATED_BY, "10:00:00:00:00:05", 'R'));

        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ENDS);
        return cduList;
    }

    /**
     * Populates the cdu status JSON object.
     * @param generatedBy - the generatedBy.
     * @param macAddress - the macAddress.
     * @param ragStatus - the ragStatus.
     * @return the cdu status JSON object.
     */
    private CduStatusJson createCduStatusJson(String generatedBy, String macAddress, Character ragStatus) {
        final String methodName = "createCduStatusJson";
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, STARTS);
        CduStatusJson cduStatusJson = new CduStatusJson();
        cduStatusJson.setGeneratedBy(generatedBy);
        cduStatusJson.setMacAddress(macAddress);
        cduStatusJson.setRagStatus(ragStatus);
        LOGGER.info(THREE_PARAMS, generatedBy, macAddress, ragStatus);

        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ENDS);
        return cduStatusJson;
    }

}
