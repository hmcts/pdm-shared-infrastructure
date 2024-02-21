package uk.gov.hmcts.pdm.publicdisplay.manager.web.display;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(EasyMockExtension.class)
class DisplayDeleteValidatorTest extends AbstractJUnit {
    private static final String DISPLAY_DELETE_VALIDATOR = "DisplayDeleteValidator";
    private DisplayDeleteValidator classUnderTest;
    private DisplayDeleteCommand displayDeleteCommand;


    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new DisplayDeleteValidator();
        displayDeleteCommand = new DisplayDeleteCommand();
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(DisplayDeleteCommand.class);
        assertTrue(result, "Not true");
    }

    @Test
    void validateDisplayIdTest() {
        displayDeleteCommand.setDisplayId(null);

        final BindingResult errors = new BeanPropertyBindingResult(displayDeleteCommand, DISPLAY_DELETE_VALIDATOR);

        classUnderTest.validate(displayDeleteCommand, errors);

        assertEquals("displayDeleteCommand.displayId.notNull", errors.getGlobalErrors().get(0).getCode(), "Not equal");
    }

    @Test
    void validateSuccessTest() {
        displayDeleteCommand.setDisplayId(1);

        final BindingResult errors = new BeanPropertyBindingResult(displayDeleteCommand, DISPLAY_DELETE_VALIDATOR);

        classUnderTest.validate(displayDeleteCommand, errors);

        assertFalse(errors.hasErrors(), "Not false");
    }
}