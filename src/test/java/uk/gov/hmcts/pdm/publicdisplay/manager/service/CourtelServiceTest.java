package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.easymock.Capture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.pdm.business.entities.xhbconfigprop.XhbConfigPropDao;
import uk.gov.hmcts.pdm.business.entities.xhbconfigprop.XhbConfigPropRepository;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtelDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICourtelService;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel.CourtelAmendCommand;

import java.util.List;
import java.util.Optional;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

@ExtendWith(MockitoExtension.class)
class CourtelServiceTest extends AbstractJUnit {
    /**
     * The class under test.
     */
    protected ICourtelService classUnderTest;

    /**
     * The mock Display Location Repo.
     */
    protected XhbConfigPropRepository mockXhbConfigPropRepo;
    protected static final String COURTEL_LIST_AMOUNT = "COURTEL_LIST_AMOUNT";
    protected static final String COURTEL_MAX_RETRY = "COURTEL_MAX_RETRY";
    protected static final String COURTEL_MESSAGE_LOOKUP_DELAY = "MESSAGE_LOOKUP_DELAY";

    protected static final String NOT_EQUAL = "Not equal";
    protected static final String FALSE = "False";

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        classUnderTest = new CourtelService();

        // Setup the mock version of the called classes
        mockXhbConfigPropRepo = createMock(XhbConfigPropRepository.class);

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "xhbConfigPropRepository", mockXhbConfigPropRepo);
    }

    @Test
    void testGetCourtelPropertyValues() {
        XhbConfigPropDao xhbConfigPropLookupDao = new XhbConfigPropDao();
        xhbConfigPropLookupDao.setPropertyValue("10");
        final List<XhbConfigPropDao> xhbConfigPropLookupDaoList = List.of(xhbConfigPropLookupDao);

        XhbConfigPropDao xhbConfigPropMaxRetryDao = new XhbConfigPropDao();
        xhbConfigPropMaxRetryDao.setPropertyValue("20");
        List<XhbConfigPropDao> xhbConfigPropMaxRetryDaoList = List.of(xhbConfigPropMaxRetryDao);

        XhbConfigPropDao xhbConfigPropListAmountDao = new XhbConfigPropDao();
        xhbConfigPropListAmountDao.setPropertyValue("30");
        List<XhbConfigPropDao> xhbConfigPropListAmountDaoList = List.of(xhbConfigPropListAmountDao);

        expect(mockXhbConfigPropRepo.findByPropertyName(eq(COURTEL_LIST_AMOUNT)))
                .andReturn(xhbConfigPropListAmountDaoList);

        expect(mockXhbConfigPropRepo.findByPropertyName(eq(COURTEL_MAX_RETRY)))
                .andReturn(xhbConfigPropMaxRetryDaoList);

        expect(mockXhbConfigPropRepo.findByPropertyName(eq(COURTEL_MESSAGE_LOOKUP_DELAY)))
                .andReturn(xhbConfigPropLookupDaoList);

        replay(mockXhbConfigPropRepo);

        CourtelDto courtelDto = classUnderTest.getCourtelPropertyValues();

        Assertions.assertEquals("10", courtelDto.getCourtelMessageLookupDelay(), NOT_EQUAL);
        Assertions.assertEquals("30", courtelDto.getCourtelListAmount(), NOT_EQUAL);
        Assertions.assertEquals("20", courtelDto.getCourtelMaxRetry(), NOT_EQUAL);

        verify(mockXhbConfigPropRepo);
    }

    @Test
    void testGetCourtelPropertiesEmpty() {
        final List<XhbConfigPropDao> xhbConfigPropLookupDaoList = List.of();
        final List<XhbConfigPropDao> xhbConfigPropMaxRetryDaoList = List.of();
        final List<XhbConfigPropDao> xhbConfigPropListAmountDaoList = List.of();

        expect(mockXhbConfigPropRepo.findByPropertyName(COURTEL_LIST_AMOUNT))
                .andReturn(xhbConfigPropListAmountDaoList);
        expect(mockXhbConfigPropRepo.findByPropertyName(COURTEL_MAX_RETRY))
                .andReturn(xhbConfigPropMaxRetryDaoList);
        expect(mockXhbConfigPropRepo.findByPropertyName(COURTEL_MESSAGE_LOOKUP_DELAY))
                .andReturn(xhbConfigPropLookupDaoList);


        mockXhbConfigPropRepo.saveDao(isA(XhbConfigPropDao.class));
        expectLastCall().times(3);
        replay(mockXhbConfigPropRepo);

        CourtelDto courtelDto = classUnderTest.getCourtelPropertyValues();

        Assertions.assertEquals("5", courtelDto.getCourtelListAmount(), NOT_EQUAL);
        Assertions.assertEquals("5", courtelDto.getCourtelMaxRetry(), NOT_EQUAL);
        Assertions.assertEquals("60", courtelDto.getCourtelMessageLookupDelay(), NOT_EQUAL);

        verify(mockXhbConfigPropRepo);
    }

    @Test
    void testUpdateCourtel() {
        final CourtelAmendCommand courtelAmendCommand = new CourtelAmendCommand();
        courtelAmendCommand.setCourtelMaxRetry("34");
        courtelAmendCommand.setCourtelListAmount("54");
        courtelAmendCommand.setMessageLookupDelay("32");

        XhbConfigPropDao xhbConfigPropLookupDao = new XhbConfigPropDao();
        xhbConfigPropLookupDao.setPropertyValue("10");
        final List<XhbConfigPropDao> xhbConfigPropLookupDaoList = List.of(xhbConfigPropLookupDao);

        XhbConfigPropDao xhbConfigPropMaxRetryDao = new XhbConfigPropDao();
        xhbConfigPropMaxRetryDao.setPropertyValue("20");
        final List<XhbConfigPropDao> xhbConfigPropMaxRetryDaoList = List.of(xhbConfigPropMaxRetryDao);

        XhbConfigPropDao xhbConfigPropListAmountDao = new XhbConfigPropDao();
        xhbConfigPropListAmountDao.setPropertyValue("30");
        final List<XhbConfigPropDao> xhbConfigPropListAmountDaoList = List.of(xhbConfigPropListAmountDao);

        Capture<XhbConfigPropDao> configPropDaoCapture = newCapture();
        Optional<XhbConfigPropDao> xhbConfigPropDaoOptional = Optional.of(new XhbConfigPropDao());

        expect(mockXhbConfigPropRepo.findByPropertyName(COURTEL_LIST_AMOUNT))
                .andReturn(xhbConfigPropListAmountDaoList);
        expect(mockXhbConfigPropRepo.findByPropertyName(COURTEL_MAX_RETRY))
                .andReturn(xhbConfigPropMaxRetryDaoList);
        expect(mockXhbConfigPropRepo.findByPropertyName(COURTEL_MESSAGE_LOOKUP_DELAY))
                .andReturn(xhbConfigPropLookupDaoList);

        expect(mockXhbConfigPropRepo.updateDao(capture(configPropDaoCapture)))
                .andReturn(xhbConfigPropDaoOptional).times(3);
        replay(mockXhbConfigPropRepo);

        classUnderTest.updateCourtelProperties(courtelAmendCommand);

        verify(mockXhbConfigPropRepo);
    }

    @Test
    void testUpdateCourtelEmpty() {
        final CourtelAmendCommand courtelAmendCommand = new CourtelAmendCommand();
        courtelAmendCommand.setCourtelMaxRetry("34");
        courtelAmendCommand.setCourtelListAmount("54");
        courtelAmendCommand.setMessageLookupDelay("32");

        final List<XhbConfigPropDao> xhbConfigPropLookupDaoList = List.of();
        final List<XhbConfigPropDao> xhbConfigPropMaxRetryDaoList = List.of();
        final List<XhbConfigPropDao> xhbConfigPropListAmountDaoList = List.of();

        expect(mockXhbConfigPropRepo.findByPropertyName(COURTEL_LIST_AMOUNT))
                .andReturn(xhbConfigPropListAmountDaoList);
        expect(mockXhbConfigPropRepo.findByPropertyName(COURTEL_MAX_RETRY))
                .andReturn(xhbConfigPropMaxRetryDaoList);
        expect(mockXhbConfigPropRepo.findByPropertyName(COURTEL_MESSAGE_LOOKUP_DELAY))
                .andReturn(xhbConfigPropLookupDaoList);

        mockXhbConfigPropRepo.saveDao(isA(XhbConfigPropDao.class));
        expectLastCall().times(3);

        replay(mockXhbConfigPropRepo);

        classUnderTest.updateCourtelProperties(courtelAmendCommand);

        verify(mockXhbConfigPropRepo);
    }

}