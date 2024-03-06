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
import uk.gov.hmcts.pdm.mockipdmanager.common.json.MappingJson;
import uk.gov.hmcts.pdm.mockipdmanager.common.json.UrlJson;

/**
 * The Class SyncDataController.
 *
 * @author toftn
 */
@RestController
@RequestMapping("/syncdata")
public class SyncDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncDataController.class);
    private static final String METHOD = "Method ";
    private static final String THREE_PARAMS = "{} {} {}";
    private static final String FOUR_PARAMS = "{} {} {} {}";
    private static final String FIVE_PARAMS = "{} {} {} {} {}";
    private static final String STARTS = " - starts";
    private static final String ENDS = " - ends";
    private static final String HOSTNAME = "MOCKIPDMANAGER";
    private static final String SAVING_CDU = "Saving CDU:";
    private static final String SAVING_LOCAL_PROXY = "Saving Local Proxy:";

    /**
     * Save cdu.
     *
     * @param cduJson the cdu json
     * @return the response
     */
    @RequestMapping(value = "/cdu/save", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity saveCdu(@RequestBody final CduJson cduJson) {
        final String methodName = "saveCdu";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        LOGGER.info(FOUR_PARAMS, SAVING_CDU, cduJson.getCduNumber(), cduJson.getDescription(), 
            cduJson.getMacAddress());
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return ResponseEntity.ok(null);
    }

    /**
     * Delete cdu.
     *
     * @param cduJson the cdu json
     * @return the response
     */
    @RequestMapping(value = "/cdu/delete", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity deleteCdu(@RequestBody final CduJson cduJson) {
        final String methodName = "deleteCdu";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        LOGGER.info("Deleting CDU: {}", cduJson.getMacAddress());
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return ResponseEntity.ok(null);
    }

    /**
     * Save url.
     *
     * @param urlJson the url json
     * @return JSON. If this is as simple as success fail still send JSON for consistency.
     */
    @RequestMapping(value = "/url/save", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity saveUrl(@RequestBody final UrlJson urlJson) {
        final String methodName = "saveUrl";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        LOGGER.info("Saving URL: {} {}", urlJson.getDescription(), urlJson.getUrl());
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return ResponseEntity.ok(null);
    }

    /**
     * Save mapping.
     *
     * @param mappingJson the mapping json
     * @return the response
     */
    @RequestMapping(value = "/mapping/save", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity saveMapping(@RequestBody final MappingJson mappingJson) {
        final String methodName = "saveMapping";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        LOGGER.info("Saving Mapping: {} {}", mappingJson.getUniqueUrlId(), mappingJson.getMacAddress());
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return ResponseEntity.ok(null);
    }

    /**
     * Delete mapping.
     *
     * @param mappingJson the mapping json
     * @return the response
     */
    @RequestMapping(value = "/mapping/delete", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity deleteMapping(@RequestBody final MappingJson mappingJson) {
        final String methodName = "deleteMapping";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        LOGGER.info("Deleting Mapping: {} {}", mappingJson.getUniqueUrlId(), mappingJson.getMacAddress());
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return ResponseEntity.ok(null);
    }

    /**
     * Delete url.
     *
     * @param urlJson the url json
     * @return the response
     */
    @RequestMapping(value = "/url/delete", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity deleteUrl(@RequestBody final UrlJson urlJson) {
        final String methodName = "deleteUrl";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        LOGGER.info("Deleting URL: {}", urlJson.getUniqueUrlId());
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return ResponseEntity.ok(null);
    }

    /**
     * Save site.
     *
     * @param courtSiteJson the court site json
     * @return JSON. If this is as simple as success fail still send JSON for consisteny
     */
    @RequestMapping(value = "/site/save", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity saveSite(@RequestBody final CourtSiteJson courtSiteJson) {
        final String methodName = "saveSite";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS); 
        LOGGER.info("Setting Hostname: {}", HOSTNAME);
        LOGGER.info(FIVE_PARAMS, SAVING_LOCAL_PROXY, courtSiteJson.getSiteId(), courtSiteJson.getTitle(), 
            courtSiteJson.getPageUrl(), courtSiteJson.getPowersaveSchedule());
        courtSiteJson.setHostName(HOSTNAME);
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return ResponseEntity.ok(courtSiteJson);
    }

    /**
     * Delete site.
     *
     * @param courtSiteJson the court site json
     * @return the response
     */
    @RequestMapping(value = "/site/delete", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON)
    public ResponseEntity deleteSite(@RequestBody final CourtSiteJson courtSiteJson) {
        final String methodName = "deleteSite";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);
        LOGGER.info("Deleting Local Proxy: {}", courtSiteJson.getSiteId());
        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return ResponseEntity.ok(null);
    }

}
