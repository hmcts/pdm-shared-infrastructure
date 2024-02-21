package uk.gov.hmcts.pdm.publicdisplay.manager.web.judge;

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
class JudgeDeleteValidatorTest extends AbstractJUnit {

    private static final String JUDGE_DELETE_VALIDATOR = "judgeDeleteValidator";
    private JudgeDeleteValidator classUnderTest;
    private JudgeDeleteCommand judgeDeleteCommand;

    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new JudgeDeleteValidator();
        judgeDeleteCommand = new JudgeDeleteCommand();
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(JudgeDeleteCommand.class);
        assertTrue(result, "Not true");
    }

    @Test
    void validateRefJudgeIdTest() {
        judgeDeleteCommand.setRefJudgeId(null);
        final BindingResult errors = new BeanPropertyBindingResult(judgeDeleteCommand, JUDGE_DELETE_VALIDATOR);

        classUnderTest.validate(judgeDeleteCommand, errors);

        assertEquals("judgeDeleteCommand.judgeId.notNull", errors.getGlobalErrors().get(0).getCode(), "Not equal");
    }

    @Test
    void validateSuccessTest() {
        judgeDeleteCommand.setRefJudgeId(2);
        final BindingResult errors = new BeanPropertyBindingResult(judgeDeleteCommand, JUDGE_DELETE_VALIDATOR);

        classUnderTest.validate(judgeDeleteCommand, errors);

        assertFalse(errors.hasErrors(), "Not false");
    }
}