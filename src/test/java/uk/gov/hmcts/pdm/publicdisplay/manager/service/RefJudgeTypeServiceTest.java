package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.easymock.Capture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.pdm.business.entities.xhbcourtsite.XhbCourtSiteDao;
import uk.gov.hmcts.pdm.business.entities.xhbcourtsite.XhbCourtSiteRepository;
import uk.gov.hmcts.pdm.business.entities.xhbrefsystemcode.XhbRefSystemCodeDao;
import uk.gov.hmcts.pdm.business.entities.xhbrefsystemcode.XhbRefSystemCodeRepository;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.RefSystemCodeDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.judgetype.JudgeTypeAmendCommand;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.judgetype.JudgeTypeCreateCommand;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class RefJudgeTypeServiceTest {

    /**
     * The class under test.
     */
    private RefJudgeTypeService classUnderTest;
    private XhbCourtSiteRepository mockCourtSiteRepo;
    private XhbRefSystemCodeRepository mockRefSystemCodeRepo;

    private static final String NOT_EQUAL = "Not equal";
    private static final String NOT_EMPTY = "Not empty";
    private static final String COURT_SITE_CODE = "COURTSITECODE";
    private static final String COURT_SITE_NAME = "COURTSITENAME";

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new RefJudgeTypeService();

        // Setup the mock version of the called classes
        mockCourtSiteRepo = createMock(XhbCourtSiteRepository.class);
        mockRefSystemCodeRepo = createMock(XhbRefSystemCodeRepository.class);

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "xhbCourtSiteRepository", mockCourtSiteRepo);
        ReflectionTestUtils.setField(classUnderTest, "xhbRefSystemCodeRepository", mockRefSystemCodeRepo);

    }

    @Test
    void courtSitesTest() {

        XhbCourtSiteDao xhbCourtSiteDao = new XhbCourtSiteDao();
        xhbCourtSiteDao.setId(2);
        xhbCourtSiteDao.setCourtSiteName(COURT_SITE_NAME);
        xhbCourtSiteDao.setCourtSiteCode(COURT_SITE_CODE);
        xhbCourtSiteDao.setCourtId(4);

        List<XhbCourtSiteDao> courtSiteDaoList = new ArrayList<>();
        courtSiteDaoList.add(xhbCourtSiteDao);

        // Add the mock calls to child classes
        expect(mockCourtSiteRepo.findAll()).andReturn(courtSiteDaoList);

        replay(mockCourtSiteRepo);

        // Perform the test
        List<XhibitCourtSiteDto> courtSiteDtoList = classUnderTest.getCourtSites();

        // Assert that the objects are as expected
        assertEquals(xhbCourtSiteDao.getId(), courtSiteDtoList.get(0).getId().intValue(), NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockCourtSiteRepo);

    }

    @Test
    void emptyCourtSitesTest() {

        List<XhbCourtSiteDao> courtSiteDaoList = new ArrayList<>();

        // Add the mock calls to child classes
        expect(mockCourtSiteRepo.findAll()).andReturn(courtSiteDaoList);

        replay(mockCourtSiteRepo);

        // Perform the test
        List<XhibitCourtSiteDto> courtSiteDtoList = classUnderTest.getCourtSites();

        // Assert that the objects are as expected
        assertTrue(courtSiteDtoList.isEmpty(), NOT_EMPTY);

        // Verify the expected mocks were called
        verify(mockCourtSiteRepo);

    }

    @Test
    void judgeTypesTest() {

        List<XhbRefSystemCodeDao> refSystemCodeDaoList = createRefSystemCodeDao();

        // Add the mock calls to child classes
        expect(mockRefSystemCodeRepo.findJudgeTypeByCourtSiteId(1)).andReturn(refSystemCodeDaoList);

        replay(mockRefSystemCodeRepo);

        // Perform the test
        List<RefSystemCodeDto> refSystemCodeDtoList = classUnderTest.getJudgeTypes(1L);

        // Assert that the objects are as expected
        assertEquals(refSystemCodeDaoList.get(0).getDeCode(), refSystemCodeDtoList.get(0).getDeCode(), NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockRefSystemCodeRepo);

    }

    @Test
    void judgeTypesEmptyTest() {

        List<XhbRefSystemCodeDao> refSystemCodeDaoList = new ArrayList<>();

        // Add the mock calls to child classes
        expect(mockRefSystemCodeRepo.findJudgeTypeByCourtSiteId(1)).andReturn(refSystemCodeDaoList);

        replay(mockRefSystemCodeRepo);

        // Perform the test
        List<RefSystemCodeDto> refSystemCodeDtoList = classUnderTest.getJudgeTypes(1L);

        // Assert that the objects are as expected
        assertTrue(refSystemCodeDtoList.isEmpty(), NOT_EMPTY);

        // Verify the expected mocks were called
        verify(mockRefSystemCodeRepo);

    }

    @Test
    void createJudgeTypeTest() {

        JudgeTypeCreateCommand judgeTypeCreateCommand = new JudgeTypeCreateCommand();
        judgeTypeCreateCommand.setCode("code");
        judgeTypeCreateCommand.setDescription("description");

        Capture<XhbRefSystemCodeDao> capturedRefSystemCodeDao = newCapture();

        mockRefSystemCodeRepo.saveDao(capture(capturedRefSystemCodeDao));
        expectLastCall();
        replay(mockRefSystemCodeRepo);

        // Perform the test
        classUnderTest.createJudgeType(judgeTypeCreateCommand, 1);

        // Assert that the objects are as expected
        assertEquals(judgeTypeCreateCommand.getCode(), capturedRefSystemCodeDao.getValue().getCode(), NOT_EQUAL);

        // Verify the expected mocks were called
        verify(mockRefSystemCodeRepo);

    }

    @Test
    void updateJudgeTypeTest() {

        JudgeTypeAmendCommand judgeTypeAmendCommand = new JudgeTypeAmendCommand();
        judgeTypeAmendCommand.setRefSystemCodeId(1);
        judgeTypeAmendCommand.setDescription("description");

        Optional<XhbRefSystemCodeDao> refSystemCodeDao = Optional.of(new XhbRefSystemCodeDao());

        expect(mockRefSystemCodeRepo.findById(1)).andReturn(refSystemCodeDao);
        expect(mockRefSystemCodeRepo.updateDao(refSystemCodeDao.get())).andReturn(refSystemCodeDao);
        replay(mockRefSystemCodeRepo);

        // Perform the test
        classUnderTest.updateJudgeType(judgeTypeAmendCommand);

        // Verify the expected mocks were called
        verify(mockRefSystemCodeRepo);
    }

    @Test
    void updateJudgeTypeEmptyTest() {

        JudgeTypeAmendCommand judgeTypeAmendCommand = new JudgeTypeAmendCommand();
        judgeTypeAmendCommand.setRefSystemCodeId(1);

        Optional<XhbRefSystemCodeDao> refSystemCodeDao = Optional.empty();

        expect(mockRefSystemCodeRepo.findById(1)).andReturn(refSystemCodeDao);
        replay(mockRefSystemCodeRepo);

        // Perform the test
        classUnderTest.updateJudgeType(judgeTypeAmendCommand);

        // Verify the expected mocks were called
        verify(mockRefSystemCodeRepo);

    }


    private List<XhbRefSystemCodeDao> createRefSystemCodeDao() {
        XhbRefSystemCodeDao xhbRefSystemCodeDao = new XhbRefSystemCodeDao();
        xhbRefSystemCodeDao.setCode("code");
        xhbRefSystemCodeDao.setCodeTitle("codeTitle");
        xhbRefSystemCodeDao.setCodeType("codeType");
        xhbRefSystemCodeDao.setCourtId(2);
        xhbRefSystemCodeDao.setCreatedBy("createdBy");
        xhbRefSystemCodeDao.setCreationDate(LocalDateTime.of(2024, 1, 11, 2, 2));
        xhbRefSystemCodeDao.setDeCode("DeCode");
        xhbRefSystemCodeDao.setLastUpdateDate(LocalDateTime.of(2024, 1, 11, 2, 2));
        xhbRefSystemCodeDao.setLastUpdatedBy("LastUpdatedBy");
        xhbRefSystemCodeDao.setObsInd("obsInd");
        xhbRefSystemCodeDao.setRefCodeOrder(3.0);
        xhbRefSystemCodeDao.setRefSystemCodeId(4);
        xhbRefSystemCodeDao.setVersion(5);
        ArrayList<XhbRefSystemCodeDao> xhbRefSystemCodeDaos = new ArrayList<>();
        xhbRefSystemCodeDaos.add(xhbRefSystemCodeDao);
        return xhbRefSystemCodeDaos;
    }
}
