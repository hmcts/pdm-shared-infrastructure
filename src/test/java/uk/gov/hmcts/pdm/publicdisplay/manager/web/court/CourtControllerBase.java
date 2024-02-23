package uk.gov.hmcts.pdm.publicdisplay.manager.web.court;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.CourtService;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICourtService;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.createMock;

@ExtendWith(MockitoExtension.class)
abstract class CourtControllerBase extends AbstractJUnit {
    protected CourtSelectedValidator mockCourtSelectedValidator;
    protected CourtCreateValidator mockCourtCreateValidator;
    protected CourtAmendValidator mockCourtAmendValidator;
    protected CourtPageStateHolder mockCourtPageStateHolder;
    protected ICourtService mockCourtService;
    protected String viewNameViewCourtSite;
    protected String mappingNameViewCourtSiteUrl;
    protected String viewNameCreateCourt;
    protected String mappingNameCreateCourtUrl;
    protected String viewNameAmendCourt;
    protected String mappingNameAmendCourtUrl;
    protected MockMvc mockMvc;
    protected static final String NOT_NULL = "Not null";
    protected static final String NULL = "Null";
    protected static final String NOT_EQUAL = "Not equal";
    protected static final String FALSE = "false";
    protected static final String RESET = "reset";
    protected static final String COURT_ID = "courtId";
    protected static final String ADD = "add";
    protected static final String NOT_FALSE = "Not false";
    protected static final String COMMAND = "command";
    protected static final String NOT_AN_INSTANCE = "Not an instance";
    protected static final String COURTSITE_LIST = "courtSiteList";
    protected static final String COURT = "court";
    protected static final String THREE = "3";
    protected static final String MOCK_ERROR_MESSAGE = "mock error message";
    protected static final String XHIBIT_COURTSITE_ID = "xhibitCourtSiteId";
    protected static final String REQUEST_MAPPING = "/court";
    protected static final String COURTSITE_NAME_PARAM = "courtSiteName";
    protected static final String COURTSITE_CODE_PARAM = "courtSiteCode";
    protected static final String COURTSITE_NAME = "A court site name";
    protected static final String COURTSITE_CODE = "court site code";

    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        CourtController classUnderTest = new CourtController();
        // Setup the mock version of the called classes
        mockObjects();
        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "courtSelectedValidator",
            mockCourtSelectedValidator);
        ReflectionTestUtils.setField(classUnderTest, "courtCreateValidator",
            mockCourtCreateValidator);
        ReflectionTestUtils.setField(classUnderTest, "courtAmendValidator",
            mockCourtAmendValidator);
        ReflectionTestUtils.setField(classUnderTest, "courtPageStateHolder",
            mockCourtPageStateHolder);
        ReflectionTestUtils.setField(classUnderTest, "courtService", mockCourtService);

        // Get the static variables from the class under test
        viewNameViewCourtSite =
            (String) ReflectionTestUtils.getField(classUnderTest, "VIEW_NAME_VIEW_COURT_SITE");
        mappingNameViewCourtSiteUrl = REQUEST_MAPPING
            + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_VIEW_COURT");
        viewNameCreateCourt =
            (String) ReflectionTestUtils.getField(classUnderTest, "VIEW_NAME_CREATE_COURT");
        mappingNameCreateCourtUrl = REQUEST_MAPPING
            + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_CREATE_COURT");
        viewNameAmendCourt =
            (String) ReflectionTestUtils.getField(classUnderTest, "VIEW_NAME_AMEND_COURT");
        mappingNameAmendCourtUrl = REQUEST_MAPPING
            + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_AMEND_COURT");

        // Stop circular view path error
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view");
        viewResolver.setSuffix(".jsp");

        // Setup the mock version of the modelMvc
        mockMvc =
            MockMvcBuilders.standaloneSetup(classUnderTest).setViewResolvers(viewResolver).build();
    }

    protected void mockObjects() {
        mockCourtSelectedValidator = createMock(CourtSelectedValidator.class);
        mockCourtCreateValidator = createMock(CourtCreateValidator.class);
        mockCourtAmendValidator = createMock(CourtAmendValidator.class);
        mockCourtPageStateHolder = createMock(CourtPageStateHolder.class);
        mockCourtService = createMock(CourtService.class);
    }

    protected List<CourtDto> getCourtDtoList() {
        CourtDto courtDto = new CourtDto();
        courtDto.setCourtName("court_name");
        courtDto.setAddressId(1);
        courtDto.setId(3);
        ArrayList<CourtDto> courtDtos = new ArrayList<>();
        courtDtos.add(courtDto);
        return courtDtos;
    }
}
