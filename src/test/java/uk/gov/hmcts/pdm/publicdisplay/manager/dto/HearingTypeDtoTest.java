package uk.gov.hmcts.pdm.publicdisplay.manager.dto;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
class HearingTypeDtoTest extends AbstractJUnit {

    /** The class under test. */
    protected HearingTypeDto classUnderTest;

    protected static final String NOT_EQUAL = "Not equal";

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        classUnderTest = new HearingTypeDto();

    }

    @Test
    void refHearingTypeIdTest() {

        classUnderTest.setRefHearingTypeId(1);
        assertEquals(1, classUnderTest.getRefHearingTypeId(), NOT_EQUAL);

    }

    @Test
    void hearingTypeCodeTest() {

        classUnderTest.setHearingTypeCode("HearingTypeCode");
        assertEquals("HearingTypeCode", classUnderTest.getHearingTypeCode(), NOT_EQUAL);

    }

    @Test
    void hearingTypeDescTest() {

        classUnderTest.setHearingTypeDesc("HearingTypeDesc");
        assertEquals("HearingTypeDesc", classUnderTest.getHearingTypeDesc(), NOT_EQUAL);

    }

    @Test
    void categoryTest() {

        classUnderTest.setCategory("Category");
        assertEquals("Category", classUnderTest.getCategory(), NOT_EQUAL);

    }

    @Test
    void seqNoTest() {

        classUnderTest.setSeqNo(50);
        assertEquals(50, classUnderTest.getSeqNo(), NOT_EQUAL);

    }

    @Test
    void listSequenceTest() {

        classUnderTest.setListSequence(60);
        assertEquals(60, classUnderTest.getListSequence(), NOT_EQUAL);

    }

    @Test
    void courtIdTest() {

        classUnderTest.setCourtId(70);
        assertEquals(70, classUnderTest.getCourtId(), NOT_EQUAL);

    }

}
