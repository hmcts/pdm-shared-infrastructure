package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import com.azure.spring.cloud.autoconfigure.implementation.aad.security.AadWebApplicationHttpSecurityConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdm.hb.jpa.AuthorizationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SuppressWarnings({"PMD.SignatureDeclareThrowsException", "PMD.LawOfDemeter"})
public class WebSecurityConfig extends AadWebApplicationHttpSecurityConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);
    private static final String HOME_URL = "/home";
    private static final String LOGIN_ERROR = "/loginError";
    private static final String[] AUTH_WHITELIST = {"/health/**", "/error**", "/fonts/glyph*",
        "/css/xhibit.css", "/css/bootstrap.min.css", "/js/bootstrap.min.js", "/WEB-INF/jsp/error**",
        "/css/**", "/js/**", "favicon.ico", "/login**"};

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
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
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
                response.setStatus(HttpStatus.OK.value());
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
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                // Get the error
                Map<String, Object> data = new ConcurrentHashMap<>();
                data.put("timestamp", LocalDateTime.now().toString());
                data.put("exception", exception.getMessage());

                ObjectMapper objectMapper = new ObjectMapper();
                response.getOutputStream().println(objectMapper.writeValueAsString(data));
                response.sendRedirect(LOGIN_ERROR);
            }
        };
    }

}
