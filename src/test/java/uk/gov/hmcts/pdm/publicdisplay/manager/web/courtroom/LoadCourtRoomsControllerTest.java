package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtroom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.easymock.Capture;
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
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtRoomDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.DynamicDropdownList;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.DynamicDropdownOption;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
abstract class LoadCourtRoomsControllerTest extends AbstractJUnit {
    protected CourtRoomSelectedValidator mockCourtRoomSelectedValidator;
    protected CourtRoomCreateValidator mockCourtRoomCreateValidator;
    protected CourtRoomDeleteValidator mockCourtRoomDeleteValidator;
    protected CourtRoomAmendValidator mockCourtRoomAmendValidator;
    protected CourtRoomPageStateHolder mockCourtRoomPageStateHolder;
    protected ICourtRoomService mockCourtRoomService;
    protected String viewNameViewCourtRoom;
    protected String viewNameCreateCourtRoom;
    protected String viewNameAmendCourtRoom;
    protected String viewNameDeleteCourtRoom;
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
    protected static final String COURTROOM_DTO_DESCRIPTION = "courtRoomDtoDescription";
    protected static final String XHIBIT_COURTSITE_ID = "xhibitCourtSiteId";
    protected static final String DESCRIPTION = "description";
    protected static final String CREATE_COMMAND_DESCRIPTION = "courtRoomCreateCommandDescription";
    protected static final String COURT_ROOM_CREATE_COMMAND_NAME = "courtRoomCreateCommandName";
    protected static final String NAME = "name";
    protected static final String COURTROOM_ID = "courtRoomId";
    protected static final String REQUEST_MAPPING = "REQUEST_MAPPING";
    protected static final String COURTROOM_NAME = "courtRoomName";

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
                ReflectionTestUtils.getField(classUnderTest, REQUEST_MAPPING)
                        + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_VIEW_COURTROOM");

        viewNameCreateCourtRoom =
                ReflectionTestUtils.getField(classUnderTest, REQUEST_MAPPING)
                        + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_CREATE_COURTROOM");

        viewNameAmendCourtRoom =
                ReflectionTestUtils.getField(classUnderTest, REQUEST_MAPPING)
                        + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_AMEND_COURTROOM");

        viewNameDeleteCourtRoom =
                ReflectionTestUtils.getField(classUnderTest, REQUEST_MAPPING)
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

    protected List<CourtDto> getCourtDtoList() {
        CourtDto courtDto = new CourtDto();
        courtDto.setCourtName("court_name");
        courtDto.setAddressId(1);
        courtDto.setId(3);
        ArrayList<CourtDto> courtDtos = new ArrayList<>();
        courtDtos.add(courtDto);
        return courtDtos;
    }

    @Test
    void loadCourtRoomsForAmendTest() throws Exception {
        CourtRoomDto courtRoomDto = new CourtRoomDto();
        courtRoomDto.setId(1L);
        courtRoomDto.setCourtRoomName(COURTROOM_NAME);
        List<CourtRoomDto> courtRoomDtos = List.of(courtRoomDto);
        DynamicDropdownList dynamicDropdownList = new DynamicDropdownList();
        dynamicDropdownList.setOptions(List.of(new DynamicDropdownOption(1, COURTROOM_NAME)));
        Capture<Long> capturedCourtSiteId = newCapture();
        Capture<List<CourtRoomDto>> capturedCourtRoomList = newCapture();

        expect(mockCourtRoomService.getCourtRooms(capture(capturedCourtSiteId))).andReturn(courtRoomDtos);
        mockCourtRoomPageStateHolder.setCourtRoomsList(capture(capturedCourtRoomList));
        expectLastCall();
        expect(mockCourtRoomPageStateHolder.getCourtRoomsList()).andReturn(courtRoomDtos);
        replay(mockCourtRoomPageStateHolder);
        replay(mockCourtRoomService);

        // Perform the test
        final MvcResult results = mockMvc.perform(get(viewNameAmendCourtRoom + "/3")).andReturn();
        String response = results.getResponse().getContentAsString();
        DynamicDropdownList dynamicDropdownList1 = new ObjectMapper().readValue(response,
                dynamicDropdownList.getClass());
        DynamicDropdownOption dynamicDropdownOption = dynamicDropdownList1.getOptions().get(0);

        assertEquals(dynamicDropdownList.getOptions().get(0).getText(), dynamicDropdownOption.getText(), NOT_EQUAL);
        assertEquals(dynamicDropdownList.getOptions().get(0).getValue(), dynamicDropdownOption.getValue(), NOT_EQUAL);
        assertEquals(3L, capturedCourtSiteId.getValue(), NOT_EQUAL);
        assertEquals(COURTROOM_NAME, capturedCourtRoomList.getValue().get(0).getCourtRoomName(), NOT_EQUAL);
        verify(mockCourtRoomPageStateHolder);
        verify(mockCourtRoomService);
    }

    @Test
    void loadSelectedCourtRoomForAmendTest() throws Exception {
        CourtRoomDto courtRoomDto = new CourtRoomDto();
        courtRoomDto.setId(7L);
        courtRoomDto.setCourtRoomNo(15);
        List<CourtRoomDto> courtRoomDtos = List.of(courtRoomDto);

        expect(mockCourtRoomPageStateHolder.getCourtRoomsList()).andReturn(courtRoomDtos);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results =
                mockMvc.perform(get(viewNameAmendCourtRoom + "/courtRoom/7")).andReturn();
        String response = results.getResponse().getContentAsString();
        CourtRoomDto returnedCourtRoomDto = new ObjectMapper().readValue(response, courtRoomDto.getClass());

        assertEquals(7, returnedCourtRoomDto.getId(), NOT_EQUAL);
        assertEquals(15, returnedCourtRoomDto.getCourtRoomNo(), NOT_EQUAL);
        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void loadSelectedCourtRoomForAmendNullTest() throws Exception {
        CourtRoomDto courtRoomDto = new CourtRoomDto();
        courtRoomDto.setId(8L);
        List<CourtRoomDto> courtRoomDtos = List.of(courtRoomDto);

        expect(mockCourtRoomPageStateHolder.getCourtRoomsList()).andReturn(courtRoomDtos);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results =
                mockMvc.perform(get(viewNameAmendCourtRoom + "/courtRoom/7")).andReturn();

        assertEquals("", results.getResponse().getContentAsString(), NOT_EQUAL);
        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void loadCourtRoomsForDeleteTest() throws Exception {
        CourtRoomDto courtRoomDto = new CourtRoomDto();
        courtRoomDto.setId(1L);
        courtRoomDto.setCourtRoomName(COURTROOM_NAME);
        courtRoomDto.setDescription("courtRoomDescription");
        List<CourtRoomDto> courtRoomDtos = List.of(courtRoomDto);
        DynamicDropdownList dynamicDropdownList = new DynamicDropdownList();
        dynamicDropdownList.setOptions(List.of(new DynamicDropdownOption(1, COURTROOM_NAME)));
        Capture<Long> capturedCourtSiteId = newCapture();
        Capture<List<CourtRoomDto>> capturedCourtRoomList = newCapture();

        expect(mockCourtRoomService.getCourtRooms(capture(capturedCourtSiteId))).andReturn(courtRoomDtos);
        mockCourtRoomPageStateHolder.setCourtRoomsList(capture(capturedCourtRoomList));
        expectLastCall();
        expect(mockCourtRoomPageStateHolder.getCourtRoomsList()).andReturn(courtRoomDtos);
        replay(mockCourtRoomPageStateHolder);
        replay(mockCourtRoomService);

        // Perform the test
        final MvcResult results = mockMvc.perform(get(viewNameDeleteCourtRoom + "/3")).andReturn();
        String response = results.getResponse().getContentAsString();
        DynamicDropdownList dynamicDropdownList1 = new ObjectMapper().readValue(response,
                dynamicDropdownList.getClass());
        DynamicDropdownOption dynamicDropdownOption = dynamicDropdownList1.getOptions().get(0);

        assertEquals(dynamicDropdownList.getOptions().get(0).getText(), dynamicDropdownOption.getText(), NOT_EQUAL);
        assertEquals(dynamicDropdownList.getOptions().get(0).getValue(), dynamicDropdownOption.getValue(), NOT_EQUAL);
        assertEquals(3L, capturedCourtSiteId.getValue(), NOT_EQUAL);
        assertEquals("courtRoomDescription", capturedCourtRoomList.getValue().get(0).getDescription(), NOT_EQUAL);
        verify(mockCourtRoomPageStateHolder);
        verify(mockCourtRoomService);
    }

    @Test
    void loadSelectedCourtRoomForDeleteTest() throws Exception {
        CourtRoomDto courtRoomDto = new CourtRoomDto();
        courtRoomDto.setId(16L);
        courtRoomDto.setCourtRoomNo(19);
        List<CourtRoomDto> courtRoomDtos = List.of(courtRoomDto);

        expect(mockCourtRoomPageStateHolder.getCourtRoomsList()).andReturn(courtRoomDtos);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results =
                mockMvc.perform(get(viewNameDeleteCourtRoom + "/courtRoom/16")).andReturn();
        String response = results.getResponse().getContentAsString();
        CourtRoomDto returnedCourtRoomDto = new ObjectMapper().readValue(response, courtRoomDto.getClass());

        assertEquals(16, returnedCourtRoomDto.getId(), NOT_EQUAL);
        assertEquals(19, returnedCourtRoomDto.getCourtRoomNo(), NOT_EQUAL);
        verify(mockCourtRoomPageStateHolder);
    }

    @Test
    void loadSelectedCourtRoomForDeleteNullTest() throws Exception {
        CourtRoomDto courtRoomDto = new CourtRoomDto();
        courtRoomDto.setId(16L);
        courtRoomDto.setCourtRoomNo(19);
        List<CourtRoomDto> courtRoomDtos = List.of(courtRoomDto);

        expect(mockCourtRoomPageStateHolder.getCourtRoomsList()).andReturn(courtRoomDtos);
        replay(mockCourtRoomPageStateHolder);

        // Perform the test
        final MvcResult results =
                mockMvc.perform(get(viewNameDeleteCourtRoom + "/courtRoom/17")).andReturn();

        assertEquals("", results.getResponse().getContentAsString(), NOT_EQUAL);
        verify(mockCourtRoomPageStateHolder);
    }
}
