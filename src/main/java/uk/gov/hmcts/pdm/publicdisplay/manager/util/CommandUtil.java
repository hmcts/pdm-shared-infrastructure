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

package uk.gov.hmcts.pdm.publicdisplay.manager.util;




/**
 * The Class CommandUtil.
 *
 * @author harrism
 *
 */
public final class CommandUtil {
    
    public static final String COURT_ID = "CourtId :";
    public static final String XHIBIT_COURTSITE_ID = "XhibitCourtSiteId :";
    public static final String NOVALUEPRESENT = "no value at present";
    
    /**
     * Private constructor to prevent instantiation.
     */
    private CommandUtil() {

    }

    public static String courtCommandToString(Integer courtId) {
        final StringBuilder sb = new StringBuilder(30);

        // Use spring StringUtils to check strings have values
        sb.append(COURT_ID)
            .append((courtId == null) ? NOVALUEPRESENT
                : courtId);
        return sb.toString();
    }
    
    public static String courtSiteCommandToString(Long xhibitCourtSiteId) {
        final StringBuilder sb = new StringBuilder(30);

        // Use spring StringUtils to check strings have values
        sb.append(XHIBIT_COURTSITE_ID)
            .append((xhibitCourtSiteId == null) ? NOVALUEPRESENT
                : xhibitCourtSiteId);
        return sb.toString();
    }
}
