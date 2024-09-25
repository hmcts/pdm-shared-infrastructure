package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.security.oauth2.client.registration.internal-azure-ad")
public class InternalAuthConfigurationProperties implements AuthConfigurationProperties {

    private String clientId;

    private String clientSecret;

    private String scope;

    private String redirectUri;

    private String logoutRedirectUri;

    private String grantType;

    private String responseType;

    private String responseMode;

    private String prompt;

    private String issuerUri;

    private String claims;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override 
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override 
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override 
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }

    @Override 
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Override
    public String getLogoutRedirectUri() {
        return logoutRedirectUri;
    }

    @Override 
    public void setLogoutRedirectUri(String logoutRedirectUri) {
        this.logoutRedirectUri = logoutRedirectUri;
    }

    @Override
    public String getGrantType() {
        return grantType;
    }

    @Override 
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    @Override
    public String getResponseType() {
        return responseType;
    }

    @Override 
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    @Override
    public String getResponseMode() {
        return responseMode;
    }

    @Override 
    public void setResponseMode(String responseMode) {
        this.responseMode = responseMode;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    @Override 
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String getIssuerUri() {
        return issuerUri;
    }

    @Override 
    public void setIssuerUri(String issuerUri) {
        this.issuerUri = issuerUri;
    }

    @Override
    public String getClaims() {
        return claims;
    }

    @Override 
    public void setClaims(String claims) {
        this.claims = claims;
    }
}