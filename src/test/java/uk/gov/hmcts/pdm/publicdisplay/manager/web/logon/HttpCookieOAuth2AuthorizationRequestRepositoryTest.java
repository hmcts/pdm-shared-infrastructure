package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.logon.WebSecurityConfigTest.LocalWebSecurityConfig;
import java.util.Optional;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The Class HttpCookieOAuth2AuthorizationRequestRepository.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HttpCookieOAuth2AuthorizationRequestRepositoryTest extends AbstractJUnit {

    private static final String NAME = "NAME";
    private static final String INVALID = "INVALID";
    private static final String VALUE = "Value";
    private static final String NOTNULL = "Result is Null";
    private static final String TRUE = "Result is False";

    @Mock
    private HttpServletRequest mockHttpServletRequest;

    @Mock
    private HttpServletResponse mockHttpServletResponse;

    @Mock
    private Cookie mockCookie;

    @Mock
    private OAuth2AuthorizationRequest mockOAuth2AuthorizationRequest;

    @InjectMocks
    private HttpCookieOAuth2AuthorizationRequestRepository classUnderTest =
        new HttpCookieOAuth2AuthorizationRequestRepository();

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        Mockito.mockStatic(CookieUtils.class);
    }

    /**
     * Teardown.
     */
    @AfterEach
    public void teardown() {
        Mockito.clearAllCaches();
    }

    @Test
    void testLoadAuthorizationRequest() {
        Mockito.when(CookieUtils.getCookie(mockHttpServletRequest,
            HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME))
            .thenReturn(Optional.of(mockCookie));
        Mockito.when(CookieUtils.deserialize(mockCookie, OAuth2AuthorizationRequest.class))
            .thenReturn(mockOAuth2AuthorizationRequest);
        OAuth2AuthorizationRequest result = classUnderTest
            .removeAuthorizationRequest(mockHttpServletRequest, mockHttpServletResponse);
        assertNotNull(result, NOTNULL);
    }

    @Test
    void testSaveAuthorizationRequest() {
        boolean result = testSaveAuthorizationRequest("Test");
        assertTrue(result, TRUE);
    }
    
    @Test
    void testSaveAuthorizationRequestNull() {
        boolean result = testSaveAuthorizationRequest(null);
        assertTrue(result, TRUE);
    }

    private boolean testSaveAuthorizationRequest(String value) {
        Mockito
            .when(mockHttpServletRequest.getParameter(
                HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME))
            .thenReturn(value);
        boolean result = false;
        try {
            classUnderTest.saveAuthorizationRequest(null, mockHttpServletRequest,
                mockHttpServletResponse);
            classUnderTest.saveAuthorizationRequest(mockOAuth2AuthorizationRequest,
                mockHttpServletRequest, mockHttpServletResponse);
            result = true;
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        return result;
    }

    @Test
    void testRemoveAuthorizationRequestCookies() {
        boolean result = false;
        try {
            classUnderTest.removeAuthorizationRequestCookies(mockHttpServletRequest,
                mockHttpServletResponse);
            result = true;
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        assertTrue(result, TRUE);
    }
}
