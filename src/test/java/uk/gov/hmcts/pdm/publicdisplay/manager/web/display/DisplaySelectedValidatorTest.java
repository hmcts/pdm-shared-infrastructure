package uk.gov.hmcts.pdm.publicdisplay.manager.web.display;

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
abstract class DisplaySelectedValidatorTest extends AbstractJUnit {
    private static final String FALSE = "False";
    protected DisplaySelectedValidator classUnderTest;
    protected DisplayPageStateHolder mockDisplayPageStateHolder;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new DisplaySelectedValidator();

        // Setup the mock version of the called classes
        mockDisplayPageStateHolder = createMock(DisplayPageStateHolder.class);

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "displayPageStateHolder", mockDisplayPageStateHolder);
    }

    protected List<XhibitCourtSiteDto> createCourtSiteDtoList() {
        XhibitCourtSiteDto xhibitCourtSiteDto = new XhibitCourtSiteDto();
        xhibitCourtSiteDto.setId(8L);
        xhibitCourtSiteDto.setCourtId(10);
        return List.of(xhibitCourtSiteDto);
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(DisplaySearchCommand.class);
        assertTrue(result, FALSE);
    }

    @Test
    void testGetCourtPageStateHolder() {
        DisplayPageStateHolder displayPageStateHolder = classUnderTest.getDisplayPageStateHolder();

        assertInstanceOf(DisplayPageStateHolder.class, displayPageStateHolder, "Not an Instance");
    }

    @Test
    void validateCourtIdTest() {
        DisplaySearchCommand displaySearchCommand = new DisplaySearchCommand();
        displaySearchCommand.setXhibitCourtSiteId(null);
        final BindingResult errors = new BeanPropertyBindingResult(displaySearchCommand, "displaySelectedValidator");

        classUnderTest.validate(displaySearchCommand, errors);

        assertEquals("displaySearchCommand.xhibitCourtSiteId.notNull", errors.getGlobalErrors().get(0).getCode(),
                "Not equal");

    }
}