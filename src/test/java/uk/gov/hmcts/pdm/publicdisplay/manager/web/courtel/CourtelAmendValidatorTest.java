package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CourtelAmendValidatorTest extends AbstractJUnit {

    private static final String NOT_EQUAL = "Not equal";
    private static final String FALSE = "False";
    private static final String COURTEL_AMEND_VALIDATOR = "courtelAmendValidator";
    private CourtelAmendValidator classUnderTest;
    private CourtelAmendCommand courtelAmendCommand;

    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new CourtelAmendValidator();
        courtelAmendCommand = new CourtelAmendCommand();
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(CourtelAmendCommand.class);
        assertTrue(result, FALSE);
    }

    @Test
    void validateSuccessTest() {
        courtelAmendCommand.setCourtelListAmount("5");
        courtelAmendCommand.setCourtelMaxRetry("10");
        courtelAmendCommand.setMessageLookupDelay("15");
        final BindingResult errors = new BeanPropertyBindingResult(courtelAmendCommand, COURTEL_AMEND_VALIDATOR);

        classUnderTest.validate(courtelAmendCommand, errors);

        assertFalse(errors.hasErrors(), "Not false");
    }

    @Test
    void validateCourtelListAmountTest() {
        courtelAmendCommand.setCourtelListAmount("s");
        final BindingResult errors = new BeanPropertyBindingResult(courtelAmendCommand, COURTEL_AMEND_VALIDATOR);

        classUnderTest.validate(courtelAmendCommand, errors);

        assertEquals("courtelAmendCommand.courtelListAmount.notNumber", errors.getGlobalErrors().get(0).getCode(),
                NOT_EQUAL);
    }

    @Test
    void validateCourtelListAmountNullTest() {
        courtelAmendCommand.setCourtelListAmount(null);
        final BindingResult errors = new BeanPropertyBindingResult(courtelAmendCommand, COURTEL_AMEND_VALIDATOR);

        classUnderTest.validate(courtelAmendCommand, errors);

        assertEquals("courtelAmendCommand.courtelListAmount.notNumber", errors.getGlobalErrors().get(0).getCode(),
                NOT_EQUAL);
    }

    @Test
    void validateCourtelMaxRetryNullTest() {

        courtelAmendCommand.setCourtelListAmount("5");
        courtelAmendCommand.setCourtelMaxRetry(null);
        final BindingResult errors = new BeanPropertyBindingResult(courtelAmendCommand, COURTEL_AMEND_VALIDATOR);

        classUnderTest.validate(courtelAmendCommand, errors);

        assertEquals("courtelAmendCommand.courtelMaxRetry.notNumber", errors.getGlobalErrors().get(0).getCode(),
                NOT_EQUAL);
    }

    @Test
    void validateCourtelMaxRetryTest() {

        courtelAmendCommand.setCourtelListAmount("5");
        courtelAmendCommand.setCourtelMaxRetry("s");
        final BindingResult errors = new BeanPropertyBindingResult(courtelAmendCommand, COURTEL_AMEND_VALIDATOR);

        classUnderTest.validate(courtelAmendCommand, errors);

        assertEquals("courtelAmendCommand.courtelMaxRetry.notNumber", errors.getGlobalErrors().get(0).getCode(),
                NOT_EQUAL);
    }

    @Test
    void validateMessageLookupDelayNullTest() {
        courtelAmendCommand.setCourtelListAmount("5");
        courtelAmendCommand.setCourtelMaxRetry("10");
        courtelAmendCommand.setMessageLookupDelay(null);
        final BindingResult errors = new BeanPropertyBindingResult(courtelAmendCommand, COURTEL_AMEND_VALIDATOR);

        classUnderTest.validate(courtelAmendCommand, errors);

        assertEquals("courtelAmendCommand.messageLookupDelay.notNumber",
                errors.getGlobalErrors().get(0).getCode(), NOT_EQUAL);
    }

    @Test
    void validateMessageLookupDelayTest() {
        courtelAmendCommand.setCourtelListAmount("5");
        courtelAmendCommand.setCourtelMaxRetry("10");
        courtelAmendCommand.setMessageLookupDelay("k");
        final BindingResult errors = new BeanPropertyBindingResult(courtelAmendCommand, COURTEL_AMEND_VALIDATOR);

        classUnderTest.validate(courtelAmendCommand, errors);

        assertEquals("courtelAmendCommand.messageLookupDelay.notNumber",
                errors.getGlobalErrors().get(0).getCode(), NOT_EQUAL);
    }

}