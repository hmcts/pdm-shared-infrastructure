package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

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
        "/oauth2/authorization/**", 
        "/login/oauth2/code**",
        "/status/health",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/webjars/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        LOG.info("webSecurityCustomizer()");
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

}
