package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.client;

import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.openid.connect.sdk.OIDCScopeValue;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthProviderConfigurationProperties;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


@Component
@SuppressWarnings({"PMD.LooseCoupling", "PMD.UseObjectForClearerAPI"})
public class OAuthClientImpl implements OAuthClient {
    @SneakyThrows({URISyntaxException.class, IOException.class})
    @Override
    public HTTPResponse fetchAccessToken(
        AuthProviderConfigurationProperties providerConfigurationProperties, String redirectType,
        String authCode, String clientId, String authClientSecret, String scope) {
        AuthorizationCode code = new AuthorizationCode(authCode);
        URI callback = new URI(redirectType);
        AuthorizationGrant codeGrant = new AuthorizationCodeGrant(code, callback);
        Scope authScope = new Scope();
        authScope.add(scope);
        authScope.add(OIDCScopeValue.PROFILE);
        authScope.add(OIDCScopeValue.EMAIL);
        authScope.add(OIDCScopeValue.OFFLINE_ACCESS);

        ClientID clientID = new ClientID(clientId);
        Secret clientSecret = new Secret(authClientSecret);
        ClientAuthentication clientAuth = new ClientSecretBasic(clientID, clientSecret);

        URI tokenEndpoint = new URI(providerConfigurationProperties.getTokenUri());

        TokenRequest request = getTokenRequest(tokenEndpoint, clientAuth, codeGrant, authScope);
        return request.toHTTPRequest().send();
    }

    protected TokenRequest getTokenRequest(URI tokenEndpoint, ClientAuthentication clientAuth,
        AuthorizationGrant codeGrant, Scope authScope) {
        return new TokenRequest(tokenEndpoint, clientAuth, codeGrant, authScope);
    }
}
