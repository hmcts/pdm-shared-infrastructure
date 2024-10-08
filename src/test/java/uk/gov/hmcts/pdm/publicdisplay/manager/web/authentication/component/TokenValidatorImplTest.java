package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.component;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthProviderConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model.JwtValidationResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class TokenValidatorImpl.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TokenValidatorImplTest extends AbstractJUnit {

    private static final String NOTNULL = "Result is Null";

    @Mock
    private AuthProviderConfigurationProperties mockAuthProviderConfigurationProperties;

    @Mock
    private AuthConfigurationProperties mockAuthConfigurationProperties;

    @Mock
    private JWKSource<SecurityContext> mockJwkSource;

    @InjectMocks
    private TokenValidatorImpl classUnderTest;

    @Test
    void testValidate() {
        // Expects
        Mockito.when(mockAuthProviderConfigurationProperties.getJwkSource())
            .thenReturn(mockJwkSource);
        // Run
        JwtValidationResult result = classUnderTest.validate("accessCode",
            mockAuthProviderConfigurationProperties, mockAuthConfigurationProperties);
        assertNotNull(result, NOTNULL);

    }
}
