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
import uk.gov.hmcts.pdm.mockipdmanager.common.json.CduJson;
import uk.gov.hmcts.pdm.mockipdmanager.common.json.CourtSiteJson;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ListCduController.
 * 
 * @author toftn
 */
@RestController
public class ListCduController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListCduController.class);
    private static final String METHOD = "Method ";
    private static final String THREE_PARAMS = "{} {} {}";
    private static final String FIVE_PARAMS = "{} {} {} {} {}";
    private static final String SEVEN_PARAMS = "{} {} {} {} {} {} {}";
    private static final String STARTS = " - starts";
    private static final String ENDS = " - ends";
    private static final String GENERATED_BY = "XHIBIT";
    private static final String TITLE = "FAKE_TITLE";
    private static final String NOTIFICATION = "FAKE NOTIFICATION";

    /**
     * Gets the cdu list.
     *
     * @param courtSiteJson the court site json
     * @return the cdu list
     */
    @RequestMapping(value = "/listcdu", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity getCduList(@RequestBody final CourtSiteJson courtSiteJson) {
        final String methodName = "getCduList";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        LOGGER.info("Listing CDUS for: {}", courtSiteJson.getSiteId());
        final List<CduJson> cduList = getFakeCduList();
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return ResponseEntity.ok(cduList);
    }
    
    /**
     * Gets a fake cdu list.
     * @return the fake cdu list
     */
    private List<CduJson> getFakeCduList() {
        final String methodName = "getFakeCduList";
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, STARTS);
        final List<CduJson> cduList = new ArrayList<>();

        CduJson cduJson1 = createCduJson("CDU_100", "10:00:00:00:00:01", TITLE,
            "MOCK COURT", "MOCKCDU1", NOTIFICATION, Long.valueOf(123));
        populateCduJson(cduJson1, 'N', 'G', Long.valueOf(1), GENERATED_BY, 'Y');

        CduJson cduJson2 = createCduJson("CDU_101", "10:00:00:00:00:02", TITLE,
            "THE MOCK ROOM", "MOCKCDU2", NOTIFICATION, Long.valueOf(123));
        populateCduJson(cduJson2, 'N', 'G', Long.valueOf(1), GENERATED_BY, 'Y');

        CduJson cduJson3 = createCduJson("CDU_102", "10:00:00:00:00:03", TITLE,
            "THE MOCK KITCHEN", "MOCKCDU3", NOTIFICATION, Long.valueOf(123));
        populateCduJson(cduJson3, 'N', 'G', Long.valueOf(1), GENERATED_BY, 'Y');
        
        CduJson cduJson4 = createCduJson("CDU_103", "10:00:00:00:00:04", TITLE,
            "THE MOCK AMBER", "MOCKAMBERCDU", NOTIFICATION, Long.valueOf(123));
        populateCduJson(cduJson4, 'N', 'G', Long.valueOf(1), GENERATED_BY, 'Y');
        
        CduJson cduJson5 = createCduJson("CDU_104", "10:00:00:00:00:05", TITLE,
            "THE MOCK RED", "MOCKREDCDU", NOTIFICATION, Long.valueOf(123));
        populateCduJson(cduJson5, 'N', 'G', Long.valueOf(1), GENERATED_BY, 'Y');

        cduList.add(cduJson1);
        cduList.add(cduJson2);
        cduList.add(cduJson3);
        cduList.add(cduJson4);
        cduList.add(cduJson5);

        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ENDS);
        return cduList;
    }
    
    /**
     * Populates the cdu JSON object.
     * @param cduNumber - the cduNumber
     * @param macAddress - the macAddress
     * @param title - the title
     * @param description - the description
     * @param location - the location
     * @param notification - the notification
     * @param refresh - the refresh
     * @return the cdu JSON object.
     */
    protected CduJson createCduJson(String cduNumber, String macAddress, String title,
        String description, String location, String notification, Long refresh) {
        final String methodName = "createCduJson";
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, STARTS);
        CduJson cduJson = new CduJson();

        cduJson.setCduNumber(cduNumber);
        cduJson.setMacAddress(macAddress);
        cduJson.setTitle(title);
        cduJson.setDescription(description);
        cduJson.setLocation(location);
        cduJson.setNotification(notification);
        cduJson.setRefresh(refresh);
        LOGGER.info(SEVEN_PARAMS, cduNumber, macAddress, title, 
            description, location, notification, refresh);

        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ENDS);
        return cduJson;
    }
    
    /**
     * Populates the remainder of the cdu JSON object.
     * @param cduJson - the cduJson
     * @param offlineIndicator - the offlineIndicator
     * @param ragStatus - the ragStatus
     * @param siteId - the siteId
     * @param generatedBy - the generatedBy
     * @param registeredIndicator - the registeredIndicator
     */
    protected void populateCduJson(CduJson cduJson, Character offlineIndicator, Character ragStatus,
        Long siteId, String generatedBy,
        Character registeredIndicator) {
        final String methodName = "populateCduJson";
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, STARTS);

        cduJson.setOfflineIndicator(offlineIndicator);
        cduJson.setRagStatus(ragStatus);
        cduJson.setSiteId(siteId);
        cduJson.setGeneratedBy(generatedBy);
        cduJson.setRegisteredIndicator(registeredIndicator);
        LOGGER.info(FIVE_PARAMS, offlineIndicator, ragStatus, siteId, 
            generatedBy, registeredIndicator);
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ENDS);
    }

}
