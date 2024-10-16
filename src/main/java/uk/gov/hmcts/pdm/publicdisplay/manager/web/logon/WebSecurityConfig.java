package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("!intTest")
@SuppressWarnings({"PMD.SignatureDeclareThrowsException", "removal"})
public class WebSecurityConfig {

    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);
    private static final String[] AUTH_WHITELIST =
        {"/health/**", "/loggers/**", "/", "/error**", "/auth/internal/callback", "/fonts/glyph*",
            "/css/**", "/js/**", "favicon.ico", "/WEB-INF/jsp/error**", "/oauth2/authorization/**",
            "/oauth2/authorize/azure/**", "/dashboard**", "/status/health", "/swagger-resources/**",
            "/swagger-ui/**", "/webjars/**", "/login**", "/WEB-INF/jsp/logon/signin**"};

    /**
     * Security filterchain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LOG.info("filterChain()");
        return getAuthHttp(http).build();
    }

    /**
     * Get the HTTP.
     */
    protected HttpSecurity getAuthHttp(HttpSecurity http) throws Exception {
        return http
            .sessionManagement(
                management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(csrf -> csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
            .oauth2Client(Customizer.withDefaults()).oauth2Login(Customizer.withDefaults());
    }

    /**
     * Produce the whitelist.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }
}
