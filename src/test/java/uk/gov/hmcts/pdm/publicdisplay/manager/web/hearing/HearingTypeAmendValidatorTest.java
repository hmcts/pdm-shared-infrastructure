package uk.gov.hmcts.pdm.publicdisplay.manager.web.hearing;

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
class HearingTypeAmendValidatorTest extends AbstractJUnit {

    private static final String NOT_EQUAL = "Not equal";
    private static final String HEARING_TYPE_AMEND_VALIDATOR = "hearingTypeAmendValidator";
    private HearingTypeAmendValidator classUnderTest;

    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new HearingTypeAmendValidator();
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(HearingTypeAmendCommand.class);
        assertTrue(result, "Not true");
    }

    @Test
    void validateSuccessTest() {
        final HearingTypeAmendCommand hearingTypeAmendCommand = new HearingTypeAmendCommand();
        hearingTypeAmendCommand.setRefHearingTypeId(5);
        hearingTypeAmendCommand.setHearingTypeDesc("A hearingTypeDesc");
        hearingTypeAmendCommand.setCategory("A category");
        final BindingResult errors = new BeanPropertyBindingResult(hearingTypeAmendCommand,
                HEARING_TYPE_AMEND_VALIDATOR);

        classUnderTest.validate(hearingTypeAmendCommand, errors);

        assertFalse(errors.hasErrors(), "Not false");
    }

    @Test
    void validateRefHearingTypeIdTest() {
        final HearingTypeAmendCommand hearingTypeAmendCommand = new HearingTypeAmendCommand();
        hearingTypeAmendCommand.setRefHearingTypeId(null);
        final BindingResult errors = new BeanPropertyBindingResult(hearingTypeAmendCommand,
                HEARING_TYPE_AMEND_VALIDATOR);

        classUnderTest.validate(hearingTypeAmendCommand, errors);

        assertEquals("hearingTypeAmendCommand.refHearingTypeId.notNull", errors.getGlobalErrors().get(0).getCode(),
                NOT_EQUAL);
    }

    @Test
    void validateHearingTypeDescTest() {
        final HearingTypeAmendCommand hearingTypeAmendCommand = new HearingTypeAmendCommand();
        hearingTypeAmendCommand.setRefHearingTypeId(null);
        hearingTypeAmendCommand.setRefHearingTypeId(4);
        final BindingResult errors = new BeanPropertyBindingResult(hearingTypeAmendCommand,
                HEARING_TYPE_AMEND_VALIDATOR);

        classUnderTest.validate(hearingTypeAmendCommand, errors);

        assertEquals("hearingTypeAmendCommand.hearingTypeDesc.notBlank", errors.getGlobalErrors().get(0).getCode(),
                NOT_EQUAL);
    }

    @Test
    void validateCategoryTest() {
        final HearingTypeAmendCommand hearingTypeAmendCommand = new HearingTypeAmendCommand();
        hearingTypeAmendCommand.setRefHearingTypeId(5);
        hearingTypeAmendCommand.setHearingTypeDesc("A hearingTypeDesc");
        hearingTypeAmendCommand.setCategory(null);
        final BindingResult errors = new BeanPropertyBindingResult(hearingTypeAmendCommand,
                HEARING_TYPE_AMEND_VALIDATOR);

        classUnderTest.validate(hearingTypeAmendCommand, errors);

        assertEquals("hearingTypeAmendCommand.category.notBlank", errors.getGlobalErrors().get(0).getCode(), NOT_EQUAL);
    }

}