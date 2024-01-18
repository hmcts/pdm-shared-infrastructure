package uk.gov.hmcts.pdm.publicdisplay.manager.web.cdus;

import jakarta.validation.ConstraintViolation;
import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class CduRegisterCommandTest.
 *
 * @author uphillj
 */
@ExtendWith(EasyMockExtension.class)
class TestAbstractCduCommandLengthTest extends TestAbstractCduCommandTest {

    /**
     * Test title length invalid.
     */
    @Test
    void testTitleLengthInvalid() {
        // Setup test
        classUnderTest.setTitle(getTestString(titleMaxLength + 1));

        // Perform the test
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test description length invalid.
     */
    @Test
    void testDescriptionLengthInvalid() {
        // Setup test
        classUnderTest.setDescription(getTestString(descriptionMaxLength + 1));

        // Perform the test
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test location length invalid.
     */
    @Test
    void testLocationLengthInvalid() {
        // Setup test
        classUnderTest.setLocation(getTestString(locationMaxLength + 1));

        // Perform the test
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

    /**
     * Test notification length invalid.
     */
    @Test
    void testNotificationLengthInvalid() {
        // Setup test
        classUnderTest.setNotification(getTestString(notificationMaxLength + 1));

        // Perform the test
        final Set<ConstraintViolation<AbstractCduCommand>> violations =
            validator.validate(classUnderTest);

        // Check the results
        assertEquals(1, violations.size(), NOT_EQUAL);
    }

}
