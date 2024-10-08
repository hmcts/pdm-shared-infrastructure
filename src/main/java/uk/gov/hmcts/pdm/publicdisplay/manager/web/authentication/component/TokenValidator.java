package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.component;

import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthProviderConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model.JwtValidationResult;

public interface TokenValidator {

    JwtValidationResult validate(String accessToken,
        AuthProviderConfigurationProperties providerConfig,
        AuthConfigurationProperties configuration);

}
