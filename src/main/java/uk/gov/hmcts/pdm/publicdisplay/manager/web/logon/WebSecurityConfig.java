package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import com.azure.spring.cloud.autoconfigure.implementation.aad.security.AadWebApplicationHttpSecurityConfigurer;
import com.pdm.hb.jpa.AuthorizationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.io.IOException;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends AadWebApplicationHttpSecurityConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);
    private static final String HOME_URL = "/home";

    /**
     * Authorisation filterchain.
     */
    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
        throws Exception {
        return getAuthHttp(http).build();
    }

    /**
     * Get the Authorisation HTTP.
     */
    protected HttpSecurity getAuthHttp(HttpSecurity http) throws Exception {
        http.oauth2Login(
            auth -> auth.successHandler(getSuccessHandler()).failureHandler(getFailureHandler()))
            .csrf(csrf -> csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));
        return http;
    }

    @Bean
    public AuthenticationSuccessHandler getSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {
                LOG.info("The user {} has logged in.",
                    AuthorizationUtil.getUsername(authentication));
                response.sendRedirect(HOME_URL);
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler getFailureHandler() {
        return new AuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request,
                HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
                LOG.info("Login Failure {}", exception.getMessage());
            }
        };
    }

}
