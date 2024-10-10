package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.client;

import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthProviderConfigurationProperties;

import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class OAuthClientImpl.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OAuthClientImplTest extends AbstractJUnit {

    private static final String NOTNULL = "Result is Null";

    @Mock
    private AuthProviderConfigurationProperties mockAuthProviderConfigurationProperties;

    @Mock
    private TokenRequest mockTokenRequest;

    @Mock
    private HTTPRequest mockHttpRequest;

    @Mock
    private HTTPResponse mockHttpResponse;

    @InjectMocks
    private OAuthClientImpl classUnderTest;

    @Test
    void testFetchAccessToken() {
        try {
            // Setup
            String redirectType = "redirectType";
            String authCode = "authCode";
            String clientId = "clientId";
            String authClientSecret = "authClientSecret";
            String tokenUri = "localhost";
            // Expects
            Mockito.when(mockAuthProviderConfigurationProperties.getTokenUri())
                .thenReturn(tokenUri);
            Mockito.when(mockTokenRequest.toHTTPRequest()).thenReturn(mockHttpRequest);
            Mockito.when(mockHttpRequest.send()).thenReturn(mockHttpResponse);
            // Run
            OAuthClientImpl localClassUnderTest = new OAuthClientImpl() {
                @Override
                protected TokenRequest getTokenRequest(URI tokenEndpoint,
                    ClientAuthentication clientAuth, AuthorizationGrant codeGrant) {
                    return mockTokenRequest;
                }
            };
            HTTPResponse result =
                localClassUnderTest.fetchAccessToken(mockAuthProviderConfigurationProperties,
                    redirectType, authCode, clientId, authClientSecret);
            assertNotNull(result, NOTNULL);
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void testGetTokenRequest() {
        TokenRequest result = classUnderTest.getTokenRequest(Mockito.mock(URI.class),
            Mockito.mock(ClientAuthentication.class), Mockito.mock(AuthorizationGrant.class));
        assertNotNull(result, NOTNULL);
    }

}
