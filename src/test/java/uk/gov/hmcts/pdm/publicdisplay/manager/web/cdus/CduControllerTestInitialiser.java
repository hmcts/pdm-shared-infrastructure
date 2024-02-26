package uk.gov.hmcts.pdm.publicdisplay.manager.web.cdus;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.CourtSiteDto;

/**
 * The Class CduControllerTestInitialiser.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
class CduControllerTestInitialiser extends CdusControllerTestBase {

    /** The mapping name cdus. */
    protected String mappingNameCdus;

    /** The view name cdus. */
    protected String viewNameCdus;

    /** The mapping name cdus. */
    protected String mappingNameCdusUrl;

    /** The view name amend cdu. */
    protected String viewNameAmendCdu;

    /** The mapping name amend cdu. */
    protected String mappingNameAmendCduUrl;

    /** The view name register cdu. */
    protected String viewNameRegisterCdu;

    /** The mapping name register cdu. */
    protected String mappingNameRegisterCduUrl;

    /** The view name mapping add url. */
    protected String viewNameMappingAddUrl;

    /** The view name mapping remove url. */
    protected String viewNameMappingRemoveUrl;

    /** The view name cdu screenshot. */
    protected String viewNameCduScreenshot;

    /** The mock mvc. */
    protected MockMvc mockMvc;

    /** The court site dto. */
    protected final CourtSiteDto courtSiteDto = getTestCourtSiteDto(1L);

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Create a new version of the class under test
        CdusController classUnderTest = new CdusController();
        // Setup the mock version of the called classes
        mockObjects();
        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "cduSearchValidator", mockCduSearchValidator);
        ReflectionTestUtils.setField(classUnderTest, "cduSearchSelectedValidator",
            mockCduSearchSelectedValidator);
        ReflectionTestUtils.setField(classUnderTest, "cduRestartAllValidator",
            mockCduRestartAllValidator);
        ReflectionTestUtils.setField(classUnderTest, "cduRegisterValidator",
            mockCduRegisterValidator);
        ReflectionTestUtils.setField(classUnderTest, "cduAmendValidator", mockCduAmendValidator);
        ReflectionTestUtils.setField(classUnderTest, "cduUnregisterValidator",
            mockCduUnregisterValidator);
        ReflectionTestUtils.setField(classUnderTest, "mappingAddValidator",
            mockMappingAddValidator);
        ReflectionTestUtils.setField(classUnderTest, "mappingRemoveValidator",
            mockMappingRemoveValidator);
        ReflectionTestUtils.setField(classUnderTest, "cduPageStateHolder", mockCduPageStateHolder);
        ReflectionTestUtils.setField(classUnderTest, "localProxyService", mockLocalProxyService);
        ReflectionTestUtils.setField(classUnderTest, "cduService", mockCduService);
        ReflectionTestUtils.setField(classUnderTest, "urlService", mockUrlService);

        // Get the static variables from the class under test
        mappingNameCdus = (String) ReflectionTestUtils.getField(classUnderTest, "REQUEST_MAPPING");
        mappingNameCdusUrl =
            mappingNameCdus + (String) ReflectionTestUtils.getField(classUnderTest, "VIEW_CDU");
        viewNameCdus = (String) ReflectionTestUtils.getField(classUnderTest, "VIEW_NAME_CDUS");
        mappingNameAmendCduUrl =
            mappingNameCdus + (String) ReflectionTestUtils.getField(classUnderTest, "AMEND_CDU");
        viewNameAmendCdu =
            (String) ReflectionTestUtils.getField(classUnderTest, "VIEW_NAME_AMEND_CDU");
        mappingNameRegisterCduUrl =
            mappingNameCdus + (String) ReflectionTestUtils.getField(classUnderTest, "REGISTER_CDU");
        viewNameRegisterCdu =
            (String) ReflectionTestUtils.getField(classUnderTest, "VIEW_NAME_REGISTER_CDU");
        viewNameMappingAddUrl = mappingNameCdus
            + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_ADD_URL");
        viewNameMappingRemoveUrl = mappingNameCdus
            + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_REMOVE_URL");
        viewNameCduScreenshot = mappingNameCdus
            + (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_CDU_SCREENSHOT");

        // Stop circular view path error
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view");
        viewResolver.setSuffix(".jsp");

        // Setup the mock version of the modelMvc
        mockMvc =
            MockMvcBuilders.standaloneSetup(classUnderTest).setViewResolvers(viewResolver).build();
    }

}
