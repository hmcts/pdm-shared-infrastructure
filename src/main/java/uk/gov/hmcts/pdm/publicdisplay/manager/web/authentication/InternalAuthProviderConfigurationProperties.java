package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.security.oauth2.client.provider.internal-azure-ad-provider")
public class InternalAuthProviderConfigurationProperties implements AuthProviderConfigurationProperties {

    private String authorizationUri;

    private String tokenUri;

    private String jwkSetUri;

    private String logoutUri;

    @Override
    public String getAuthorizationUri() {
        return authorizationUri;
    }

    @Override
    public void setAuthorizationUri(String authorizationUri) {
        this.authorizationUri = authorizationUri;
    }

    @Override
    public String getTokenUri() {
        return tokenUri;
    }

    @Override
    public void setTokenUri(String tokenUri) {
        this.tokenUri = tokenUri;
    }

    @Override
    public String getJwkSetUri() {
        return jwkSetUri;
    }

    @Override
    public void setJwkSetUri(String jwkSetUri) {
        this.jwkSetUri = jwkSetUri;
    }

    @Override
    public String getLogoutUri() {
        return logoutUri;
    }

    @Override
    public void setLogoutUri(String logoutUri) {
        this.logoutUri = logoutUri;
    }
}