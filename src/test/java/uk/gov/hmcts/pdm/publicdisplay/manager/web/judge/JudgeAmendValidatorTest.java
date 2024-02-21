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
class JudgeAmendValidatorTest extends AbstractJUnit {

    private static final String NOT_FALSE = "Not false";
    private static final String JUDGE_AMEND_VALIDATOR = "judgeAmendValidator";
    private static final String A_FIRST_NAME = "A firstName";
    private static final String A_SURNAME = "A surname";
    private JudgeAmendValidator classUnderTest;

    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new JudgeAmendValidator();
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(JudgeAmendCommand.class);
        assertTrue(result, "Not true");
    }

    @Test
    void validateSuccessTest() {
        final JudgeAmendCommand judgeAmendCommand = new JudgeAmendCommand();
        judgeAmendCommand.setRefJudgeId(2);
        judgeAmendCommand.setSurname(A_SURNAME);
        judgeAmendCommand.setFirstName(A_FIRST_NAME);
        judgeAmendCommand.setTitle("A title");
        judgeAmendCommand.setFullListTitle1("A FullListTitle1");
        judgeAmendCommand.setJudgeType("A judgeType");

        final BindingResult errors = new BeanPropertyBindingResult(judgeAmendCommand, JUDGE_AMEND_VALIDATOR);

        classUnderTest.validate(judgeAmendCommand, errors);

        assertFalse(errors.hasErrors(), NOT_FALSE);
    }

    @Test
    void validateRefJudgeIdTest() {
        final JudgeAmendCommand judgeAmendCommand = new JudgeAmendCommand();
        judgeAmendCommand.setRefJudgeId(null);

        final BindingResult errors = new BeanPropertyBindingResult(judgeAmendCommand, JUDGE_AMEND_VALIDATOR);

        classUnderTest.validate(judgeAmendCommand, errors);

        assertEquals("judgeAmendCommand.judgeId.notNull", errors.getGlobalErrors().get(0).getCode(), NOT_FALSE);
    }

    @Test
    void validateSurnameTest() {
        final JudgeAmendCommand judgeAmendCommand = new JudgeAmendCommand();
        judgeAmendCommand.setRefJudgeId(2);
        judgeAmendCommand.setSurname(null);

        final BindingResult errors = new BeanPropertyBindingResult(judgeAmendCommand, JUDGE_AMEND_VALIDATOR);

        classUnderTest.validate(judgeAmendCommand, errors);

        assertEquals("judgeAmendCommand.surname.notBlank", errors.getGlobalErrors().get(0).getCode(), NOT_FALSE);
    }

    @Test
    void validateFirstNameTest() {
        final JudgeAmendCommand judgeAmendCommand = new JudgeAmendCommand();
        judgeAmendCommand.setRefJudgeId(2);
        judgeAmendCommand.setSurname(A_SURNAME);
        judgeAmendCommand.setFirstName(null);

        final BindingResult errors = new BeanPropertyBindingResult(judgeAmendCommand, JUDGE_AMEND_VALIDATOR);

        classUnderTest.validate(judgeAmendCommand, errors);

        assertEquals("judgeAmendCommand.firstName.notBlank", errors.getGlobalErrors().get(0).getCode(), NOT_FALSE);
    }

    @Test
    void validateTitleTest() {
        final JudgeAmendCommand judgeAmendCommand = new JudgeAmendCommand();
        judgeAmendCommand.setRefJudgeId(2);
        judgeAmendCommand.setSurname(A_SURNAME);
        judgeAmendCommand.setFirstName(A_FIRST_NAME);
        judgeAmendCommand.setTitle(null);

        final BindingResult errors = new BeanPropertyBindingResult(judgeAmendCommand, JUDGE_AMEND_VALIDATOR);

        classUnderTest.validate(judgeAmendCommand, errors);

        assertEquals("judgeAmendCommand.title.notBlank", errors.getGlobalErrors().get(0).getCode(), NOT_FALSE);
    }

    @Test
    void validateFullListTitleTest() {
        final JudgeAmendCommand judgeAmendCommand = new JudgeAmendCommand();
        judgeAmendCommand.setRefJudgeId(2);
        judgeAmendCommand.setSurname(A_SURNAME);
        judgeAmendCommand.setFirstName(A_FIRST_NAME);
        judgeAmendCommand.setTitle("A title");
        judgeAmendCommand.setFullListTitle1(null);

        final BindingResult errors = new BeanPropertyBindingResult(judgeAmendCommand, JUDGE_AMEND_VALIDATOR);

        classUnderTest.validate(judgeAmendCommand, errors);

        assertEquals("judgeAmendCommand.fullListTitle1.notBlank", errors.getGlobalErrors().get(0).getCode(), NOT_FALSE);
    }

    @Test
    void validateJudgeTypeTest() {
        final JudgeAmendCommand judgeAmendCommand = new JudgeAmendCommand();
        judgeAmendCommand.setRefJudgeId(2);
        judgeAmendCommand.setSurname(A_SURNAME);
        judgeAmendCommand.setFirstName(A_FIRST_NAME);
        judgeAmendCommand.setTitle("A title");
        judgeAmendCommand.setFullListTitle1("A FullListTitle1");
        judgeAmendCommand.setJudgeType(null);

        final BindingResult errors = new BeanPropertyBindingResult(judgeAmendCommand, JUDGE_AMEND_VALIDATOR);

        classUnderTest.validate(judgeAmendCommand, errors);

        assertEquals("judgeAmendCommand.judgeType.notBlank", errors.getGlobalErrors().get(0).getCode(), NOT_FALSE);
    }
}