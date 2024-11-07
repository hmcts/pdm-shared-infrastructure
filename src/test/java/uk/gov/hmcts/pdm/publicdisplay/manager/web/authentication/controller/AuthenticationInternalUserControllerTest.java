package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthConfigurationPropertiesStrategy;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model.SecurityToken;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.service.AuthenticationService;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class AuthenticationInternalUserController.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationInternalUserControllerTest extends AbstractJUnit {

    private static final String NOTNULL = "Result is Null";

    @Mock
    private URI mockUri;

    @Mock
    private AuthenticationService mockAuthenticationService;

    @Mock
    private InternalAuthConfigurationPropertiesStrategy mockInternalAuthConfigurationPropertiesStrategy;

    @InjectMocks
    private AuthenticationInternalUserController classUnderTest;

    @Test
    void testLoginOrRefresh() {
        String authHeaderValue = "Bearer : 1";
        String redirectUri = "redirectUri";
        // Expects
        Mockito.when(mockAuthenticationService.loginOrRefresh(Mockito.isA(String.class),
            Mockito.isA(String.class))).thenReturn(mockUri);
        Mockito.when(mockAuthenticationService.loginOrRefresh(Mockito.isNull(),
            Mockito.isA(String.class))).thenReturn(mockUri);
        // Run
        ModelAndView result = classUnderTest.loginOrRefresh(authHeaderValue, redirectUri);
        assertNotNull(result, NOTNULL);
        result = classUnderTest.loginOrRefresh(null, redirectUri);
        assertNotNull(result, NOTNULL);
    }
    
    @Test
    void testhandleOauthCode() {
        String code = "code";
        // Run
        SecurityToken result = classUnderTest.handleOauthCode(code);
        assertNotNull(result, NOTNULL);
    }
    
    @Test
    void testLogout() {
        String authHeaderValue = "Bearer : 1";
        String redirectUri = "redirectUri";
        // Expects
        Mockito.when(mockAuthenticationService.logout(Mockito.isA(String.class),
            Mockito.isA(String.class))).thenReturn(mockUri);
        // Run
        ModelAndView result = classUnderTest.logout(authHeaderValue, redirectUri);
        assertNotNull(result, NOTNULL);
    }
    
}
