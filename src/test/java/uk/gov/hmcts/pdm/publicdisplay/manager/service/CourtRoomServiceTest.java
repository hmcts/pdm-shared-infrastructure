package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.pdm.business.entities.xhbcourt.XhbCourtDao;
import uk.gov.hmcts.pdm.business.entities.xhbcourt.XhbCourtRepository;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICourtRoomService;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CourtRoomServiceTest extends AbstractJUnit {

    /** The class under test. */
    protected ICourtRoomService classUnderTest;

    /** The mock Display Location Repo. */
    protected XhbCourtRepository mockCourtRepo;


    protected static final String NOT_EQUAL = "Not equal";
    protected static final String FALSE = "False";


    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new CourtRoomService();

        // Setup the mock version of the called classes
        mockCourtRepo = createMock(XhbCourtRepository.class);

        ReflectionTestUtils.setField(classUnderTest, "xhbCourtRepository", mockCourtRepo);
    }

    @Test
    void courtsTest() {

        XhbCourtDao courtDao = new XhbCourtDao();
        courtDao.setCourtId(1);
        courtDao.setCourtName("courtName");
        courtDao.setAddressId(1);

        List<XhbCourtDao> courtDaoList = new ArrayList<>();
        courtDaoList.add(courtDao);

        // Add the mock calls to child classes
        expect(mockCourtRepo.findAll()).andReturn(courtDaoList);

        replay(mockCourtRepo);

        // Perform the test
        List<CourtDto> rotationSetsDtos = classUnderTest.getCourts();

        // Assert that the objects are as expected
        assertEquals(courtDao.getCourtId(), rotationSetsDtos.get(0).getId(), NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockCourtRepo);

    }

    @Test
    void emptyCourtsTest() {

        List<XhbCourtDao> courtDaoList = new ArrayList<>();

        // Add the mock calls to child classes
        expect(mockCourtRepo.findAll()).andReturn(courtDaoList);

        replay(mockCourtRepo);

        // Perform the test
        List<CourtDto> courtDtoList = classUnderTest.getCourts();

        // Assert that the objects are as expected
        assertTrue(courtDtoList.isEmpty(), "NOT Empty");

        // Verify the expected mocks were called
        verify(mockCourtRepo);

    }
}
