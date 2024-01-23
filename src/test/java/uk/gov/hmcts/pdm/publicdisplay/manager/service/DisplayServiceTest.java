package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.pdm.business.entities.xhbcourtsite.XhbCourtSiteDao;
import uk.gov.hmcts.pdm.business.entities.xhbdisplay.XhbDisplayDao;
import uk.gov.hmcts.pdm.business.entities.xhbdisplaytype.XhbDisplayTypeDao;
import uk.gov.hmcts.pdm.business.entities.xhbrotationsets.XhbRotationSetsDao;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.DisplayDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.DisplayTypeDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.RotationSetsDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DisplayServiceTest extends DisplayCrudTest {

    @Test
    void rotationSetsEmptyTest() {

        List<XhbRotationSetsDao> rotationSetsDaos = new ArrayList<>();

        // Add the mock calls to child classes
        expect(mockRotationSetsRepo.findByCourtId(1)).andReturn(rotationSetsDaos);

        replay(mockRotationSetsRepo);

        // Perform the test
        List<RotationSetsDto> rotationSetsDtos = classUnderTest.getRotationSets(1);

        // Assert that the objects are as expected
        assertTrue(rotationSetsDtos.isEmpty(), "False");

        // Verify the expected mocks were called
        verify(mockRotationSetsRepo);

    }

    @Test
    void displayTypesTest() {

        XhbDisplayTypeDao displayTypeDao = new XhbDisplayTypeDao();
        displayTypeDao.setDisplayTypeId(1);
        displayTypeDao.setDescriptionCode("description code");

        List<XhbDisplayTypeDao> displayTypeDaoList = new ArrayList<>();
        displayTypeDaoList.add(displayTypeDao);

        // Add the mock calls to child classes
        expect(mockDisplayTypeRepository.findAll()).andReturn(displayTypeDaoList);

        replay(mockDisplayTypeRepository);

        // Perform the test
        List<DisplayTypeDto> displayTypeDtoList = classUnderTest.getDisplayTypes();

        // Assert that the objects are as expected
        assertEquals(displayTypeDtoList.get(0).getDisplayTypeId(), displayTypeDao.getDisplayTypeId(), NOT_EQUAL);
        assertEquals(displayTypeDtoList.get(0).getDescriptionCode(), displayTypeDao.getDescriptionCode(), NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockDisplayTypeRepository);

    }

    @Test
    void displayTypesEmptyTest() {

        List<XhbDisplayTypeDao> displayTypeDaoList = new ArrayList<>();

        // Add the mock calls to child classes
        expect(mockDisplayTypeRepository.findAll()).andReturn(displayTypeDaoList);

        replay(mockDisplayTypeRepository);

        // Perform the test
        List<DisplayTypeDto> displayTypeDtoList = classUnderTest.getDisplayTypes();

        // Assert that the objects are as expected
        assertTrue(displayTypeDtoList.isEmpty(), FALSE);

        // Verify the expected mocks were called
        verify(mockDisplayTypeRepository);

    }

    @Test
    void displaysTest() {

        XhbDisplayDao xhbDisplayDao = createDisplayDao();

        List<XhbDisplayDao> displayDaoList = new ArrayList<>();
        displayDaoList.add(xhbDisplayDao);

        List<DisplayTypeDto> displayTypeDtoList = createDisplayTypeDtoList();

        List<XhibitCourtSiteDto> courtSiteDtoList = createCourtSiteDtoList();

        List<RotationSetsDto> rotationSetsDtoList = createRotationSetsDtoList();

        // Add the mock calls to child classes
        expect(mockDisplayRepo.findByCourtSiteId(1)).andReturn(displayDaoList);

        replay(mockDisplayRepo);

        // Perform the test
        List<DisplayDto> displayDtos = classUnderTest.getDisplays(1L, displayTypeDtoList, courtSiteDtoList, rotationSetsDtoList);

        // Assert that the objects are as expected
        assertEquals(xhbDisplayDao.getDisplayId(), displayDtos.get(0).getDisplayId(), NOT_EQUAL);
        assertEquals(xhbDisplayDao.getDescriptionCode(), displayDtos.get(0).getDescriptionCode(), NOT_EQUAL);
        assertEquals(xhbDisplayDao.getDisplayLocationId(), displayDtos.get(0).getDisplayLocationId(), NOT_EQUAL);
        assertEquals(xhbDisplayDao.getDisplayTypeId(), displayDtos.get(0).getDisplayTypeId(), NOT_EQUAL);
        assertEquals(xhbDisplayDao.getLocale(), displayDtos.get(0).getLocale(), NOT_EQUAL);
        assertEquals(xhbDisplayDao.getRotationSetId(), displayDtos.get(0).getRotationSetId(), NOT_EQUAL);
        assertEquals(xhbDisplayDao.getShowUnassignedYn(), displayDtos.get(0).getShowUnassignedYn(), NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockDisplayRepo);

    }

    @Test
    void emptyDisplaysTest() {

        List<DisplayTypeDto> displayTypeDtoList = createDisplayTypeDtoList();

        List<XhibitCourtSiteDto> courtSiteDtoList = createCourtSiteDtoList();

        List<RotationSetsDto> rotationSetsDtoList = createRotationSetsDtoList();

        List<XhbDisplayDao> displayDaoList = new ArrayList<>();

        // Add the mock calls to child classes
        expect(mockDisplayRepo.findByCourtSiteId(1)).andReturn(displayDaoList);

        replay(mockDisplayRepo);

        // Perform the test
        List<DisplayDto> displayDtos = classUnderTest.getDisplays(1L, displayTypeDtoList, courtSiteDtoList, rotationSetsDtoList);

        // Assert that the objects are as expected
        assertTrue(displayDtos.isEmpty(), FALSE);

        // Verify the expected mocks were called
        verify(mockDisplayRepo);

    }

    @Test
    void emptyDtosTest() {

        XhbDisplayDao xhbDisplayDao = createDisplayDao();

        List<XhbDisplayDao> displayDaoList = new ArrayList<>();
        displayDaoList.add(xhbDisplayDao);

        // Add the mock calls to child classes
        expect(mockDisplayRepo.findByCourtSiteId(1)).andReturn(displayDaoList);

        replay(mockDisplayRepo);

        // Perform the test
        List<DisplayDto> displayDtos = classUnderTest.getDisplays(1L, null, null, null);

        // Assert that the objects are as expected
        assertNull(displayDtos.get(0).getDisplayType(), "Not null");

        // Verify the expected mocks were called
        verify(mockDisplayRepo);

    }

    @Test
    void courtSitesTest() {

        XhbCourtSiteDao courtSiteDao = new XhbCourtSiteDao();
        courtSiteDao.setId(1);
        courtSiteDao.setCourtSiteName("courtSiteName");
        courtSiteDao.setCourtSiteCode("courtSiteCode");
        courtSiteDao.setCourtId(2);


        List<XhbCourtSiteDao> courtSiteDaoList = new ArrayList<>();
        courtSiteDaoList.add(courtSiteDao);

        // Add the mock calls to child classes
        expect(mockCourtSiteRepo.findAll()).andReturn(courtSiteDaoList);

        replay(mockCourtSiteRepo);

        // Perform the test
        List<XhibitCourtSiteDto> courtSiteDtos = classUnderTest.getCourtSites();

        // Assert that the objects are as expected
        assertEquals(courtSiteDao.getId(), courtSiteDtos.get(0).getId().intValue(), NOT_EQUAL);
        assertEquals(courtSiteDao.getCourtSiteName(), courtSiteDtos.get(0).getCourtSiteName(), NOT_EQUAL);
        assertEquals(courtSiteDao.getCourtSiteCode(), courtSiteDtos.get(0).getCourtSiteCode(), NOT_EQUAL);
        assertEquals(courtSiteDao.getCourtId(), courtSiteDtos.get(0).getCourtId(), NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockCourtSiteRepo);

    }


    @Test
    void courtSitesEmptyTest() {

        List<XhbCourtSiteDao> courtSiteDaoList = new ArrayList<>();

        // Add the mock calls to child classes
        expect(mockCourtSiteRepo.findAll()).andReturn(courtSiteDaoList);

        replay(mockCourtSiteRepo);

        // Perform the test
        List<XhibitCourtSiteDto> courtSiteDtos = classUnderTest.getCourtSites();

        // Assert that the objects are as expected
        assertTrue(courtSiteDtos.isEmpty(), FALSE);

        // Verify the expected mocks were called
        verify(mockCourtSiteRepo);

    }

    private XhbDisplayDao createDisplayDao() {
        XhbDisplayDao xhbDisplayDao = new XhbDisplayDao();
        xhbDisplayDao.setDisplayId(2);
        xhbDisplayDao.setDescriptionCode("description code");
        xhbDisplayDao.setDisplayLocationId(3);
        xhbDisplayDao.setDisplayTypeId(4);
        xhbDisplayDao.setLocale("locale");
        xhbDisplayDao.setRotationSetId(5);
        xhbDisplayDao.setShowUnassignedYn("Yn");
        return xhbDisplayDao;
    }

}
