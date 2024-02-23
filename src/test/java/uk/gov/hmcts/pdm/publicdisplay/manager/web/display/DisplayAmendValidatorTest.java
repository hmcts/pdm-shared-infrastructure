package uk.gov.hmcts.pdm.publicdisplay.manager.web.display;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(EasyMockExtension.class)
class DisplayAmendValidatorTest extends AbstractJUnit {

    private static final String NOT_FALSE = "Not false";
    private static final String DISPLAY_AMEND_VALIDATOR = "displayAmendValidator";
    private DisplayAmendValidator classUnderTest;

    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new DisplayAmendValidator();
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(DisplayAmendCommand.class);
        assertTrue(result, "Not true");
    }

    @Test
    void validateDisplayIdTest() {
        DisplayAmendCommand displayAmendCommand = new DisplayAmendCommand();
        displayAmendCommand.setDisplayId(null);

        final BindingResult errors = new BeanPropertyBindingResult(displayAmendCommand, DISPLAY_AMEND_VALIDATOR);

        classUnderTest.validate(displayAmendCommand, errors);

        assertEquals("displayAmendCommand.display.notNull", errors.getGlobalErrors().get(0).getCode(), NOT_FALSE);
    }

    @Test
    void validateDisplayTypeIdTest() {
        DisplayAmendCommand displayAmendCommand = new DisplayAmendCommand();
        displayAmendCommand.setDisplayId(2);
        displayAmendCommand.setDisplayTypeId(null);

        final BindingResult errors = new BeanPropertyBindingResult(displayAmendCommand, DISPLAY_AMEND_VALIDATOR);

        classUnderTest.validate(displayAmendCommand, errors);

        assertEquals("displayAmendCommand.displayTypeId.notNull", errors.getGlobalErrors().get(0).getCode(), NOT_FALSE);
    }

    @Test
    void validateCourtSiteIdTest() {
        DisplayAmendCommand displayAmendCommand = new DisplayAmendCommand();
        displayAmendCommand.setDisplayId(2);
        displayAmendCommand.setDisplayTypeId(3);
        displayAmendCommand.setXhibitCourtSiteId(null);

        final BindingResult errors = new BeanPropertyBindingResult(displayAmendCommand, DISPLAY_AMEND_VALIDATOR);

        classUnderTest.validate(displayAmendCommand, errors);

        assertEquals("displayAmendCommand.xhibitCourtSiteId.notNull", errors.getGlobalErrors().get(0).getCode(),
                NOT_FALSE);
    }

    @Test
    void validateRotationSetIdTest() {
        DisplayAmendCommand displayAmendCommand = new DisplayAmendCommand();
        displayAmendCommand.setDisplayId(2);
        displayAmendCommand.setDisplayTypeId(3);
        displayAmendCommand.setXhibitCourtSiteId(4L);

        final BindingResult errors = new BeanPropertyBindingResult(displayAmendCommand, DISPLAY_AMEND_VALIDATOR);

        classUnderTest.validate(displayAmendCommand, errors);

        assertEquals("displayAmendCommand.rotationSetId.notNull", errors.getGlobalErrors().get(0).getCode(), NOT_FALSE);
    }
}