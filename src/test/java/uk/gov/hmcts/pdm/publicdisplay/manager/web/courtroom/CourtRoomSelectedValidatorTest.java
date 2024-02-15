package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;

import java.util.List;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(EasyMockExtension.class)
class CourtRoomSelectedValidatorTest extends AbstractJUnit {

    protected CourtRoomSelectedValidator classUnderTest;

    protected CourtRoomPageStateHolder mockcourtRoomPageStateHolder;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new CourtRoomSelectedValidator();

        // Setup the mock version of the called classes
        mockcourtRoomPageStateHolder = createMock(CourtRoomPageStateHolder.class);

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "courtRoomPageStateHolder", mockcourtRoomPageStateHolder);
    }

    protected List<XhibitCourtSiteDto> createCourtSiteDtoList() {
        XhibitCourtSiteDto xhibitCourtSiteDto = new XhibitCourtSiteDto();
        xhibitCourtSiteDto.setId(8L);
        xhibitCourtSiteDto.setCourtId(10);
        return List.of(xhibitCourtSiteDto);
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(CourtRoomSearchCommand.class);
        assertTrue(result, "False");
    }

    @Test
    void testGetCourtPageStateHolder() {
        CourtRoomPageStateHolder courtRoomPageStateHolder = classUnderTest.getCourtRoomPageStateHolder();
        assertInstanceOf(CourtRoomPageStateHolder.class, courtRoomPageStateHolder, "Not an Instance");
    }

    @Test
    void validateCourtIdTest() {
        CourtRoomSearchCommand courtRoomSearchCommand = new CourtRoomSearchCommand();
        courtRoomSearchCommand.setCourtId(null);
        final BindingResult errors = new BeanPropertyBindingResult(courtRoomSearchCommand,
                "courtRoomSelectedValidator");

        classUnderTest.validate(courtRoomSearchCommand, errors);

        assertEquals("courtRoomSearchCommand.courtId.notNull",
                errors.getGlobalErrors().get(0).getCode(), "Not equal");

    }

}