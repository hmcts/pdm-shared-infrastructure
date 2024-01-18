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

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The Class CduAmendCommandTest.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
class CduAmendCommandTest extends AbstractJUnit {
    /** The title max length. */
    private int titleMaxLength;

    /** The description max length. */
    private int descriptionMaxLength;

    /** The notification max length. */
    private int notificationMaxLength;

    /** The class under test. */
    private CduAmendCommand classUnderTest;

    private ValidatorFactory validatorFactory;

    /** The validator used to test. */
    private Validator validator;

    private static final String NOT_EQUAL = "Not equal";

    private static final String FALSE = "False";


    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test (CduRegisterCommand)
        classUnderTest = new CduAmendCommand();

        // Get the valid length of the variables
        titleMaxLength = (Integer) ReflectionTestUtils.getField(classUnderTest, "TITLE_MAX_LENGTH");
        descriptionMaxLength =
            (Integer) ReflectionTestUtils.getField(classUnderTest, "DESCRIPTION_MAX_LENGTH");
        notificationMaxLength =
            (Integer) ReflectionTestUtils.getField(classUnderTest, "NOTIFICATION_MAX_LENGTH");
        int locationMaxLength =
            (Integer) ReflectionTestUtils.getField(classUnderTest, "LOCATION_MAX_LENGTH");

        // Set all mandatory fields with valid values to pass all validations initially
        classUnderTest.setTitle(getTestString(titleMaxLength));
        classUnderTest.setLocation(getTestString(locationMaxLength));
        classUnderTest.setRefresh(30L);
        classUnderTest.setWeighting(1L);
        classUnderTest.setOfflineIndicator(AppConstants.NO_CHAR);

        // Create validator for testing annotations
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    /**
     * TearDown.
     */
    @AfterEach
    public void tearDown() {
        validatorFactory.close();

    }

    /**
     * Test valid.
     */
    @Test
    void testValid() {
        // Perform the test
        final Set<ConstraintViolation<CduAmendCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(0, violations.size(), NOT_EQUAL);
    }

    /**
     * Test offline indicator blank.
     */
    @Test
    void testOfflineIndicatorBlank() {
        // Setup test
        classUnderTest.setOfflineIndicator(null);

        // Perform the test
        final Set<ConstraintViolation<CduAmendCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertTrue(containsViolation(violations, "{cduCommand.offlineIndicator.notBlank}"), FALSE);
    }

    /**
     * Test offline indicator invalid.
     */
    @Test
    void testOfflineIndicatorInvalid() {
        // Setup test
        classUnderTest.setOfflineIndicator('X');

        // Perform the test
        final Set<ConstraintViolation<CduAmendCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test all valid.
     */
    @Test
    void testAllValid() {
        // Setup test
        classUnderTest.setTitle(getTestString(titleMaxLength));
        classUnderTest.setDescription(getTestString(descriptionMaxLength));
        classUnderTest.setNotification(getTestString(notificationMaxLength));
        classUnderTest.setRefresh(1L);
        classUnderTest.setWeighting(2L);
        classUnderTest.setOfflineIndicator(AppConstants.YES_CHAR);

        // Perform the test
        final Set<ConstraintViolation<CduAmendCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(0, violations.size(), NOT_EQUAL);
    }

    /**
     * Gets the test string.
     *
     * @param length the length
     * @return the test string
     */
    private String getTestString(final int length) {
        final String testString = "1234567890";
        final int remainder = length % testString.length();
        StringBuilder stringBuilder = new StringBuilder("");

        // Build the string using the entire string length
        for (int i = 0; i < (length / testString.length()); i++) {
            stringBuilder.append(testString);
        }
        // Add in any remainder parts of the string to make up the passed in length
        if (remainder != 0) {
            stringBuilder.append(testString.substring(0, remainder));
        }
        return stringBuilder.toString();
    }

    /**
     * Checks violations contain a specific violation.
     *
     * @return boolean
     */
    private boolean containsViolation(Set<ConstraintViolation<CduAmendCommand>> violations,
        String violationMsg) {
        if (!violations.isEmpty()) {
            for (ConstraintViolation<CduAmendCommand> violation : violations) {
                if (violationMsg.equals(violation.getMessage())) {
                    return true;
                }
            }
        }
        return false;
    }
}
