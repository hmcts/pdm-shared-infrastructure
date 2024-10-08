package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.client;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthProviderConfigurationProperties;

public interface OAuthClient {
    @SuppressWarnings({"PMD.UseObjectForClearerAPI"})
    HTTPResponse fetchAccessToken(AuthProviderConfigurationProperties providerConfigurationProperties,
                                  String redirectType, String authCode,
                                  String clientId,
                                  String authClientSecret, String scope);
}