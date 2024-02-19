package uk.gov.hmcts.pdm.publicdisplay.manager.web.judgetype;

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
abstract class JudgeTypeSelectedValidatorTest extends AbstractJUnit {
    private static final String FALSE = "False";
    protected JudgeTypeSelectedValidator classUnderTest;
    protected JudgeTypePageStateHolder mockJudgeTypePageStateHolder;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new JudgeTypeSelectedValidator();

        // Setup the mock version of the called classes
        mockJudgeTypePageStateHolder = createMock(JudgeTypePageStateHolder.class);

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "judgeTypePageStateHolder", mockJudgeTypePageStateHolder);
    }

    protected List<XhibitCourtSiteDto> createCourtSiteDtoList() {
        XhibitCourtSiteDto xhibitCourtSiteDto = new XhibitCourtSiteDto();
        xhibitCourtSiteDto.setId(8L);
        xhibitCourtSiteDto.setCourtId(10);
        return List.of(xhibitCourtSiteDto);
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(JudgeTypeSearchCommand.class);
        assertTrue(result, FALSE);
    }

    @Test
    void testGetCourtPageStateHolder() {
        JudgeTypePageStateHolder judgeTypePageStateHolder = classUnderTest.getJudgeTypePageStateHolder();

        assertInstanceOf(JudgeTypePageStateHolder.class, judgeTypePageStateHolder, "Not an Instance");
    }

    @Test
    void validateCourtSiteIdTest() {
        JudgeTypeSearchCommand judgeTypeSearchCommand = new JudgeTypeSearchCommand();
        judgeTypeSearchCommand.setXhibitCourtSiteId(null);
        final BindingResult errors = new BeanPropertyBindingResult(judgeTypeSearchCommand,
                "judgeTypeSelectedValidator");

        classUnderTest.validate(judgeTypeSearchCommand, errors);

        assertEquals("judgeTypeSearchCommand.xhibitCourtSiteId.notNull", errors.getGlobalErrors().get(0).getCode(),
                "Not equal");

    }
}