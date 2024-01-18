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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.cdus;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Locale;


/**
 * The register CDU Command object.
 * 
 * @author harrism
 *
 */
public class CduRegisterCommand extends AbstractCduCommand {
    /**
     * Maximum length for Cdu Number field.
     */
    private static final int CDUNUMBER_MAX_LENGTH = 7;

    /** The Cdu Number. */
    @NotBlank(message = "{cduCommand.cduNumber.notBlank}")
    @Length(max = CDUNUMBER_MAX_LENGTH, message = "{cduCommand.cduNumber.tooLong}")
    @Pattern(regexp = "CDU_?[0-9]{1,4}", message = "{cduCommand.cduNumber.invalid}")
    private String cduNumber;

    /**
     * Gets the cdu number.
     *
     * @return the cdu number
     */
    public String getCduNumber() {
        return cduNumber;
    }

    /**
     * Sets the cdu number and ensure it is in uppercases.
     *
     * @param cduNumber the new cdu number
     */
    public void setCduNumber(final String cduNumber) {
        this.cduNumber = cduNumber != null ? cduNumber.toUpperCase(Locale.UK) : "";
    }

}
