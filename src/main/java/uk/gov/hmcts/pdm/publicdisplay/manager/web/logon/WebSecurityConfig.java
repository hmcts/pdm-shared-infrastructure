package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class WebSecurityConfig {

    private static final Log LOG = LogFactory.getLog(WebSecurityConfig.class);
    private static final String[] AUTH_WHITELIST = {
        "/health/liveness",
        "/health/readiness",
        "/health",
        "/loggers/**",
        "/",
        "/login**",
        "/error**",
        "/callback/",
        "/css/xhibit.css",
        "/css/bootstrap.min.css",
        "/js/bootstrap.min.js",
        "/WEB-INF/jsp/error**",
        "/oauth2/authorization/**", 
        "/login/oauth2/code**",
        "/status/health",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/webjars/**"
    };
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LOG.info("filterChain()");
        http.csrf(csrf -> csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        LOG.info("webSecurityCustomizer()");
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

}
