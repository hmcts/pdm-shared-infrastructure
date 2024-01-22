package uk.gov.hmcts.pdm.publicdisplay.manager.dto;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
class RefJudgeDtoTest extends RefJudgeAuthorDtoTest {

    @Test
    void refJudgeIdTestTest() {

        classUnderTest.setRefJudgeId(1);
        assertEquals(1, classUnderTest.getRefJudgeId(), NOT_EQUAL);

    }

    @Test
    void judgeTypeTest() {

        classUnderTest.setJudgeType("judge");
        assertEquals("judge", classUnderTest.getJudgeType(), NOT_EQUAL);

    }

    @Test
    void crestJudgeIdTest() {

        classUnderTest.setCrestJudgeId(2);
        assertEquals(2, classUnderTest.getCrestJudgeId(), NOT_EQUAL);

    }

    @Test
    void statsCodeTest() {

        classUnderTest.setStatsCode("statsCode");
        assertEquals("statsCode", classUnderTest.getStatsCode(), NOT_EQUAL);

    }

    @Test
    void honoursTest() {

        classUnderTest.setHonours("honours");
        assertEquals("honours", classUnderTest.getHonours(), NOT_EQUAL);

    }

    @Test
    void judVersTest() {

        classUnderTest.setJudVers("judvers");
        assertEquals("judvers", classUnderTest.getJudVers(), NOT_EQUAL);

    }

    @Test
    void obsIndTest() {

        classUnderTest.setObsInd("obsInd");
        assertEquals("obsInd", classUnderTest.getObsInd(), NOT_EQUAL);

    }

    @Test
    void sourceTableTest() {

        classUnderTest.setSourceTable("sourceTable");
        assertEquals("sourceTable", classUnderTest.getSourceTable(), NOT_EQUAL);

    }

    @Test
    void courtIdTest() {

        classUnderTest.setCourtId(2);
        assertEquals(2, classUnderTest.getCourtId(), NOT_EQUAL);

    }

    @Test
    void judgeTypeDeCodeTest() {

        classUnderTest.setJudgeTypeDeCode("JTDCode");
        assertEquals("JTDCode", classUnderTest.getJudgeTypeDeCode(), NOT_EQUAL);

    }

}
