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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.users;

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
import uk.gov.hmcts.pdm.publicdisplay.manager.security.UserRole;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class UserAddCommandTest.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
class UserAddCommandTest extends AbstractJUnit {

    private static final String NOT_EQUAL = "Not equal";

    /** The user name max length. */
    private int userNameMaxLength;

    /** The class under test. */
    private UserAddCommand classUnderTest;

    /** The validator used to test. */
    private Validator validator;

    private ValidatorFactory validatorFactory;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test (UserAddCommand)
        classUnderTest = new UserAddCommand();

        // Get the valid length of the variables
        userNameMaxLength =
            (Integer) ReflectionTestUtils.getField(classUnderTest, "USERNAME_MAX_LENGTH");

        // Set all mandatory fields with valid values to pass all validations initially
        classUnderTest.setUserName(getTestString(userNameMaxLength));
        classUnderTest.setUserRole(UserRole.ROLE_ADMIN);

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
        final Set<ConstraintViolation<UserAddCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(0, violations.size(), NOT_EQUAL);
    }

    /**
     * Test user name blank.
     */
    @Test
    void testUserNameBlank() {
        // Setup test
        classUnderTest.setUserName(null);

        // Perform the test
        final Set<ConstraintViolation<UserAddCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test user role null.
     */
    @Test
    void testUserRoleNull() {
        // Setup test
        classUnderTest.setUserRole(null);

        // Perform the test
        final Set<ConstraintViolation<UserAddCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test UserName too long.
     */
    @Test
    void testUserNameTooLong() {
        // Setup test
        classUnderTest.setUserName(getTestString(userNameMaxLength + 1));

        // Perform the test
        final Set<ConstraintViolation<UserAddCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test UserName invalid chars at start.
     */
    @Test
    void testUserNameInvalidStart() {
        // Setup test
        classUnderTest.setUserName("%" + getTestString(userNameMaxLength - 1));

        // Perform the test
        final Set<ConstraintViolation<UserAddCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test UserName invalid chars at start.
     */
    @Test
    void testUserNameInvalidEnd() {
        // Setup test
        classUnderTest.setUserName(getTestString(userNameMaxLength - 1) + "%");

        // Perform the test
        final Set<ConstraintViolation<UserAddCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test all valid.
     */
    @Test
    void testAllValid() {
        // Perform the test
        final Set<ConstraintViolation<UserAddCommand>> violations =
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
        final String testString = "_Aa4567890";
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
