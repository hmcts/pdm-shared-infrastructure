package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthProviderConfigurationProperties;

import java.io.IOException;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final Log LOG = LogFactory.getLog(WebSecurityConfig.class);
    private static final String[] AUTH_WHITELIST = {"/health/liveness", "/health/readiness",
        "/health", "/loggers/**", "/", "/error**", "/callback/", "/css/xhibit.css",
        "/css/bootstrap.min.css", "/js/bootstrap.min.js", "/WEB-INF/jsp/error**",
        "/oauth2/authorization/**", "/oauth2/authorize/azure/**", "/login/oauth2/code**",
        "/status/health", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**"};

    private final InternalAuthConfigurationProperties internalAuthConfigurationProperties;
    private final InternalAuthProviderConfigurationProperties internalAuthProviderConfigurationProperties;

    public WebSecurityConfig() {
        this(new InternalAuthConfigurationProperties(),
            new InternalAuthProviderConfigurationProperties());
    }

    public WebSecurityConfig(
        InternalAuthConfigurationProperties internalAuthConfigurationProperties,
        InternalAuthProviderConfigurationProperties internalAuthProviderConfigurationProperties) {
        this.internalAuthConfigurationProperties = internalAuthConfigurationProperties;
        this.internalAuthProviderConfigurationProperties =
            internalAuthProviderConfigurationProperties;
    }

    @Bean
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "squid:S4502"})
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return getHttp(http).build();
    }

    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "removal"})
    protected HttpSecurity getHttp(HttpSecurity http) throws Exception {
        return http
            .addFilterBefore(new AuthorisationTokenExistenceFilter(),
                OAuth2LoginAuthenticationFilter.class)
            .authorizeHttpRequests().anyRequest().authenticated().and().oauth2ResourceServer()
            .authenticationManagerResolver(jwtIssuerAuthenticationManagerResolver()).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
            .disable().formLogin().disable().logout().disable();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        LOG.info("webSecurityCustomizer()");
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

    private JwtIssuerAuthenticationManagerResolver jwtIssuerAuthenticationManagerResolver() {
        Map<String, AuthenticationManager> authenticationManagers = Map
            .ofEntries(createAuthenticationEntry(internalAuthConfigurationProperties.getIssuerUri(),
                internalAuthProviderConfigurationProperties.getJwkSetUri()));
        return new JwtIssuerAuthenticationManagerResolver(authenticationManagers::get);
    }

    private Map.Entry<String, AuthenticationManager> createAuthenticationEntry(String issuer,
        String jwkSetUri) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri)
            .jwsAlgorithm(SignatureAlgorithm.RS256).build();

        OAuth2TokenValidator<Jwt> jwtValidator = JwtValidators.createDefaultWithIssuer(issuer);
        jwtDecoder.setJwtValidator(jwtValidator);

        JwtAuthenticationProvider authenticationProvider =
            new JwtAuthenticationProvider(jwtDecoder);

        return Map.entry(issuer, authenticationProvider::authenticate);
    }

    private final class AuthorisationTokenExistenceFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
            }
        }
    }
}
