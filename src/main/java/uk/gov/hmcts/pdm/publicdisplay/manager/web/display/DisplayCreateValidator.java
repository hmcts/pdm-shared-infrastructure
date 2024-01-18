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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.display;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.DisplayDto;

import java.util.List;
import java.util.Locale;

/**
 * The Class DisplayCreateValidator.
 * 
 * @author harrism
 */
@Component
public class DisplayCreateValidator extends AbstractDisplayValidator {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayCreateValidator.class);
    private static final String EMPTY_STRING = "";

    @Autowired
    private MessageSource messageSource;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return DisplayCreateCommand.class.isAssignableFrom(clazz);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {
        throw new IllegalArgumentException("Use other method definition");
    }

    public void validate(final Object command, final Errors errors, List<DisplayDto> displayDtos) {
        LOGGER.info("validate method starts");

        final DisplayCreateCommand displayCreateCommand = (DisplayCreateCommand) command;
        if (displayCreateCommand.getDescriptionCode() == null
            || EMPTY_STRING.equals(displayCreateCommand.getDescriptionCode())) {
            LOGGER.warn("validate method - Invalid description");
            errors.reject("displayAmendCommand.descriptionCode.notBlank");
        } else if (alreadyExists(displayDtos, displayCreateCommand.getDescriptionCode())) {
            LOGGER.warn("validate method - Location Description already exists");
            String message = messageSource.getMessage("displayCreateCommand.descriptionCode.exists",
                null, Locale.getDefault());
            errors.rejectValue("descriptionCode", null, message);
        } else if (displayCreateCommand.getDisplayTypeId() == null) {
            LOGGER.warn("validate method - Invalid display type");
            errors.reject("displayAmendCommand.displayTypeId.notNull");
        } else if (displayCreateCommand.getXhibitCourtSiteId() == null) {
            LOGGER.warn("validate method - Invalid court site");
            errors.reject("displayAmendCommand.xhibitCourtSiteId.notNull");
        } else if (displayCreateCommand.getRotationSetId() == null) {
            LOGGER.warn("validate method - Invalid rotation set");
            errors.reject("displayAmendCommand.rotationSetId.notNull");
        }
        LOGGER.info("validate method ends");
    }

    private boolean alreadyExists(List<DisplayDto> displayDtos, String descriptionCode) {
        for (DisplayDto displayDto : displayDtos) {
            if (displayDto.getDescriptionCode().equalsIgnoreCase(descriptionCode)) {
                return true;
            }
        }
        return false;
    }
}
