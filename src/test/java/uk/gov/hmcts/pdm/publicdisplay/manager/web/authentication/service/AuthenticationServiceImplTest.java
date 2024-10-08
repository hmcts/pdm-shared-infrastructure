package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthProviderConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthConfigurationPropertiesStrategy;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.component.TokenValidator;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.dao.AzureDao;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception.AzureDaoException;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception.PddaApiException;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model.JwtValidationResult;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model.OAuthProviderRawResponse;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * The Class AuthenticationServiceImpl.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationServiceImplTest extends AbstractJUnit {

    private static final String ACCESSCODE = "accessCode";
    private static final String EQUALS = "Result is not Equal";
    private static final String NOTNULL = "Result is Null";
    private static final String TRUE = "Result is False";

    @Mock
    private TokenValidator mockTokenValidator;

    @Mock
    private AzureDao mockAzureDao;

    @Mock
    private URI mockUri;

    @Mock
    private JwtValidationResult mockJwtValidationResult;

    @Mock
    private InternalAuthConfigurationPropertiesStrategy mockInternalAuthConfigurationPropertiesStrategy;

    @Mock
    private AuthProviderConfigurationProperties mockAuthProviderConfigurationProperties;

    @Mock
    private AuthConfigurationProperties mockAuthConfigurationProperties;

    @Mock
    private OAuthProviderRawResponse mockOAuthProviderRawResponse;

    @InjectMocks
    private AuthenticationServiceImpl classUnderTest;

    @Test
    void testloginOrRefresh() {
        boolean result = testloginOrRefresh(ACCESSCODE, true);
        assertTrue(result, TRUE);
    }

    private boolean testloginOrRefresh(String code, boolean isValid) {
        // Expects
        if (code != null) {
            Mockito.when(mockInternalAuthConfigurationPropertiesStrategy.getProviderConfiguration())
                .thenReturn(mockAuthProviderConfigurationProperties);
            Mockito.when(mockInternalAuthConfigurationPropertiesStrategy.getConfiguration())
                .thenReturn(mockAuthConfigurationProperties);
            Mockito.when(mockTokenValidator.validate(code, mockAuthProviderConfigurationProperties,
                mockAuthConfigurationProperties)).thenReturn(mockJwtValidationResult);
            if (isValid) {
                Mockito.when(mockJwtValidationResult.valid()).thenReturn(true);
                Mockito.when(mockInternalAuthConfigurationPropertiesStrategy.getLandingPageUri())
                    .thenReturn(mockUri);
            } else {
                Mockito.when(mockJwtValidationResult.valid()).thenReturn(false);
                Mockito.when(mockInternalAuthConfigurationPropertiesStrategy
                    .getLoginUri(Mockito.isA(String.class))).thenReturn(mockUri);
            }
        } else {
            Mockito.when(mockInternalAuthConfigurationPropertiesStrategy
                .getLoginUri(Mockito.isA(String.class))).thenReturn(mockUri);
        }
        // Run
        URI result = classUnderTest.loginOrRefresh(code, "redirectUrl");
        assertNotNull(result, NOTNULL);
        assertEquals(mockUri, result, EQUALS);
        return true;
    }

    @Test
    void testloginOrRefreshInvalid() {
        boolean result = testloginOrRefresh(ACCESSCODE, false);
        assertTrue(result, TRUE);
    }

    @Test
    void testloginOrRefreshNull() {
        boolean result = testloginOrRefresh(null, false);
        assertTrue(result, TRUE);
    }

    @Test
    void testhHandleOauthCode() {
        boolean result = testhHandleOauthCode(ACCESSCODE, "idToken", true);
        assertTrue(result, TRUE);
    }


    private boolean testhHandleOauthCode(String code, String idToken, boolean isValid) {
        try {
            // Expects
            Mockito.when(mockInternalAuthConfigurationPropertiesStrategy.getProviderConfiguration())
                .thenReturn(mockAuthProviderConfigurationProperties);
            Mockito.when(mockInternalAuthConfigurationPropertiesStrategy.getConfiguration())
                .thenReturn(mockAuthConfigurationProperties);
            Mockito.when(mockAzureDao.fetchAccessToken(code,
                mockAuthProviderConfigurationProperties, mockAuthConfigurationProperties))
                .thenReturn(mockOAuthProviderRawResponse);
            String accessToken = "accessToken";
            if (idToken == null) {
                Mockito.when(mockOAuthProviderRawResponse.getIdToken()).thenReturn(null);
                Mockito.when(mockOAuthProviderRawResponse.getAccessToken()).thenReturn(accessToken);
            } else {
                Mockito.when(mockOAuthProviderRawResponse.getIdToken()).thenReturn(accessToken);
            }
            Mockito
                .when(mockTokenValidator.validate(accessToken,
                    mockAuthProviderConfigurationProperties, mockAuthConfigurationProperties))
                .thenReturn(mockJwtValidationResult);
            Mockito.when(mockJwtValidationResult.valid()).thenReturn(isValid);
            // Run
            String result = classUnderTest.handleOauthCode(code);
            assertNotNull(result, NOTNULL);
            return true;
        } catch (AzureDaoException ex) {
            fail(ex.getMessage());
            return false;
        }
    }

    @Test
    void testhHandleOauthCodeNull() {
        boolean result = testhHandleOauthCode(ACCESSCODE, null, true);
        assertTrue(result, TRUE);
    }

    @Test
    void testhHandleOauthCodeInvalid() {
        assertThrows(PddaApiException.class,
            () -> testhHandleOauthCode(ACCESSCODE, "idToken", false));
    }

    @Test
    void testLogout() {
        // Setup
        String accessToken = "accessToken";
        String redirectUri = "redirectUri";
        // Expects
        Mockito.when(
            mockInternalAuthConfigurationPropertiesStrategy.getLogoutUri(accessToken, redirectUri))
            .thenReturn(mockUri);
        // Run
        URI result = classUnderTest.logout(accessToken, redirectUri);
        assertNotNull(result, NOTNULL);
        assertEquals(mockUri, result, EQUALS);
    }
}
