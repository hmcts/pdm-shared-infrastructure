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
class CourtRoomDeleteValidatorTest extends AbstractJUnit {

    private static final String COURTROOM_DELETE_VALIDATOR = "courtRoomDeleteValidator";
    private CourtRoomDeleteValidator classUnderTest;
    private CourtRoomDeleteCommand courtRoomDeleteCommand;


    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new CourtRoomDeleteValidator();
        courtRoomDeleteCommand = new CourtRoomDeleteCommand();
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(CourtRoomDeleteCommand.class);
        assertTrue(result, "Not true");
    }

    @Test
    void validateSuccessTest() {
        courtRoomDeleteCommand.setXhibitCourtSiteId(1L);
        courtRoomDeleteCommand.setCourtRoomId(1);
        final BindingResult errors = new BeanPropertyBindingResult(courtRoomDeleteCommand, COURTROOM_DELETE_VALIDATOR);

        classUnderTest.validate(courtRoomDeleteCommand, errors);

        assertFalse(errors.hasErrors(), "Not false");
    }

    @Test
    void validateCourtRoomIdTest() {
        courtRoomDeleteCommand.setXhibitCourtSiteId(1L);
        courtRoomDeleteCommand.setCourtRoomId(null);
        final BindingResult errors = new BeanPropertyBindingResult(courtRoomDeleteCommand, COURTROOM_DELETE_VALIDATOR);

        classUnderTest.validate(courtRoomDeleteCommand, errors);

        assertEquals("courtRoomAmendCommand.courtRoomId.notNull", errors.getGlobalErrors().get(0).getCode(),
                "Not " + "equal");
    }

    @Test
    void validateCourtRoomNameTest() {
        courtRoomDeleteCommand.setXhibitCourtSiteId(null);
        final BindingResult errors = new BeanPropertyBindingResult(courtRoomDeleteCommand, COURTROOM_DELETE_VALIDATOR);

        classUnderTest.validate(courtRoomDeleteCommand, errors);

        assertEquals("courtRoomAmendCommand.xhibitCourtSiteId.notNull", errors.getGlobalErrors().get(0).getCode(),
                "Not equal");
    }

}