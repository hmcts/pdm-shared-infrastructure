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

package uk.gov.hmcts.pdm.publicdisplay.common.dao;

import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;

/**
 * Sub-class for sorting by a RAG status property. Red is regarded as the highest status, followed
 * by amber and then green.
 *
 * @author harrism
 */
public class RagStatusOrder implements java.util.Comparator<XhibitCourtSiteDto> {

    private static final String RED = AppConstants.RED_CHAR.toString();
    private static final String AMBER = AppConstants.AMBER_CHAR.toString();

    public static int getRagStatusOrder(String ragStatus) {
        if (RED.equalsIgnoreCase(ragStatus)) {
            return 1;
        } else if (AMBER.equalsIgnoreCase(ragStatus)) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public int compare(XhibitCourtSiteDto obj1, XhibitCourtSiteDto obj2) {
        return getRagStatusOrder(obj1.getRagStatus()) - getRagStatusOrder(obj2.getRagStatus());
    }
}
