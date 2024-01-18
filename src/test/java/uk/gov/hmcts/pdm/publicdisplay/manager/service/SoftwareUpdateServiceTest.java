package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The Class SoftwareUpdateServiceTest.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
class SoftwareUpdateServiceTest extends SoftwareUpdateServiceTestBase {

    /**
     * Test get checksum existing file generating a checksum.
     */
    @Test
    void testGetChecksumExistingFileValid() {
        // Perform the test
        final String result = performTestChecksum(EXISTING_FILE, true);

        // Assert that the objects are as expected
        assertGeneratedChecksum(result);
    }

    /**
     * Test get checksum existing file without generating a checksum.
     */
    @Test
    void testGetChecksumExistingFileInvalid() {
        // Perform the test
        final String result = performTestChecksum(EXISTING_FILE, false);

        // Assert that the objects are as expected
        assertEmptyChecksum(result);
    }

    /**
     * Test get checksum non existent file with generating a checksum.
     */
    @Test
    void testGetChecksumNonExistentFileValid() {
        // Perform the test
        final String result = performTestChecksum(NONEXISTENT_FILE, true);

        // Assert that the objects are as expected
        assertEmptyChecksum(result);
    }

    /**
     * Test get checksum non existent file without generating a checksum.
     */
    @Test
    void testGetChecksumNonExistentFileInvalid() {
        // Perform the test
        final String result = performTestChecksum(NONEXISTENT_FILE, false);

        // Assert that the objects are as expected
        assertEmptyChecksum(result);
    }

    /**
     * Test get checksum for a file that is not in the correct directory.
     */
    @Test
    void testGetChecksumFileInvalid() {
        // Perform the test
        final String result = performTestChecksum(INVALID_FILE, true);

        // Assert that the objects are as expected
        assertNull(result, NULL);
    }

    /**
     * Test get file that exists in the file structure.
     */
    @Test
    void testGetFileValid() {
        // Perform the test
        final File result = classUnderTest.getFile(EXISTING_FILE, true);

        // Assert that the objects are as expected
        assertNotNull(result, NULL);
    }

    /**
     * Test get file that does not exist in the file structure.
     */
    @Test
    void testGetFileInvalid() {
        // Perform the test
        final File result = classUnderTest.getFile(NONEXISTENT_FILE, true);

        // Assert that the objects are as expected
        assertNull(result, NOT_NULL);
    }

}
