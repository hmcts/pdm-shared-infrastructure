package uk.gov.hmcts.pdm.publicdisplay.manager.web.judge;

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
abstract class JudgeSelectedValidatorTest extends AbstractJUnit {

    private static final String FALSE = "False";
    protected JudgeSelectedValidator classUnderTest;
    protected JudgePageStateHolder mockJudgePageStateHolder;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new JudgeSelectedValidator();

        // Setup the mock version of the called classes
        mockJudgePageStateHolder = createMock(JudgePageStateHolder.class);

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "judgePageStateHolder", mockJudgePageStateHolder);
    }

    protected List<XhibitCourtSiteDto> createCourtSiteDtoList() {
        XhibitCourtSiteDto xhibitCourtSiteDto = new XhibitCourtSiteDto();
        xhibitCourtSiteDto.setId(8L);
        xhibitCourtSiteDto.setCourtId(10);
        return List.of(xhibitCourtSiteDto);
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(JudgeSearchCommand.class);
        assertTrue(result, FALSE);
    }

    @Test
    void testGetCourtPageStateHolder() {
        JudgePageStateHolder judgePageStateHolder = classUnderTest.getJudgePageStateHolder();

        assertInstanceOf(JudgePageStateHolder.class, judgePageStateHolder, "Not an Instance");
    }

    @Test
    void validateCourtIdTest() {
        JudgeSearchCommand judgeSearchCommand = new JudgeSearchCommand();
        judgeSearchCommand.setXhibitCourtSiteId(null);
        final BindingResult errors = new BeanPropertyBindingResult(judgeSearchCommand, "judgeSelectedValidator");

        classUnderTest.validate(judgeSearchCommand, errors);

        assertEquals("judgeSearchCommand.xhibitCourtSiteId.notNull", errors.getGlobalErrors().get(0).getCode(), "Not "
                + "equal");

    }
}
