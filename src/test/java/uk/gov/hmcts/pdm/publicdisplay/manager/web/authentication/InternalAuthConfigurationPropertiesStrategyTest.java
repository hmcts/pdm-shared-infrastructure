package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The Class InternalAuthConfigurationPropertiesStrategy.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InternalAuthConfigurationPropertiesStrategyTest extends AbstractJUnit {

    private static final String FALSE = "Result is True";
    private static final String NOTNULL = "Result is Null";
    private static final String TRUE = "Result is False";

    @Mock
    private InternalAuthProviderConfigurationProperties mockInternalAuthProviderConfigurationProperties;

    private AuthenticationConfigurationPropertiesStrategy classUnderTest;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        mockInternalAuthProviderConfigurationProperties =
            Mockito.mock(InternalAuthProviderConfigurationProperties.class);

        classUnderTest = new InternalAuthConfigurationPropertiesStrategy(
            Mockito.mock(InternalAuthConfigurationProperties.class),
            mockInternalAuthProviderConfigurationProperties);
    }

    @Test
    void testGetConfiguration() {
        AuthConfigurationProperties result = classUnderTest.getConfiguration();
        assertNotNull(result, NOTNULL);
    }

    @Test
    void testGetProviderConfiguration() {
        AuthProviderConfigurationProperties result = classUnderTest.getProviderConfiguration();
        assertNotNull(result, NOTNULL);
    }

    @Test
    void testDoesMatch() {
        StringBuffer validSb = new StringBuffer();
        validSb.append("/login");
        HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockHttpServletRequest.getRequestURL()).thenReturn(validSb);

        boolean result = classUnderTest.doesMatch(mockHttpServletRequest);
        assertTrue(result, TRUE);
    }

    @Test
    void testDoesMatchFailure() {
        StringBuffer validSb = new StringBuffer();
        validSb.append("/invalid");
        HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockHttpServletRequest.getRequestURL()).thenReturn(validSb);

        boolean result = classUnderTest.doesMatch(mockHttpServletRequest);
        assertFalse(result, FALSE);
    }

    @Test
    void testGetLoginUri() {
        Mockito.when(mockInternalAuthProviderConfigurationProperties.getAuthorizationUri())
            .thenReturn("/auth");
        URI result = classUnderTest.getLoginUri("/login");
        assertNotNull(result, NOTNULL);
    }

    @Test
    void testGetLandingPageUri() {
        URI result = classUnderTest.getLandingPageUri();
        assertNotNull(result, NOTNULL);
    }

    @Test
    void testGetLoginoutUri() {
        Mockito.when(mockInternalAuthProviderConfigurationProperties.getLogoutUri())
            .thenReturn("/logout");
        URI result = classUnderTest.getLogoutUri("accessToken", "/redirect");
        assertNotNull(result, NOTNULL);
    }
}
