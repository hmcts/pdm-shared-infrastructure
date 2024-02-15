package uk.gov.hmcts.pdm.publicdisplay.manager.web.court;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;

import java.util.List;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(EasyMockExtension.class)
class CourtSelectedValidatorTest extends AbstractJUnit {

    private static final String FALSE = "False";

    private CourtSelectedValidator classUnderTest;

    private CourtPageStateHolder mockcourtPageStateHolder;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test (CduAmendValidator)
        classUnderTest = new CourtSelectedValidator();


        // Setup the mock version of the called classes
        mockcourtPageStateHolder = createMock(CourtPageStateHolder.class);

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "courtPageStateHolder", mockcourtPageStateHolder);
    }

    protected List<XhibitCourtSiteDto> createCourtSiteDtoList() {
        XhibitCourtSiteDto xhibitCourtSiteDto = new XhibitCourtSiteDto();
        xhibitCourtSiteDto.setId(8L);
        xhibitCourtSiteDto.setCourtId(10);
        return List.of(xhibitCourtSiteDto);
    }

    @Test
    void supportsTest() {
        final boolean result = classUnderTest.supports(CourtSelectedValidator.class);
        assertTrue(result, FALSE);
    }

    @Test
    void isCourtSiteValidTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        expect(mockcourtPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockcourtPageStateHolder);

        boolean courtSiteValid = classUnderTest.isCourtSiteValid(8L);

        assertTrue(courtSiteValid, "Not true");


    }

    @Test
    void isCourtSiteValidFalseTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        expect(mockcourtPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockcourtPageStateHolder);

        boolean courtSiteValid = classUnderTest.isCourtSiteValid(9L);

        assertFalse(courtSiteValid, "Not false");
    }

    @Test
    void isRegisteredCourtSiteTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.YES_CHAR);
        expect(mockcourtPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockcourtPageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSite(xhibitCourtSiteDtos.get(0));

        assertTrue(courtSiteValid, "Not true");
    }

    @Test
    void isRegisteredCourtSiteFalseTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.NO_CHAR);
        expect(mockcourtPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockcourtPageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSite(xhibitCourtSiteDtos.get(0));

        assertFalse(courtSiteValid, "Not false");
    }

    @Test
    void isRegisteredCourtSiteSelectedTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.YES_CHAR);
        expect(mockcourtPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockcourtPageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSiteSelected(8L);

        assertTrue(courtSiteValid, "Not true");
    }

    @Test
    void isRegisteredCourtSiteSelectedFalseTest() {
        final List<XhibitCourtSiteDto> xhibitCourtSiteDtos = createCourtSiteDtoList();
        xhibitCourtSiteDtos.get(0).setRegisteredIndicator(AppConstants.YES_CHAR);
        expect(mockcourtPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockcourtPageStateHolder);

        boolean courtSiteValid = classUnderTest.isRegisteredCourtSiteSelected(10L);

        assertFalse(courtSiteValid, "Not false");
    }

    @Test
    void testGetCourtPageStateHolder() {
        CourtPageStateHolder courtPageStateHolder = classUnderTest.getCourtPageStateHolder();

        assertInstanceOf(CourtPageStateHolder.class, courtPageStateHolder, "Not an Instance");
    }
}
