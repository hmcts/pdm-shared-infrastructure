package uk.gov.hmcts.pdm.publicdisplay.manager.web.cdus;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * The Class CduSearchValidatorServiceTest.
 *
 * @author boparaij
 */
@ExtendWith(EasyMockExtension.class)
class CduSearchValidatorServiceTest extends CduSearchValidatorTest {

    /**
     * Test validate mac address.
     */
    @Test
    void testMacAddressValid() {
        final CduSearchCommand cduSearchCommand = getTestCduSearchCommandMacAddress();
        final BindingResult errors =
            new BeanPropertyBindingResult(cduSearchCommand, CDU_SEARCH_COMMAND);

        // Perform the test
        classUnderTest.validate(cduSearchCommand, errors);

        // Checking that the search criteria is valid
        assertFalse(errors.hasErrors(), TRUE);
    }

    /**
     * Test invalid mac address.
     */
    @Test
    void testMacAddressInvalid() {
        final CduSearchCommand cduSearchCommand = new CduSearchCommand();
        final BindingResult errors =
            new BeanPropertyBindingResult(cduSearchCommand, CDU_SEARCH_COMMAND);

        // Perform the test
        classUnderTest.validate(cduSearchCommand, errors);

        // Checking that the search criteria is invalid (negative)
        assertEquals(1, errors.getErrorCount(), NOT_EQUAL);
    }

    /**
     * Test is valid positive.
     */
    @Test
    void testMacAddressIsValid() {
        final CduSearchCommand cduSearchCommand = getTestCduSearchCommandMacAddress();
        final BindingResult errors =
            new BeanPropertyBindingResult(cduSearchCommand, CDU_SEARCH_COMMAND);

        // Perform the test
        classUnderTest.isValid(cduSearchCommand);

        // Checking that the search criteria is valid
        assertFalse(errors.hasErrors(), TRUE);
    }

    /**
     * Test is valid negative.
     * 
     */
    @Test
    void testMacAddressIsNull() {
        // Perform the test
        final boolean retVal = classUnderTest.isValid(null);

        // Checking that the search criteria is invalid (negative)
        assertFalse(retVal, TRUE);
    }
}
