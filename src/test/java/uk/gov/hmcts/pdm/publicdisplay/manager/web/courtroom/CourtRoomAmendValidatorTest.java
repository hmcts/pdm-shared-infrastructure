package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

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
class CourtRoomAmendValidatorTest extends AbstractJUnit {

    private static final String NOT_EQUAL = "Not equal";
    private static final String FALSE = "False";
    private static final String COURTROOM_AMEND_VALIDATOR = "courtRoomAmendValidator";
    private CourtRoomAmendValidator classUnderTest;
    private CourtRoomAmendCommand courtRoomAmendCommand;

    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new CourtRoomAmendValidator();
        courtRoomAmendCommand = new CourtRoomAmendCommand();
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(CourtRoomDeleteCommand.class);
        assertTrue(result, FALSE);
    }

    @Test
    void validateSuccessTest() {
        courtRoomAmendCommand.setXhibitCourtSiteId(1L);
        courtRoomAmendCommand.setCourtRoomId(1);
        courtRoomAmendCommand.setName("A name");
        courtRoomAmendCommand.setDescription("A description");
        final BindingResult errors = new BeanPropertyBindingResult(courtRoomAmendCommand, COURTROOM_AMEND_VALIDATOR);

        classUnderTest.validate(courtRoomAmendCommand, errors);

        assertFalse(errors.hasErrors(), "Not false");
    }

    @Test
    void validateCourtSiteNameTest() {
        courtRoomAmendCommand.setXhibitCourtSiteId(null);
        final BindingResult errors = new BeanPropertyBindingResult(courtRoomAmendCommand, COURTROOM_AMEND_VALIDATOR);

        classUnderTest.validate(courtRoomAmendCommand, errors);

        assertEquals("courtRoomAmendCommand.xhibitCourtSiteId.notNull", errors.getGlobalErrors().get(0).getCode(),
                NOT_EQUAL);
    }

    @Test
    void validateCourtRoomIdTest() {

        courtRoomAmendCommand.setXhibitCourtSiteId(1L);
        courtRoomAmendCommand.setCourtRoomId(null);
        final BindingResult errors = new BeanPropertyBindingResult(courtRoomAmendCommand, COURTROOM_AMEND_VALIDATOR);

        classUnderTest.validate(courtRoomAmendCommand, errors);

        assertEquals("courtRoomAmendCommand.courtRoomId.notNull", errors.getGlobalErrors().get(0).getCode(), NOT_EQUAL);
    }

    @Test
    void validateCourtRoomNameTest() {
        courtRoomAmendCommand.setXhibitCourtSiteId(1L);
        courtRoomAmendCommand.setCourtRoomId(1);
        courtRoomAmendCommand.setName(null);
        final BindingResult errors = new BeanPropertyBindingResult(courtRoomAmendCommand, COURTROOM_AMEND_VALIDATOR);

        classUnderTest.validate(courtRoomAmendCommand, errors);

        assertEquals("courtRoomAmendCommand.name.notBlank", errors.getGlobalErrors().get(0).getCode(), NOT_EQUAL);
    }

    @Test
    void validateCourtRoomDescriptionTest() {
        courtRoomAmendCommand.setXhibitCourtSiteId(1L);
        courtRoomAmendCommand.setCourtRoomId(1);
        courtRoomAmendCommand.setName("A name");
        courtRoomAmendCommand.setDescription(null);
        final BindingResult errors = new BeanPropertyBindingResult(courtRoomAmendCommand, COURTROOM_AMEND_VALIDATOR);

        classUnderTest.validate(courtRoomAmendCommand, errors);

        assertEquals("courtRoomAmendCommand.description.notBlank", errors.getGlobalErrors().get(0).getCode(),
                NOT_EQUAL);
    }
}