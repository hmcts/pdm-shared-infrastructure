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

import java.util.Set;

import static org.easymock.EasyMock.createMockBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class CduRegisterCommandTest.
 *
 * @author uphillj
 */
@ExtendWith(EasyMockExtension.class)
abstract class TestAbstractCduCommandTest extends AbstractJUnit {

    protected static final String NOT_EQUAL = "Not equal";

    /** The title max length. */
    protected int titleMaxLength;

    /** The description max length. */
    protected int descriptionMaxLength;

    /** The notification max length. */
    protected int notificationMaxLength;

    /** The location max length. */
    protected int locationMaxLength;

    /** The class under test. */
    // CHECKSTYLE:OFF
    protected AbstractCduCommand classUnderTest;
    // CHECKSTYLE:ON

    /** The validator used to test. */
    protected Validator validator;

    protected ValidatorFactory validatorFactory;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test (CduRegisterCommand)
        classUnderTest = createMockBuilder(AbstractCduCommand.class).createMock();

        // Get the valid length of the variables
        titleMaxLength = (Integer) ReflectionTestUtils.getField(classUnderTest, "TITLE_MAX_LENGTH");
        descriptionMaxLength =
            (Integer) ReflectionTestUtils.getField(classUnderTest, "DESCRIPTION_MAX_LENGTH");
        notificationMaxLength =
            (Integer) ReflectionTestUtils.getField(classUnderTest, "NOTIFICATION_MAX_LENGTH");
        locationMaxLength =
            (Integer) ReflectionTestUtils.getField(classUnderTest, "LOCATION_MAX_LENGTH");

        // Set all mandatory fields with valid values to pass all validations initially
        classUnderTest.setTitle(getTestString(titleMaxLength));
        classUnderTest.setLocation(getTestString(locationMaxLength));
        classUnderTest.setRefresh(30L);
        classUnderTest.setWeighting(1L);

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
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(0, violations.size(), NOT_EQUAL);
    }

    /**
     * Test location blank.
     */
    @Test
    void testLocationBlank() {
        // Setup test
        classUnderTest.setLocation(null);

        // Perform the test
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test refresh blank.
     */
    @Test
    void testRefreshBlank() {
        // Setup test
        classUnderTest.setRefresh(null);

        // Perform the test
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test weighting blank.
     */
    @Test
    void testWeightingBlank() {
        // Setup test
        classUnderTest.setWeighting(null);

        // Perform the test
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test weighting invalid value.
     */
    @Test
    void testWeightingInvalidValue() {
        // Setup test
        classUnderTest.setWeighting(3L);

        // Perform the test
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test refresh invalid value zero.
     */
    @Test
    void testRefreshInvalidValueZero() {
        // Setup test
        classUnderTest.setRefresh(0L);

        // Perform the test
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test refresh invalid value thirty one.
     */
    @Test
    void testRefreshInvalidValueThirtyOne() {
        // Setup test
        classUnderTest.setRefresh(31L);

        // Perform the test
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Gets the test string.
     *
     * @param length the length
     * @return the test string
     */
    protected String getTestString(final int length) {
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
}
