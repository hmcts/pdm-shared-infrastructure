package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

public interface AuthConfigurationProperties {

    String getRedirectUri();
    
    void setRedirectUri(String redirectUri);

    String getLogoutRedirectUri();
    
    void setLogoutRedirectUri(String logoutRedirectUri);

    String getIssuerUri();
    
    void setIssuerUri(String issuerUri);

    String getPrompt();
    
    void setPrompt(String prompt);

    String getClientId();
    
    void setClientId(String clientId);

    String getClientSecret();
    
    void setClientSecret(String clientSecret);

    String getResponseMode();
    
    void setResponseMode(String responseMode);

    String getScope();
    
    void setScope(String scope);

    String getGrantType();
    
    void setGrantType(String grantType);

    String getResponseType();
    
    void setResponseType(String responseType);

    String getClaims();
    
    void setClaims(String claims);
    
}