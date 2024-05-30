package uk.gov.hmcts.pdm.publicdisplay.manager.web.courtel;

import org.easymock.Capture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.CourtelService;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ICourtelService;

import java.util.Map;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class CourtelControllerTest extends AbstractJUnit {

    protected ICourtelService mockCourtelService;
    protected MockMvc mockMvc;
    protected String viewNameViewCourtel;
    protected String mappingNameViewCourtelUrl;
    protected String mappingNameAmendCourtelUrl;
    protected static final String REQUEST_MAPPING = "/courtel";
    protected static final String NOT_NULL = "Not null";
    protected static final String NULL = "Null";
    protected static final String NOT_EQUAL = "Not equal";
    protected static final String FALSE = "false";
    protected static final String ADD = "add";
    protected static final String COMMAND = "command";
    protected static final String NOT_AN_INSTANCE = "Not an instance";
    protected static final String COURTEL_LIST_AMOUNT = "courtelListAmount";
    protected static final String COURTEL_MAX_RETRY = "courtelMaxRetry";
    protected static final String MESSAGE_LOOKUP_DELAY = "messageLookupDelay";

    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        CourtelController classUnderTest = new CourtelController();
        // Setup the mock version of the called classes
        mockCourtelService = createMock(CourtelService.class);
        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "courtelService", mockCourtelService);

        // Get the static variables from the class under test
        viewNameViewCourtel =
                (String) ReflectionTestUtils.getField(classUnderTest, "VIEW_NAME_VIEW_COURTEL");
        mappingNameViewCourtelUrl = REQUEST_MAPPING
                + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_VIEW_COURTEL");
        mappingNameAmendCourtelUrl = REQUEST_MAPPING
                + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_AMEND_COURTEL");

        // Stop circular view path error
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view");
        viewResolver.setSuffix(".jsp");

        // Setup the mock version of the modelMvc
        mockMvc =
                MockMvcBuilders.standaloneSetup(classUnderTest).setViewResolvers(viewResolver).build();
    }

    @Test
    void viewCourtelTest() throws Exception {

        // Perform the test
        final MvcResult results = mockMvc.perform(get(mappingNameViewCourtelUrl)).andReturn();
        String returnedViewName = results.getModelAndView().getViewName();

        assertNotNull(results, NULL);
        assertInstanceOf(CourtelAmendCommand.class, results.getModelAndView().getModel().get(COMMAND),
                NOT_AN_INSTANCE);
        assertEquals(viewNameViewCourtel, returnedViewName, NOT_EQUAL);

    }

    @Test
    void updateCourtelTest() throws Exception {
        final Capture<CourtelAmendCommand> capturedCourtelAmendCommand = newCapture();

        mockCourtelService.updateCourtelListAmount(capture(capturedCourtelAmendCommand));
        expectLastCall();
        mockCourtelService.updateCourtelMaxRetry(capture(capturedCourtelAmendCommand));
        expectLastCall();
        mockCourtelService.updateMessageLookupDelay(capture(capturedCourtelAmendCommand));
        expectLastCall();

        replay(mockCourtelService);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(mappingNameAmendCourtelUrl)
                                                 .param("name", "courtelAmendCommandName")
                                                 .param(COURTEL_LIST_AMOUNT, "1")
                                                 .param(COURTEL_MAX_RETRY, "2")
                                                 .param(MESSAGE_LOOKUP_DELAY, "3")
                                                 .param("btnUpdateConfirm", ADD))
                                         .andReturn();
        final Map<String, Object> model = results.getModelAndView().getModel();

        assertNotNull(results.getModelAndView().getViewName(), NOT_NULL);
        assertEquals(viewNameViewCourtel, results.getModelAndView().getViewName(), NOT_EQUAL);
        assertEquals("1", capturedCourtelAmendCommand.getValue().getCourtelListAmount(), NOT_EQUAL);
        assertEquals("2", capturedCourtelAmendCommand.getValue().getCourtelMaxRetry(), NOT_EQUAL);
        assertEquals("3", capturedCourtelAmendCommand.getValue().getMessageLookupDelay(), NOT_EQUAL);
        assertEquals("Courtel has been updated successfully.", model.get("successMessage"), NOT_EQUAL);
        assertInstanceOf(CourtelAmendCommand.class, results.getModelAndView().getModel().get(COMMAND),
                NOT_AN_INSTANCE);

        verify(mockCourtelService);
    }

    @Test
    void updateCourtelErrorTest() throws Exception {

        // Perform the test
        final MvcResult results = mockMvc.perform(post(mappingNameAmendCourtelUrl)
                                                 .param(COURTEL_LIST_AMOUNT, "a")
                                                 .param(COURTEL_MAX_RETRY, "b")
                                                 .param(MESSAGE_LOOKUP_DELAY, "c")
                                                 .param("btnUpdateConfirm", ADD))
                                         .andReturn();

        BeanPropertyBindingResult bindingResult =
                (BeanPropertyBindingResult) results.getModelAndView().getModel().get("org"
                        + ".springframework.validation.BindingResult.courtelAmendCommand");

        assertNotNull(results.getModelAndView().getViewName(), NOT_NULL);
        assertEquals(viewNameViewCourtel, results.getModelAndView().getViewName(), NOT_EQUAL);
        assertEquals(3, bindingResult.getErrorCount(), NOT_EQUAL);
        assertEquals("{courtelAmendCommand.courtelListAmount.notNumber}",
                bindingResult.getFieldError(COURTEL_LIST_AMOUNT).getDefaultMessage(), NOT_EQUAL);
        assertEquals("{courtelAmendCommand.courtelMaxRetry.notNumber}",
                bindingResult.getFieldError(COURTEL_MAX_RETRY).getDefaultMessage(), NOT_EQUAL);
        assertEquals("{courtelAmendCommand.messageLookupDelay.notNumber}",
                bindingResult.getFieldError(MESSAGE_LOOKUP_DELAY).getDefaultMessage(), NOT_EQUAL);
        assertInstanceOf(CourtelAmendCommand.class, results.getModelAndView().getModel().get(COMMAND),
                NOT_AN_INSTANCE);
    }

    @Test
    void updateCourtelExceptionTest() throws Exception {

        final Capture<CourtelAmendCommand> capturedCourtelAmendCommand = newCapture();

        mockCourtelService.updateCourtelListAmount(capture(capturedCourtelAmendCommand));
        expectLastCall().andThrow(new DataAccessException("Courtel Update Error") {});

        replay(mockCourtelService);

        // Perform the test
        final MvcResult results = mockMvc.perform(post(mappingNameAmendCourtelUrl)
                                                 .param("name", "courtelAmendCommandName")
                                                 .param(COURTEL_LIST_AMOUNT, "1")
                                                 .param(COURTEL_MAX_RETRY, "2")
                                                 .param(MESSAGE_LOOKUP_DELAY, "3")
                                                 .param("btnUpdateConfirm", ADD))
                                         .andReturn();

        BeanPropertyBindingResult bindingResult =
                (BeanPropertyBindingResult) results.getModelAndView().getModel().get("org"
                        + ".springframework.validation.BindingResult.courtelAmendCommand");

        assertEquals("Unable to update Courtel: Courtel Update Error",
                bindingResult.getAllErrors().get(0).getDefaultMessage(), NOT_EQUAL);
        assertEquals("1", capturedCourtelAmendCommand.getValue().getCourtelListAmount(), NOT_EQUAL);
        assertEquals("2", capturedCourtelAmendCommand.getValue().getCourtelMaxRetry(), NOT_EQUAL);
        assertEquals("3", capturedCourtelAmendCommand.getValue().getMessageLookupDelay(), NOT_EQUAL);
        assertInstanceOf(CourtelAmendCommand.class, results.getModelAndView().getModel().get(COMMAND),
                NOT_AN_INSTANCE);

        verify(mockCourtelService);
    }
}