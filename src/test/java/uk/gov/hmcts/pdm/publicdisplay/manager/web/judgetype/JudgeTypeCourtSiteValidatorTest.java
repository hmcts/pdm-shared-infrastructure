package uk.gov.hmcts.pdm.publicdisplay.manager.web.judgetype;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;

import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JudgeTypeCourtSiteValidatorTest extends JudgeTypeSelectedValidatorTest {
    @Test
    void isCourtSiteValidTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        expect(mockJudgeTypePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgeTypePageStateHolder);

        boolean courtSiteValid = classUnderTest.isCourtSiteValid(8L);

        assertTrue(courtSiteValid, "Not true");


    }

    @Test
    void isCourtSiteValidFalseTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        expect(mockJudgeTypePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgeTypePageStateHolder);

        boolean courtSiteValid = classUnderTest.isCourtSiteValid(9L);

        assertFalse(courtSiteValid, "Not false");
    }

    @Test
    void isRegisteredCourtSiteTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.YES_CHAR);
        expect(mockJudgeTypePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgeTypePageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSite(xhibitCourtSiteDtos.get(0));

        assertTrue(courtSiteValid, "Not true");
    }

    @Test
    void isRegisteredCourtSiteFalseTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.NO_CHAR);
        expect(mockJudgeTypePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgeTypePageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSite(xhibitCourtSiteDtos.get(0));

        assertFalse(courtSiteValid, "Not false");
    }

    @Test
    void isRegisteredCourtSiteSelectedTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.YES_CHAR);
        expect(mockJudgeTypePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgeTypePageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSiteSelected(8L);

        assertTrue(courtSiteValid, "Not true");
    }

    @Test
    void isRegisteredCourtSiteSelectedFalseTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.YES_CHAR);
        expect(mockJudgeTypePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgeTypePageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSiteSelected(10L);

        assertFalse(courtSiteValid, "Not false");
    }
}
