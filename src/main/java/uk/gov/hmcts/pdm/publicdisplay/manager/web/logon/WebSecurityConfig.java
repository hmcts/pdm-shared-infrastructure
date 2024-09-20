package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
        "/health/liveness",
        "/health/readiness",
        "/health",
        "/loggers/**",
        "/",
        "/status/health",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/webjars/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

}
