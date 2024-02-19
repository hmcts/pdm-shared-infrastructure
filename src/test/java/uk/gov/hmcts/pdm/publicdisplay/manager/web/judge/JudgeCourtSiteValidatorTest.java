package uk.gov.hmcts.pdm.publicdisplay.manager.web.judge;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;

import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(EasyMockExtension.class)
class JudgeCourtSiteValidatorTest extends JudgeSelectedValidatorTest {
    @Test
    void isCourtSiteValidTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        expect(mockJudgePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgePageStateHolder);

        boolean courtSiteValid = classUnderTest.isCourtSiteValid(8L);

        assertTrue(courtSiteValid, "Not true");


    }

    @Test
    void isCourtSiteValidFalseTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        expect(mockJudgePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgePageStateHolder);

        boolean courtSiteValid = classUnderTest.isCourtSiteValid(9L);

        assertFalse(courtSiteValid, "Not false");
    }

    @Test
    void isRegisteredCourtSiteTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.YES_CHAR);
        expect(mockJudgePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgePageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSite(xhibitCourtSiteDtos.get(0));

        assertTrue(courtSiteValid, "Not true");
    }

    @Test
    void isRegisteredCourtSiteFalseTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.NO_CHAR);
        expect(mockJudgePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgePageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSite(xhibitCourtSiteDtos.get(0));

        assertFalse(courtSiteValid, "Not false");
    }

    @Test
    void isRegisteredCourtSiteSelectedTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.YES_CHAR);
        expect(mockJudgePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgePageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSiteSelected(8L);

        assertTrue(courtSiteValid, "Not true");
    }

    @Test
    void isRegisteredCourtSiteSelectedFalseTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.YES_CHAR);
        expect(mockJudgePageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockJudgePageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSiteSelected(10L);

        assertFalse(courtSiteValid, "Not false");
    }
}
