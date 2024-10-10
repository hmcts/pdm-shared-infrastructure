package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import org.apache.http.HttpStatus;
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
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.client.OAuthClient;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception.AzureDaoException;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model.OAuthProviderRawResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * The Class AzureDaoImpl.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AzureDaoImplTest extends AbstractJUnit {

    private static final String NOTNULL = "Result is Null";
    private static final String TRUE = "Result is False";

    @Mock
    private OAuthProviderRawResponse mockOAuthProviderRawResponse;

    @Mock
    private AuthProviderConfigurationProperties mockAuthProviderConfigurationProperties;

    @Mock
    private AuthConfigurationProperties mockAuthConfigurationProperties;

    @Mock
    private OAuthClient mockOAuthClient;

    @Mock
    private HTTPResponse mockHttpResponse;

    @InjectMocks
    private AzureDaoImpl classUnderTest;

    @Test
    void testFetchAccessToken() {
        try {
            boolean result = testFetchAccessToken("accessCode",
                getJsonObject(new OAuthProviderRawResponse()), HttpStatus.SC_OK);
            assertTrue(result, TRUE);
        } catch (AzureDaoException ex) {
            fail(ex.getMessage());
        }
    }
    
    private boolean testFetchAccessToken(String code, String jsonObject, int httpStatus)
        throws AzureDaoException {
        // Expects
        Mockito.when(mockAuthConfigurationProperties.getRedirectUri()).thenReturn("redirectUri");
        Mockito.when(mockAuthConfigurationProperties.getClientId()).thenReturn("clientId");
        Mockito.when(mockAuthConfigurationProperties.getClientSecret()).thenReturn("clientSecret");
        Mockito.when(
            mockOAuthClient.fetchAccessToken(Mockito.isA(AuthProviderConfigurationProperties.class),
                Mockito.isA(String.class), Mockito.isA(String.class), Mockito.isA(String.class),
                Mockito.isA(String.class)))
            .thenReturn(mockHttpResponse);
        Mockito.when(mockHttpResponse.getContent()).thenReturn(jsonObject);
        Mockito.when(mockHttpResponse.getStatusCode()).thenReturn(httpStatus);
        // Run
        OAuthProviderRawResponse result = classUnderTest.fetchAccessToken(code,
            mockAuthProviderConfigurationProperties, mockAuthConfigurationProperties);
        // Checks
        assertNotNull(result, NOTNULL);
        return true;
    }

    @Test
    void testFetchAccessTokenFailure() {
        assertThrows(AzureDaoException.class,
            () -> testFetchAccessToken("accessCode", null, HttpStatus.SC_BAD_REQUEST));
    }
    
    @Test
    void testFetchAccessTokenNullCode() {
        assertThrows(AzureDaoException.class,
            () -> testFetchAccessToken(null, null, HttpStatus.SC_BAD_REQUEST));
    }

    private String getJsonObject(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            fail(ex.getMessage());
            return null;
        }
    }
}
