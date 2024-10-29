package com.pdm.hb.jpa;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

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
            if (oauthToken != null && oauthToken.getPrincipal() != null) {
                return oauthToken.getPrincipal().getAttribute("name");
            }
        } else if (authentication instanceof AnonymousAuthenticationToken) {
            AnonymousAuthenticationToken oauthToken = (AnonymousAuthenticationToken) authentication;
            if (oauthToken != null) {
                return oauthToken.getName();
            }
        }
        return EMPTY_STRING;
    }

    public static boolean isAuthorised() {
        return isAuthorised(SecurityContextHolder.getContext().getAuthentication());
    }

    public static boolean isAuthorised(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            if (oauthToken != null && oauthToken.getPrincipal() != null) {
                return true;
            }
        }
        return false;
    }
}
