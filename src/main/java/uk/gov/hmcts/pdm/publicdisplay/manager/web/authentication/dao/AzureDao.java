package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.dao;

import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthProviderConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception.AzureDaoException;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model.OAuthProviderRawResponse;

public interface AzureDao {
    OAuthProviderRawResponse fetchAccessToken(String code, AuthProviderConfigurationProperties providerConfig,
                                              AuthConfigurationProperties configuration) throws AzureDaoException;
}