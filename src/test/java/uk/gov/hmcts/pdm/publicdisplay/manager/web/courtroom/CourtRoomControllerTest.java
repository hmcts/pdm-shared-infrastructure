package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import org.easymock.Capture;
import org.easymock.IAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtRoomDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.XhibitCourtSiteDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.CourtRoomService;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICourtRoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class CourtRoomControllerTest extends AbstractJUnit {

    private CourtRoomSelectedValidator mockCourtRoomSelectedValidator;
    private CourtRoomCreateValidator mockCourtRoomCreateValidator;
    private CourtRoomDeleteValidator mockCourtRoomDeleteValidator;
    private CourtRoomAmendValidator mockCourtRoomAmendValidator;
    private CourtRoomPageStateHolder mockCourtRoomPageStateHolder;
    private ICourtRoomService mockCourtRoomService;
    private String viewNameViewCourtRoom;
    private String viewNameCreateCourtRoom;
    private String viewNameAmendCourtRoom;
    private String viewNameDeleteCourtRoom;
    private MockMvc mockMvc;
    private static final String NOT_NULL = "Not null";
    private static final String NOT_EQUAL = "Not equal";
    private static final String FALSE = "false";
    private static final String RESET = "reset";
    private static final String COURT_ID = "courtId";
    private static final String ADD = "add";
    private static final String NOT_FALSE = "Not false";
    private static final String COMMAND = "command";
    private static final String NOT_AN_INSTANCE = "Not an instance";
    private static final String COURTSITE_LIST = "courtSiteList";
    private static final String COURT = "court";
    private static final String THREE = "3";
    private static final String MOCK_ERROR_MESSAGE = "mock error message";
    private static final String COURTROOM_DTO_DESCRIPTION = "courtRoomDtoDescription";
    private static final String XHIBIT_COURTSITE_ID = "xhibitCourtSiteId";
    private static final String DESCRIPTION = "description";
    private static final String CREATE_COMMAND_DESCRIPTION = "courtRoomCreateCommandDescription";


    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        CourtRoomController classUnderTest = new CourtRoomController();
        // Setup the mock version of the called classes
        mockObjects();
        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "courtRoomSelectedValidator", mockCourtRoomSelectedValidator);
        ReflectionTestUtils.setField(classUnderTest, "courtRoomCreateValidator", mockCourtRoomCreateValidator);
        ReflectionTestUtils.setField(classUnderTest, "courtRoomDeleteValidator", mockCourtRoomDeleteValidator);
        ReflectionTestUtils.setField(classUnderTest, "courtRoomAmendValidator", mockCourtRoomAmendValidator);
        ReflectionTestUtils.setField(classUnderTest, "courtRoomPageStateHolder", mockCourtRoomPageStateHolder);
        ReflectionTestUtils.setField(classUnderTest, "courtRoomService", mockCourtRoomService);

        // Get the static variables from the class under test
        viewNameViewCourtRoom =
                ReflectionTestUtils.getField(classUnderTest, "REQUEST_MAPPING")
                        + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_VIEW_COURTROOM");

        viewNameCreateCourtRoom =
                ReflectionTestUtils.getField(classUnderTest, "REQUEST_MAPPING")
                        + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_CREATE_COURTROOM");

        viewNameAmendCourtRoom =
                ReflectionTestUtils.getField(classUnderTest, "REQUEST_MAPPING")
                        + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_AMEND_COURTROOM");

        viewNameDeleteCourtRoom =
                ReflectionTestUtils.getField(classUnderTest, "REQUEST_MAPPING")
                        + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_DELETE_COURTROOM");

        // Stop circular view path error
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view");
        viewResolver.setSuffix(".jsp");

        // Setup the mock version of the modelMvc
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).setViewResolvers(viewResolver).build();
    }

    protected void mockObjects() {
        mockCourtRoomSelectedValidator = createMock(CourtRoomSelectedValidator.class);
        mockCourtRoomCreateValidator = createMock(CourtRoomCreateValidator.class);
        mockCourtRoomDeleteValidator = createMock(CourtRoomDeleteValidator.class);
        mockCourtRoomAmendValidator = createMock(CourtRoomAmendValidator.class);
        mockCourtRoomPageStateHolder = createMock(CourtRoomPageStateHolder.class);
        mockCourtRoomService = createMock(CourtRoomService.class);
    }

    @Test
    void viewCourtRoomTest() throws Exception {
        mockCourtRoomPageStateHolder.reset();
        Capture<List<CourtDto>> capturedCourts = newCapture();
        expectLastCall();
        expect(mockCourtRoomService.getCourts()).andReturn(getCourtDtoList());
        mockCourtRoomPageStateHolder.setCourts(capture(capturedCourts));
        expectLastCall();
        replay(mockCourtRoomService);
        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(getCourtDtoList());
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(get(viewNameViewCourtRoom)).andReturn();
        String returnedViewName = results.getModelAndView().getViewName();

        assertNotNull(results, NOT_NULL);
        assertEquals(viewNameViewCourtRoom, returnedViewName, NOT_EQUAL);
        assertEquals(capturedCourts.getValue().get(0).getId(), getCourtDtoList().get(0).getId(), NOT_EQUAL);

        verify(mockCourtRoomService);
        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void viewCourtRoomResetFalseTest() throws Exception {
        CourtRoomSearchCommand courtRoomSearchCommand = new CourtRoomSearchCommand();

        expect(mockCourtRoomPageStateHolder.getCourtRoomSearchCommand()).andReturn(courtRoomSearchCommand).times(2);
        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(getCourtDtoList());
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(get(viewNameViewCourtRoom).param(RESET, FALSE)).andReturn();
        String returnedViewName = results.getModelAndView().getViewName();

        assertNotNull(results, NOT_NULL);
        assertEquals(viewNameViewCourtRoom, returnedViewName, NOT_EQUAL);

        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void viewCourtRoomNullCommandTest() throws Exception {
        expect(mockCourtRoomPageStateHolder.getCourtRoomSearchCommand()).andReturn(null);
        mockCourtRoomPageStateHolder.reset();
        Capture<List<CourtDto>> capturedCourts = newCapture();
        expectLastCall();
        expect(mockCourtRoomService.getCourts()).andReturn(getCourtDtoList());
        mockCourtRoomPageStateHolder.setCourts(capture(capturedCourts));
        expectLastCall();
        replay(mockCourtRoomService);
        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(getCourtDtoList());
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(get(viewNameViewCourtRoom).param(RESET, FALSE)).andReturn();
        String returnedViewName = results.getModelAndView().getViewName();

        assertNotNull(results, NOT_NULL);
        assertEquals(viewNameViewCourtRoom, returnedViewName, NOT_EQUAL);
        assertEquals(capturedCourts.getValue().get(0).getId(), getCourtDtoList().get(0).getId(), NOT_EQUAL);

        verify(mockCourtRoomService);
        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void showCreateCourtRoomTest() throws Exception {
        Capture<CourtRoomSearchCommand> capturedCourtRoomSearchCommand = newCapture();
        final Capture<BindingResult> capturedErrors = newCapture();
        final Capture<List<XhibitCourtSiteDto>> capturedCourtSites = newCapture();

        mockCourtRoomPageStateHolder.setCourtRoomSearchCommand(capture(capturedCourtRoomSearchCommand));
        mockCourtRoomSelectedValidator.validate(capture(capturedCourtRoomSearchCommand), capture(capturedErrors));
        expectLastCall();

        List<CourtDto> courtDtos = getCourtDtoList();
        List<XhibitCourtSiteDto> xhibitCourtSiteDtos = List.of(new XhibitCourtSiteDto());

        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(courtDtos);
        replay(mockCourtRoomSelectedValidator);
        expect(mockCourtRoomService.getCourtSites(3)).andReturn(xhibitCourtSiteDtos);
        replay(mockCourtRoomService);
        mockCourtRoomPageStateHolder.setSites(capture(capturedCourtSites));
        expectLastCall();
        mockCourtRoomPageStateHolder.setCourt(courtDtos.get(0));
        expectLastCall();
        expect(mockCourtRoomPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(viewNameViewCourtRoom)
                .param(COURT_ID, THREE)
                .param("btnAdd", ADD)).andReturn();
        ModelAndView modelAndView = results.getModelAndView();

        assertNotNull(modelAndView.getViewName(), NOT_NULL);
        assertEquals(viewNameCreateCourtRoom, modelAndView.getViewName(), NOT_EQUAL);
        assertFalse(capturedErrors.getValue().hasErrors(), NOT_FALSE);
        assertEquals(xhibitCourtSiteDtos.get(0).getCourtId(),
                capturedCourtSites.getValue().get(0).getCourtId(), NOT_EQUAL);
        assertEquals(3, capturedCourtRoomSearchCommand.getValue().getCourtId(), NOT_EQUAL);
        assertInstanceOf(CourtRoomCreateCommand.class,
                modelAndView.getModel().get(COMMAND), NOT_AN_INSTANCE);
        assertEquals(xhibitCourtSiteDtos, modelAndView.getModel().get(COURTSITE_LIST), NOT_EQUAL);
        assertEquals(courtDtos.get(0), modelAndView.getModel().get(COURT), NOT_EQUAL);

        verify(mockCourtRoomService);
        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void showCreateCourtRoomWithErrorsTest() throws Exception {
        Capture<CourtRoomSearchCommand> capturedCourtRoomSearchCommand = newCapture();
        final Capture<BindingResult> capturedErrors = newCapture();

        mockCourtRoomPageStateHolder.setCourtRoomSearchCommand(capture(capturedCourtRoomSearchCommand));
        mockCourtRoomSelectedValidator.validate(capture(capturedCourtRoomSearchCommand), capture(capturedErrors));
        expectLastCall().andAnswer((IAnswer<Void>) () -> {
            ((BindingResult) getCurrentArguments()[1]).reject(MOCK_ERROR_MESSAGE);
            return null;
        });
        List<CourtDto> courtDtos = getCourtDtoList();

        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(courtDtos);
        replay(mockCourtRoomSelectedValidator);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(viewNameViewCourtRoom)
                .param(COURT_ID, THREE)
                .param("btnAdd", ADD)).andReturn();
        ModelAndView modelAndView = results.getModelAndView();

        assertEquals(courtDtos, modelAndView.getModel().get("courtList"), NOT_EQUAL);
        assertNotNull(modelAndView.getViewName(), NOT_NULL);
        assertEquals(viewNameViewCourtRoom, modelAndView.getViewName(), NOT_EQUAL);
        assertEquals(1, capturedErrors.getValue().getErrorCount());

        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void showAmendCourtRoomTest() throws Exception {
        Capture<CourtRoomSearchCommand> capturedCourtRoomSearchCommand = newCapture();
        final Capture<BindingResult> capturedErrors = newCapture();
        final Capture<List<XhibitCourtSiteDto>> capturedCourtSites = newCapture();

        mockCourtRoomPageStateHolder.setCourtRoomSearchCommand(capture(capturedCourtRoomSearchCommand));
        mockCourtRoomSelectedValidator.validate(capture(capturedCourtRoomSearchCommand), capture(capturedErrors));
        expectLastCall();

        List<CourtDto> courtDtos = getCourtDtoList();
        List<XhibitCourtSiteDto> xhibitCourtSiteDtos = List.of(new XhibitCourtSiteDto());

        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(courtDtos);
        replay(mockCourtRoomSelectedValidator);
        expect(mockCourtRoomService.getCourtSites(3)).andReturn(xhibitCourtSiteDtos);
        replay(mockCourtRoomService);
        mockCourtRoomPageStateHolder.setSites(capture(capturedCourtSites));
        expectLastCall();
        mockCourtRoomPageStateHolder.setCourt(courtDtos.get(0));
        expectLastCall();
        expect(mockCourtRoomPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(viewNameViewCourtRoom)
                .param(COURT_ID, THREE)
                .param("btnAmend", ADD)).andReturn();
        Map<String, Object> model = results.getModelAndView().getModel();

        assertNotNull(results.getModelAndView().getViewName(), NOT_NULL);
        assertEquals(viewNameAmendCourtRoom, results.getModelAndView().getViewName(), NOT_EQUAL);
        assertFalse(capturedErrors.getValue().hasErrors(), NOT_FALSE);
        assertEquals(xhibitCourtSiteDtos.get(0).getCourtId(),
                capturedCourtSites.getValue().get(0).getCourtId(), NOT_EQUAL);
        assertEquals(3, capturedCourtRoomSearchCommand.getValue().getCourtId(), NOT_EQUAL);
        assertInstanceOf(CourtRoomAmendCommand.class,
                model.get(COMMAND), NOT_AN_INSTANCE);
        assertEquals(xhibitCourtSiteDtos, model.get(COURTSITE_LIST), NOT_EQUAL);
        assertEquals(courtDtos.get(0), model.get(COURT), NOT_EQUAL);
        assertEquals(new ArrayList<CourtRoomDto>() , model.get("courtRoomList") );

        verify(mockCourtRoomService);
        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void showAmendCourtRoomWithErrorsTest() throws Exception {
        Capture<CourtRoomSearchCommand> capturedCourtRoomSearchCommand = newCapture();
        final Capture<BindingResult> capturedErrors = newCapture();

        mockCourtRoomPageStateHolder.setCourtRoomSearchCommand(capture(capturedCourtRoomSearchCommand));
        mockCourtRoomSelectedValidator.validate(capture(capturedCourtRoomSearchCommand), capture(capturedErrors));
        expectLastCall().andAnswer((IAnswer<Void>) () -> {
            ((BindingResult) getCurrentArguments()[1]).reject(MOCK_ERROR_MESSAGE);
            return null;
        });

        List<CourtDto> courtDtos = getCourtDtoList();

        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(courtDtos);
        replay(mockCourtRoomSelectedValidator);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(viewNameViewCourtRoom)
                .param(COURT_ID, THREE)
                .param("btnAmend", ADD)).andReturn();
        String viewName = results.getModelAndView().getViewName();

        assertNotNull(viewName, NOT_NULL);
        assertEquals(viewNameViewCourtRoom, viewName, NOT_EQUAL);
        assertEquals(1, capturedErrors.getValue().getErrorCount());
        assertEquals(3, capturedCourtRoomSearchCommand.getValue().getCourtId(), NOT_EQUAL);

        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void showDeleteCourtRoomTest() throws Exception {
        Capture<CourtRoomSearchCommand> capturedCourtRoomSearchCommand = newCapture();
        final Capture<BindingResult> capturedErrors = newCapture();
        final Capture<List<XhibitCourtSiteDto>> capturedCourtSites = newCapture();

        mockCourtRoomPageStateHolder.setCourtRoomSearchCommand(capture(capturedCourtRoomSearchCommand));
        mockCourtRoomPageStateHolder.setCourtRoomsList(anyObject());
        expectLastCall();
        mockCourtRoomSelectedValidator.validate(capture(capturedCourtRoomSearchCommand), capture(capturedErrors));
        expectLastCall();

        List<CourtDto> courtDtos = getCourtDtoList();
        List<XhibitCourtSiteDto> xhibitCourtSiteDtos = List.of(new XhibitCourtSiteDto());

        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(courtDtos);
        replay(mockCourtRoomSelectedValidator);
        expect(mockCourtRoomService.getCourtSites(3)).andReturn(xhibitCourtSiteDtos);
        replay(mockCourtRoomService);
        mockCourtRoomPageStateHolder.setSites(capture(capturedCourtSites));
        expectLastCall();
        mockCourtRoomPageStateHolder.setCourt(courtDtos.get(0));
        expectLastCall();
        expect(mockCourtRoomPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        expect(mockCourtRoomPageStateHolder.getCourtRoomsList()).andReturn(new ArrayList<>());
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(viewNameViewCourtRoom)
                .param(COURT_ID, THREE)
                .param("btnDelete", ADD)).andReturn();
        Map<String, Object> model = results.getModelAndView().getModel();

        assertNotNull(results.getModelAndView().getViewName(), NOT_NULL);
        assertEquals(viewNameDeleteCourtRoom, results.getModelAndView().getViewName(), NOT_EQUAL);
        assertFalse(capturedErrors.getValue().hasErrors(), NOT_FALSE);
        assertEquals(xhibitCourtSiteDtos.get(0).getCourtId(),
                capturedCourtSites.getValue().get(0).getCourtId(), NOT_EQUAL);
        assertEquals(3, capturedCourtRoomSearchCommand.getValue().getCourtId(), NOT_EQUAL);
        assertInstanceOf(CourtRoomAmendCommand.class,
                model.get(COMMAND), NOT_AN_INSTANCE);
        assertEquals(xhibitCourtSiteDtos, model.get(COURTSITE_LIST), NOT_EQUAL);
        assertEquals(courtDtos.get(0), model.get(COURT), NOT_EQUAL);
        assertEquals(new ArrayList<CourtRoomDto>() , model.get("courtRoomList") );

        verify(mockCourtRoomService);
        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void showDeleteCourtRoomWithErrorsTest() throws Exception {
        Capture<CourtRoomSearchCommand> capturedCourtRoomSearchCommand = newCapture();
        final Capture<BindingResult> capturedErrors = newCapture();

        mockCourtRoomPageStateHolder.setCourtRoomSearchCommand(capture(capturedCourtRoomSearchCommand));
        mockCourtRoomPageStateHolder.setCourtRoomsList(anyObject());
        mockCourtRoomSelectedValidator.validate(capture(capturedCourtRoomSearchCommand), capture(capturedErrors));
        expectLastCall().andAnswer((IAnswer<Void>) () -> {
            ((BindingResult) getCurrentArguments()[1]).reject(MOCK_ERROR_MESSAGE);
            return null;
        });
        List<CourtDto> courtDtos = getCourtDtoList();

        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(courtDtos);
        replay(mockCourtRoomSelectedValidator);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(viewNameViewCourtRoom)
                .param(COURT_ID, THREE)
                .param("btnDelete", ADD)).andReturn();
        String viewName = results.getModelAndView().getViewName();

        assertNotNull(viewName, NOT_NULL);
        assertEquals(viewNameViewCourtRoom, viewName, NOT_EQUAL);
        assertEquals(1, capturedErrors.getValue().getErrorCount());
        assertEquals(3, capturedCourtRoomSearchCommand.getValue().getCourtId(), NOT_EQUAL);

        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void createCourtRoomTest() throws Exception {
        Capture<CourtRoomCreateCommand> capturedCourtRoomCreateCommand = newCapture();
        final Capture<BindingResult> capturedErrors = newCapture();
        final Capture<ICourtRoomService> capturedCourtRoomService = newCapture();
        final Capture<List<CourtDto>> capturedCourtDtoList = newCapture();

        CourtRoomDto courtRoomDto = new CourtRoomDto();
        courtRoomDto.setDescription(COURTROOM_DTO_DESCRIPTION);
        List<CourtRoomDto> courtRoomDtoList = List.of(courtRoomDto);
        List<CourtDto> courtDtos = getCourtDtoList();

        mockCourtRoomCreateValidator.validate(capture(capturedCourtRoomCreateCommand), capture(capturedErrors), capture(capturedCourtRoomService));
        expectLastCall();
        expect(mockCourtRoomService.getCourtRooms(3L)).andReturn(courtRoomDtoList);
        mockCourtRoomService.createCourtRoom(capture(capturedCourtRoomCreateCommand), anyObject());
        expectLastCall();
        expect(mockCourtRoomService.getCourts()).andReturn(getCourtDtoList());
        mockCourtRoomPageStateHolder.setCourts(capture(capturedCourtDtoList));
        expectLastCall();
        mockCourtRoomPageStateHolder.reset();
        expectLastCall();
        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(courtDtos);

        replay(mockCourtRoomCreateValidator);
        replay(mockCourtRoomService);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(viewNameCreateCourtRoom)
                .param(XHIBIT_COURTSITE_ID, THREE)
                        .param(DESCRIPTION, CREATE_COMMAND_DESCRIPTION)
                        .param("name", "courtRoomCreateCommandName")
                .param("btnCreateConfirm", ADD)).andReturn();
        Map<String, Object> model = results.getModelAndView().getModel();

        assertNotNull(results.getModelAndView().getViewName(), NOT_NULL);
        assertEquals(viewNameViewCourtRoom, results.getModelAndView().getViewName(), NOT_EQUAL);
        assertFalse(capturedErrors.getValue().hasErrors(), NOT_FALSE);
        assertEquals(model.get("successMessage"), "Court Room has been created successfully.");
        assertInstanceOf(CourtRoomSearchCommand.class,
                model.get(COMMAND), NOT_AN_INSTANCE);
        assertEquals(3, capturedCourtRoomCreateCommand.getValue().getXhibitCourtSiteId(), NOT_EQUAL);
        assertEquals("court_name", capturedCourtDtoList.getValue().get(0).getCourtName(), NOT_EQUAL);

        verify(mockCourtRoomCreateValidator);
        verify(mockCourtRoomService);
        verify(mockCourtRoomPageStateHolder);
    }



    @Test
    void createCourtRoomWithErrorsTest() throws Exception {
        Capture<CourtRoomCreateCommand> capturedCourtRoomCreateCommand = newCapture();
        final Capture<BindingResult> capturedErrors = newCapture();
        final Capture<ICourtRoomService> capturedCourtRoomService = newCapture();

        mockCourtRoomCreateValidator.validate(capture(capturedCourtRoomCreateCommand), capture(capturedErrors), capture(capturedCourtRoomService));
        expectLastCall().andAnswer((IAnswer<Void>) () -> {
            ((BindingResult) getCurrentArguments()[1]).reject(MOCK_ERROR_MESSAGE);
            return null;
        });
        List<XhibitCourtSiteDto> xhibitCourtSiteDtos = List.of(new XhibitCourtSiteDto());

        expect(mockCourtRoomPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        expect(mockCourtRoomPageStateHolder.getCourt()).andReturn(getCourtDtoList().get(0));

        replay(mockCourtRoomCreateValidator);
        replay(mockCourtRoomService);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(viewNameCreateCourtRoom)
                .param(XHIBIT_COURTSITE_ID, THREE)
                .param(DESCRIPTION, CREATE_COMMAND_DESCRIPTION)
                .param("name", "courtRoomCreateCommandName")
                .param("btnCreateConfirm", ADD)).andReturn();
        String viewName = results.getModelAndView().getViewName();

        assertNotNull(viewName, NOT_NULL);
        assertEquals(viewNameCreateCourtRoom, viewName, NOT_EQUAL);
        assertEquals(1, capturedErrors.getValue().getErrorCount());
        assertInstanceOf(CourtRoomCreateCommand.class,
                results.getModelAndView().getModel().get(COMMAND), NOT_AN_INSTANCE);

        verify(mockCourtRoomCreateValidator);
        verify(mockCourtRoomService);
        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void createCourtRoomExceptionTest() throws Exception {

        Capture<CourtRoomCreateCommand> capturedCourtRoomCreateCommand = newCapture();
        final Capture<BindingResult> capturedErrors = newCapture();
        final Capture<ICourtRoomService> capturedCourtRoomService = newCapture();
        Capture<List<CourtRoomDto>> capturedCourtRoomDtoList = newCapture();

        CourtRoomDto courtRoomDto = new CourtRoomDto();
        courtRoomDto.setDescription(COURTROOM_DTO_DESCRIPTION);
        List<CourtRoomDto> courtRoomDtoList = List.of(courtRoomDto);

        mockCourtRoomCreateValidator.validate(capture(capturedCourtRoomCreateCommand), capture(capturedErrors), capture(capturedCourtRoomService));
        expectLastCall();
        expect(mockCourtRoomService.getCourtRooms(3L)).andReturn(courtRoomDtoList);
        mockCourtRoomService.createCourtRoom(capture(capturedCourtRoomCreateCommand), capture(capturedCourtRoomDtoList));
        expectLastCall().andThrow(new RuntimeException("CourtRoom Creation Error"));

        CourtDto courtDto = new CourtDto();
        List<XhibitCourtSiteDto> xhibitCourtSiteDtos = List.of(new XhibitCourtSiteDto());

        expect(mockCourtRoomPageStateHolder.getSites()).andReturn(xhibitCourtSiteDtos);
        expect(mockCourtRoomPageStateHolder.getCourt()).andReturn(courtDto);

        replay(mockCourtRoomCreateValidator);
        replay(mockCourtRoomService);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(viewNameCreateCourtRoom)
                .param(XHIBIT_COURTSITE_ID, THREE)
                .param(DESCRIPTION, CREATE_COMMAND_DESCRIPTION)
                .param("name", "courtRoomCreateCommandName")
                .param("btnCreateConfirm", ADD)).andReturn();
        Map<String, Object> model = results.getModelAndView().getModel();

        assertNotNull(results.getModelAndView().getViewName(), NOT_NULL);
        assertEquals(viewNameCreateCourtRoom, results.getModelAndView().getViewName(), NOT_EQUAL);
        assertEquals("Unable to create Court Room: CourtRoom Creation Error", capturedErrors.getValue().getAllErrors().get(0).getDefaultMessage());
        assertEquals(3, capturedCourtRoomCreateCommand.getValue().getXhibitCourtSiteId(), NOT_EQUAL);
        assertEquals(COURTROOM_DTO_DESCRIPTION, capturedCourtRoomDtoList.getValue().get(0).getDescription(), NOT_EQUAL);
        assertInstanceOf(CourtRoomCreateCommand.class,
                model.get(COMMAND), NOT_AN_INSTANCE);
        assertEquals(xhibitCourtSiteDtos, model.get(COURTSITE_LIST));
        assertEquals(courtDto, model.get(COURT));

        verify(mockCourtRoomCreateValidator);
        verify(mockCourtRoomService);
        verify(mockCourtRoomPageStateHolder);
    }

    private List<CourtDto> getCourtDtoList() {
        CourtDto courtDto = new CourtDto();
        courtDto.setCourtName("court_name");
        courtDto.setAddressId(1);
        courtDto.setId(3);
        ArrayList<CourtDto> courtDtos = new ArrayList<>();
        courtDtos.add(courtDto);
        return courtDtos;
    }

}

