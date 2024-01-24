package uk.gov.hmcts.pdm.publicdisplay.manager.dto;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
class DisplayDtoTest {

    protected static final String NOT_EQUAL = "Not equal";

    /** The class under test. */
    protected DisplayDto classUnderTest;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        classUnderTest = new DisplayDto();

    }

    @Test
    void displayIdTest() {

        classUnderTest.setDisplayId(1);
        assertEquals(1, classUnderTest.getDisplayId(), NOT_EQUAL);

    }

    @Test
    void displayTypeIdTest() {

        classUnderTest.setDisplayTypeId(10);
        assertEquals(10, classUnderTest.getDisplayTypeId(), NOT_EQUAL);

    }

    @Test
    void displayLocationIdTest() {

        classUnderTest.setDisplayLocationId(20);
        assertEquals(20, classUnderTest.getDisplayLocationId(), NOT_EQUAL);

    }

    @Test
    void rotationSetIdTest() {

        classUnderTest.setRotationSetId(30);
        assertEquals(30, classUnderTest.getRotationSetId(), NOT_EQUAL);

    }

    @Test
    void descriptionCodeTest() {

        classUnderTest.setDescriptionCode("DescCode");
        assertEquals("DescCode", classUnderTest.getDescriptionCode(), NOT_EQUAL);

    }

    @Test
    void localeTest() {

        classUnderTest.setLocale("locale");
        assertEquals("locale", classUnderTest.getLocale(), NOT_EQUAL);

    }

    @Test
    void showUnassignedYnTest() {

        classUnderTest.setShowUnassignedYn("ShowUnassignedYn");
        assertEquals("ShowUnassignedYn", classUnderTest.getShowUnassignedYn(), NOT_EQUAL);

    }

    @Test
    void displayTypeTest() {

        DisplayTypeDto displayTypeDto = new DisplayTypeDto();

        classUnderTest.setDisplayType(displayTypeDto);
        assertEquals(displayTypeDto, classUnderTest.getDisplayType(), NOT_EQUAL);

    }

    @Test
    void courtSiteTest() {

        XhibitCourtSiteDto xhibitCourtSiteDto = new XhibitCourtSiteDto();

        classUnderTest.setCourtSite(xhibitCourtSiteDto);
        assertEquals(xhibitCourtSiteDto, classUnderTest.getCourtSite(), NOT_EQUAL);

    }

    @Test
    void rotationSetTest() {

        RotationSetsDto rotationSetsDto = new RotationSetsDto();
        classUnderTest.setRotationSet(rotationSetsDto);
        assertEquals(rotationSetsDto, classUnderTest.getRotationSet(), NOT_EQUAL);

    }

}
