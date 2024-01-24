package uk.gov.hmcts.pdm.publicdisplay.manager.dto;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
class RefSystemCodeDtoTest extends RefSystemCodeAuthorDtoTest {

    @Test
    void refSystemCodeId() {

        classUnderTest.setRefSystemCodeId(1);
        assertEquals(1, classUnderTest.getRefSystemCodeId(), NOT_EQUAL);

    }

    @Test
    void code() {

        classUnderTest.setCode("Code");
        assertEquals("Code", classUnderTest.getCode(), NOT_EQUAL);

    }

    @Test
    void codeType() {

        classUnderTest.setCodeType("CodeType");
        assertEquals("CodeType", classUnderTest.getCodeType(), NOT_EQUAL);

    }

    @Test
    void codeTitle() {

        classUnderTest.setCodeTitle("CodeTitle");
        assertEquals("CodeTitle", classUnderTest.getCodeTitle(), NOT_EQUAL);

    }

    @Test
    void deCode() {

        classUnderTest.setDeCode("DeCode");
        assertEquals("DeCode", classUnderTest.getDeCode(), NOT_EQUAL);

    }

    @Test
    void refCodeOrder() {

        classUnderTest.setRefCodeOrder(10.0);
        assertEquals(10.0, classUnderTest.getRefCodeOrder(), NOT_EQUAL);

    }

    @Test
    void version() {

        classUnderTest.setVersion(20);
        assertEquals(20, classUnderTest.getVersion(), NOT_EQUAL);

    }

    @Test
    void courtId() {

        classUnderTest.setCourtId(30);
        assertEquals(30, classUnderTest.getCourtId(), NOT_EQUAL);

    }

    @Test
    void obsInd() {

        classUnderTest.setObsInd("Y");
        assertEquals("Y", classUnderTest.getObsInd(), NOT_EQUAL);

    }

}
