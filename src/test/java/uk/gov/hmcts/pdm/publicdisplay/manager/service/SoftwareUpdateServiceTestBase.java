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

package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class SoftwareUpdateServiceTest.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
abstract class SoftwareUpdateServiceTestBase extends AbstractJUnit {
    /** The Constant TEST_DIRECTORY. */
    protected static final String TEST_DIRECTORY = "target/test-classes";

    /** The Constant EXISING_FILE. */
    protected static final String EXISTING_FILE = "log4j2.properties";

    /** The Constant NONEXISTENT_FILE. */
    protected static final String NONEXISTENT_FILE = "thisFileShouldNeverExist.txt";

    /** The Constant INVALID_FILE. */
    protected static final String INVALID_FILE = NONEXISTENT_FILE + "INVALID";

    protected static final String NOT_EQUAL = "Not equal";

    protected static final String NULL = "Null";

    protected static final String NOT_NULL = "Not null";

    /** The class under test. */
    protected SoftwareUpdateService classUnderTest;

    /** The test filenames. */
    protected final List<String> testFilenames = getTestFilenames();

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new SoftwareUpdateService();

        // Get the variables from the class under test
        ReflectionTestUtils.setField(classUnderTest, "updateDirectory", TEST_DIRECTORY);
        ReflectionTestUtils.setField(classUnderTest, "filenames", testFilenames);

        // Initialise the class under tests internal variables
        classUnderTest.initialise();
    }

    /**
     * Gets the test filenames.
     *
     * @return the test filenames
     */
    private List<String> getTestFilenames() {
        final List<String> filenames = new ArrayList<>();
        filenames.add(EXISTING_FILE);
        filenames.add(NONEXISTENT_FILE);
        return filenames;
    }

    /**
     * Perform test checksum.
     *
     * @param filename the filename
     * @param generateChecksums the generate checksums
     * @return the string
     */
    protected String performTestChecksum(final String filename, final boolean generateChecksums) {
        if (generateChecksums) {
            // Generate the checksums which is normally done by a scheduled task
            classUnderTest.checksumFiles();
        }

        // Perform the test
        return classUnderTest.getChecksum(filename);
    }

    /**
     * Assert empty checksum.
     *
     * @param result the result
     */
    protected void assertEmptyChecksum(final String result) {
        assertNotNull(result, NULL);
        assertEquals(0, result.length(), NOT_EQUAL);
    }

    /**
     * Assert generated checksum.
     *
     * @param result the result
     */
    protected void assertGeneratedChecksum(final String result) {
        assertNotNull(result, NULL);
        // assertFalse (result.length () == 0);
    }
}
