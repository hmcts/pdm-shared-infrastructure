package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthConfigurationPropertiesStrategy;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.component.TokenValidator;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.dao.AzureDao;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception.AuthenticationError;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception.AzureDaoException;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception.PddaApiException;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model.JwtValidationResult;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model.OAuthProviderRawResponse;

import java.net.URI;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenValidator tokenValidator;
    private final AzureDao azureDao;
    private final InternalAuthConfigurationPropertiesStrategy uriProvider;

    @Override
    public URI loginOrRefresh(String accessToken, String redirectUri) {
        log.debug("Initiated login or refresh flow with access token {}", accessToken);

        if (accessToken == null) {
            return uriProvider.getLoginUri(redirectUri);
        }

        JwtValidationResult validationResult = tokenValidator.validate(accessToken,
            uriProvider.getProviderConfiguration(), uriProvider.getConfiguration());

        if (!validationResult.valid()) {
            return uriProvider.getLoginUri(redirectUri);
        }

        return uriProvider.getLandingPageUri();
    }

    @Override
    public String handleOauthCode(String code) {
        log.debug("Presented authorization code {}", code);

        OAuthProviderRawResponse tokenResponse;
        try {
            tokenResponse = azureDao.fetchAccessToken(code, uriProvider.getProviderConfiguration(),
                uriProvider.getConfiguration());
        } catch (AzureDaoException ex) {
            throw new PddaApiException(AuthenticationError.FAILED_TO_OBTAIN_ACCESS_TOKEN, ex);
        }
        String accessToken =
            Objects.nonNull(tokenResponse.getIdToken()) ? tokenResponse.getIdToken()
                : tokenResponse.getAccessToken();

        JwtValidationResult validationResult = tokenValidator.validate(accessToken,
            uriProvider.getProviderConfiguration(), uriProvider.getConfiguration());
        if (!validationResult.valid()) {
            throw new PddaApiException(AuthenticationError.FAILED_TO_VALIDATE_ACCESS_TOKEN);
        }

        return accessToken;
    }

    @Override
    public URI logout(String accessToken, String redirectUri) {
        log.debug("Initiated logout flow with access token {} and redirectUri {}", accessToken,
            redirectUri);
        return uriProvider.getLogoutUri(accessToken, redirectUri);
    }
}
