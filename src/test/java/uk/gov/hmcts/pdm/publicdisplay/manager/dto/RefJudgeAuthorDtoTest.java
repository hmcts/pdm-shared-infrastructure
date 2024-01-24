package uk.gov.hmcts.pdm.publicdisplay.manager.dto;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
abstract class RefJudgeAuthorDtoTest extends AbstractJUnit {

    /** The class under test. */
    protected RefJudgeDto classUnderTest;

    protected static final String NOT_EQUAL = "Not equal";

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        classUnderTest = new RefJudgeDto();

    }

    @Test
    void titleTest() {

        classUnderTest.setTitle("title");
        assertEquals("title", classUnderTest.getTitle(), NOT_EQUAL);

    }

    @Test
    void firstNameTest() {

        classUnderTest.setFirstName("FirstName");
        assertEquals("FirstName", classUnderTest.getFirstName(), NOT_EQUAL);

    }

    @Test
    void middleNameTest() {

        classUnderTest.setMiddleName("MiddleName");
        assertEquals("MiddleName", classUnderTest.getMiddleName(), NOT_EQUAL);

    }

    @Test
    void surnameTest() {

        classUnderTest.setSurname("Surname");
        assertEquals("Surname", classUnderTest.getSurname(), NOT_EQUAL);

    }

    @Test
    void fullListTitle1Test() {

        classUnderTest.setFullListTitle1("FLTitle1");
        assertEquals("FLTitle1", classUnderTest.getFullListTitle1(), NOT_EQUAL);

    }

    @Test
    void fullListTitle2Test() {

        classUnderTest.setFullListTitle2("FLTitle2");
        assertEquals("FLTitle2", classUnderTest.getFullListTitle2(), NOT_EQUAL);

    }

    @Test
    void fullListTitle3Test() {

        classUnderTest.setFullListTitle3("FLTitle3");
        assertEquals("FLTitle3", classUnderTest.getFullListTitle3(), NOT_EQUAL);

    }


    @Test
    void initialsTest() {

        classUnderTest.setInitials("initials");
        assertEquals("initials", classUnderTest.getInitials(), NOT_EQUAL);

    }
}
