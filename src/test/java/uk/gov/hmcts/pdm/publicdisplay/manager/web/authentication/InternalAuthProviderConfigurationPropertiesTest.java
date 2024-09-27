package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Class InternalAuthProviderConfigurationProperties.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InternalAuthProviderConfigurationPropertiesTest extends AbstractJUnit {

    private static final String EQUALS = "Result is not Equal";

    @InjectMocks
    private final AuthProviderConfigurationProperties classUnderTest =
        new InternalAuthProviderConfigurationProperties();


    @Test
    void testAuthorizationUri() {
        String result = "AuthorizationUri";
        classUnderTest.setAuthorizationUri(result);
        assertEquals(result, classUnderTest.getAuthorizationUri(), EQUALS);
    }

    @Test
    void testTokenUri() {
        String result = "TokenUri";
        classUnderTest.setTokenUri(result);
        assertEquals(result, classUnderTest.getTokenUri(), EQUALS);
    }

    @Test
    void testJwkSetUri() {
        String result = "JwkSetUri";
        classUnderTest.setJwkSetUri(result);
        assertEquals(result, classUnderTest.getJwkSetUri(), EQUALS);
    }

    @Test
    void testLogoutUri() {
        String result = "LogoutUri";
        classUnderTest.setLogoutUri(result);
        assertEquals(result, classUnderTest.getLogoutUri(), EQUALS);
    }
}
