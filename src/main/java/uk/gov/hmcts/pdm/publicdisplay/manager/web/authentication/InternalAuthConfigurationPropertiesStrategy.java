package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InternalAuthConfigurationPropertiesStrategy implements AuthenticationConfigurationPropertiesStrategy {
    
    private final InternalAuthConfigurationProperties configuration;

    private final InternalAuthProviderConfigurationProperties provider;

    @Override
    public AuthConfigurationProperties getConfiguration() {
        return configuration;
    }

    @Override
    public AuthProviderConfigurationProperties getProviderConfiguration() {
        return provider;
    }

    @Override
    public boolean doesMatch(HttpServletRequest req) {
        return URL_MAPPER_INTERNAL.doesMatch(req);
    }
}