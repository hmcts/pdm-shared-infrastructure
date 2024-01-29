package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import org.easymock.Capture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.CourtRoomService;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICourtRoomService;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class CourtRoomControllerTest extends AbstractJUnit {

    private CourtRoomSelectedValidator mockCourtRoomSelectedValidator;
    private CourtRoomCreateValidator mockCourtRoomCreateValidator;
    private CourtRoomDeleteValidator mockCourtRoomDeleteValidator;
    private CourtRoomAmendValidator mockCourtRoomAmendValidator;

    private CourtRoomPageStateHolder mockCourtRoomPageStateHolder;

    private ICourtRoomService mockCourtRoomService;

    private String requestMapping;
    private String mappingCreateCourtRoom;
    private String mappingViewCourtRoom;
    private String mappingDeleteCourtRoom;
    private String mappingAmendCourtRoom;
    private String viewNameViewCourtRoom;
    private String viewNameCreateCourtRoom;
    private String viewNameDeleteCourtRoom;
    private String viewNameAmendCourtRoom;

    private MockMvc mockMvc;

    private final List<CourtDto> courtDtoList = getCourtDtoList();


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
        requestMapping = (String) ReflectionTestUtils.getField(classUnderTest, "REQUEST_MAPPING");
        mappingCreateCourtRoom = (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_CREATE_COURTROOM");
        mappingViewCourtRoom = (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_VIEW_COURTROOM");
        mappingDeleteCourtRoom = (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_DELETE_COURTROOM");
        mappingAmendCourtRoom = (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_AMEND_COURTROOM");
        viewNameViewCourtRoom =
                (String) ReflectionTestUtils.getField(classUnderTest, "REQUEST_MAPPING") + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_VIEW_COURTROOM");
        viewNameCreateCourtRoom =
                (String) ReflectionTestUtils.getField(classUnderTest, "REQUEST_MAPPING") + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_CREATE_COURTROOM");
        viewNameDeleteCourtRoom =
                (String) ReflectionTestUtils.getField(classUnderTest, "REQUEST_MAPPING") + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_DELETE_COURTROOM");
        viewNameAmendCourtRoom =
                (String) ReflectionTestUtils.getField(classUnderTest, "REQUEST_MAPPING") + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_AMEND_COURTROOM");

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

    private List<CourtDto> getCourtDtoList() {
        ArrayList<CourtDto> courtDtos = new ArrayList<>();
        CourtDto courtDto = new CourtDto();
        courtDto.setCourtName("court_name");
        courtDto.setAddressId(1);
        courtDto.setId(2);
        courtDtos.add(courtDto);
        return courtDtos;
    }

    @Test
    void viewCourtRoomTest() throws Exception {

        Capture<List<CourtDto>> capturedCourts = newCapture();

        mockCourtRoomPageStateHolder.reset();
        expectLastCall();
        expect(mockCourtRoomService.getCourts()).andReturn(getCourtDtoList());
        mockCourtRoomPageStateHolder.setCourts(capture(capturedCourts));
        expectLastCall();
        replay(mockCourtRoomService);
        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(courtDtoList);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(get(viewNameViewCourtRoom)).andReturn();
        String returnedViewName = results.getModelAndView().getViewName();

        assertNotNull(results, "Not null");
        assertEquals(viewNameViewCourtRoom, returnedViewName, "Not equal");
        assertEquals(capturedCourts.getValue().get(0), courtDtoList.get(0));

        verify(mockCourtRoomService);
        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void viewCourtRoomResetFalseTest() throws Exception {
        CourtRoomSearchCommand courtRoomSearchCommand = new CourtRoomSearchCommand();

        expect(mockCourtRoomPageStateHolder.getCourtRoomSearchCommand()).andReturn(courtRoomSearchCommand).times(2);
        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(courtDtoList);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(get(viewNameViewCourtRoom).param("reset", "false")).andReturn();
        String returnedViewName = results.getModelAndView().getViewName();

        assertNotNull(results, "Not null");
        assertEquals(viewNameViewCourtRoom, returnedViewName, "Not equal");

        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void viewCourtRoomNullCommandTest() throws Exception {

        Capture<List<CourtDto>> capturedCourts = newCapture();

        expect(mockCourtRoomPageStateHolder.getCourtRoomSearchCommand()).andReturn(null);
        mockCourtRoomPageStateHolder.reset();
        expectLastCall();
        expect(mockCourtRoomService.getCourts()).andReturn(getCourtDtoList());
        mockCourtRoomPageStateHolder.setCourts(capture(capturedCourts));
        expectLastCall();
        replay(mockCourtRoomService);
        expect(mockCourtRoomPageStateHolder.getCourts()).andReturn(courtDtoList);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results = mockMvc.perform(get(viewNameViewCourtRoom).param("reset", "false")).andReturn();
        String returnedViewName = results.getModelAndView().getViewName();

        assertNotNull(results, "Not null");
        assertEquals(viewNameViewCourtRoom, returnedViewName, "Not equal");
        assertEquals(capturedCourts.getValue().get(0).getId(), courtDtoList.get(0).getId());

        verify(mockCourtRoomService);
        verify(mockCourtRoomPageStateHolder);
    }
}
