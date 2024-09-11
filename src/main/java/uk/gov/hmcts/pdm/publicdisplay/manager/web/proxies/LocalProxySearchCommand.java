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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.proxies;

import jakarta.validation.constraints.NotNull;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptedFormat;

import java.io.Serializable;

/**
 * LocalProxySearchCommand.
 * 
 * @author scullionm
 *
 */

public class LocalProxySearchCommand implements Serializable {

    /**
     * SerialVersionUID of the LocalProxySearchCommand class.
     */
    private static final long serialVersionUID = 7519083759201767559L;
    private static final String XHIBITCOURTSITEID = "XhibitCourtSiteId :";
    private static final String NOVALUEPRESENT = "no value at present";
    
    /**
     * The ID of the CourtSite.
     */
    @EncryptedFormat
    @NotNull(message = "{localProxySearchCommand.xhibitCourtSiteId.notNull}")
    private Long xhibitCourtSiteId;

    /**
     * getXhibitCourtSiteId.
     * 
     * @return the xhibitCourtSiteId
     */
    public Long getXhibitCourtSiteId() {
        return xhibitCourtSiteId;
    }

    /**
     * setXhibitCourtSiteId.
     * 
     * @param xhibitCourtSiteId the xhibitCourtSiteId to set.
     */
    public void setXhibitCourtSiteId(final Long xhibitCourtSiteId) {
        this.xhibitCourtSiteId = xhibitCourtSiteId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(30);

        // Use spring StringUtils to check strings have values
        sb.append(XHIBITCOURTSITEID)
            .append((this.getXhibitCourtSiteId() == null) ? NOVALUEPRESENT
                : this.getXhibitCourtSiteId());
        return sb.toString();
    }

}
