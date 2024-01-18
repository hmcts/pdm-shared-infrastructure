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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.hearing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;

import java.util.List;


/**
 * The Class AbstractHearingTypeValidator.
 * 
 * @author gittinsl
 *
 */
public abstract class AbstractHearingTypeValidator implements Validator {

    /** The hearing page state holder. */
    @Autowired
    private HearingTypePageStateHolder hearingTypePageStateHolder;
    private final Character yesChar = AppConstants.YES_CHAR;

    /**
     * Gets the hearing type page state holder.
     *
     * @return the hearing type page state holder
     */
    protected HearingTypePageStateHolder getHearingTypePageStateHolder() {
        return hearingTypePageStateHolder;
    }

    /**
     * Xhibit court site id in the valid list of sites.
     *
     * @param xhibitCourtSiteId the xhibit court site id
     * @return true, if successful
     */
    protected boolean isCourtSiteValid(final Long xhibitCourtSiteId) {
        final XhibitCourtSiteDto selectedCourtSite =
            getCourtSiteFromSearchResults(xhibitCourtSiteId);
        return selectedCourtSite != null;
    }

    /**
     * Checks if is registered court site selected.
     *
     * @param xhibitCourtSiteId the xhibit court site id
     * @return true, if is registered court site selected
     */
    protected boolean isRegisteredCourtSiteSelected(final Long xhibitCourtSiteId) {
        final XhibitCourtSiteDto selectedCourtSite =
            getCourtSiteFromSearchResults(xhibitCourtSiteId);
        return isRegisteredCourtSite(selectedCourtSite);
    }

    /**
     * Checks if is registered court site.
     *
     * @param selectedCourtSite the selected court site
     * @return true, if is registered court site
     */
    protected boolean isRegisteredCourtSite(final XhibitCourtSiteDto selectedCourtSite) {
        return selectedCourtSite != null
            && yesChar.equals(selectedCourtSite.getRegisteredIndicator());
    }

    /**
     * Gets the court site from search results.
     *
     * @param xhibitCourtSiteId the xhibit court site id
     * @return the court site from search results
     */
    private XhibitCourtSiteDto getCourtSiteFromSearchResults(final Long xhibitCourtSiteId) {
        XhibitCourtSiteDto selectedCourtSite = null;
        final List<XhibitCourtSiteDto> courtSiteList = hearingTypePageStateHolder.getSites();
        if (courtSiteList != null) {
            for (XhibitCourtSiteDto courtSite : courtSiteList) {
                if (courtSite.getId().equals(xhibitCourtSiteId)) {
                    selectedCourtSite = courtSite;
                    break;
                }
            }
        }
        return selectedCourtSite;
    }

}
