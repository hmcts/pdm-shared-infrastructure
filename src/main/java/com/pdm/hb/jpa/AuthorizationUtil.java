package com.pdm.hb.jpa;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

@SuppressWarnings("PMD.LawOfDemeter")
public class AuthorizationUtil {

    private static final String EMPTY_STRING = "";

    protected AuthorizationUtil() {
        // Protected constructor
    }

    public static String getUsername() {
        return getUsername(SecurityContextHolder.getContext().getAuthentication());
    }

    public static String getUsername(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            if (oauthToken.getPrincipal() != null) {
                return oauthToken.getPrincipal().getAttribute("name");
            }
        } else if (authentication instanceof AnonymousAuthenticationToken) {
            AnonymousAuthenticationToken oauthToken = (AnonymousAuthenticationToken) authentication;
            return oauthToken.getName();
        }
        return EMPTY_STRING;
    }

    public static boolean isAuthorised() {
        return isAuthorised(SecurityContextHolder.getContext().getAuthentication());
    }

    public static boolean isAuthorised(Authentication authentication) {
        return getToken(authentication) != null;
    }
    
    public static OidcIdToken getToken(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            if (oauthToken.getPrincipal() instanceof DefaultOidcUser) {
                DefaultOidcUser principal = (DefaultOidcUser) oauthToken.getPrincipal();
                return principal.getIdToken();
            }
        }
        return null;
    }
}
