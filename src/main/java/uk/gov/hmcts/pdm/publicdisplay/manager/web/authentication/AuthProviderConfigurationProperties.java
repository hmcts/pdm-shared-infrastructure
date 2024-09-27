package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.JWKSourceBuilder;
import com.nimbusds.jose.proc.SecurityContext;

import java.net.MalformedURLException;
import java.net.URL;

public interface AuthProviderConfigurationProperties {

    String getAuthorizationUri();
    
    void setAuthorizationUri(String authorizationUri);

    String getTokenUri();
    
    void setTokenUri(String tokenUri);

    String getJwkSetUri();
    
    void setJwkSetUri(String jwkSetUri);

    String getLogoutUri();
    
    void setLogoutUri(String logoutUri);

    default JWKSource<SecurityContext> getJwkSource() {
        try {
            URL jwksUrl = new URL(getJwkSetUri());

            return JWKSourceBuilder.create(jwksUrl).build();
        } catch (MalformedURLException malformedUrlException) {
            throw new AuthenticationException("Sorry authentication jwks URL is incorrect", malformedUrlException);
        }
    }
    
}
