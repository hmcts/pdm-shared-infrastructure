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
 * The Class InternalAuthConfigurationProperties.
 *
 * @author harrism
 */
@SuppressWarnings("PMD.TooManyMethods")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InternalAuthConfigurationPropertiesTest extends AbstractJUnit {

    private static final String EQUALS = "Result is not Equal";

    @InjectMocks
    private final AuthConfigurationProperties classUnderTest =
        new InternalAuthConfigurationProperties();


    @Test
    void testCclientId() {
        String result = "ClientId";
        classUnderTest.setClientId(result);
        assertEquals(result, classUnderTest.getClientId(), EQUALS);
    }

    @Test
    void testClientSecret() {
        String result = "ClientSecret";
        classUnderTest.setClientSecret(result);
        assertEquals(result, classUnderTest.getClientSecret(), EQUALS);
    }

    @Test
    void testScope() {
        String result = "Scope";
        classUnderTest.setScope(result);
        assertEquals(result, classUnderTest.getScope(), EQUALS);
    }

    @Test
    void testRedirectUri() {
        String result = "RedirectUri";
        classUnderTest.setRedirectUri(result);
        assertEquals(result, classUnderTest.getRedirectUri(), EQUALS);
    }
    
    @Test
    void testLogoutRedirectUri() {
        String result = "LogoutRedirectUri";
        classUnderTest.setLogoutRedirectUri(result);
        assertEquals(result, classUnderTest.getLogoutRedirectUri(), EQUALS);
    }
    
    @Test
    void testGrantType() {
        String result = "GrantType";
        classUnderTest.setGrantType(result);
        assertEquals(result, classUnderTest.getGrantType(), EQUALS);
    }
    
    @Test
    void testResponseType() {
        String result = "ResponseType";
        classUnderTest.setResponseType(result);
        assertEquals(result, classUnderTest.getResponseType(), EQUALS);
    }
    
    @Test
    void testResponseMode() {
        String result = "ResponseMode";
        classUnderTest.setResponseMode(result);
        assertEquals(result, classUnderTest.getResponseMode(), EQUALS);
    }
    
    @Test
    void testPrompt() {
        String result = "Prompt";
        classUnderTest.setPrompt(result);
        assertEquals(result, classUnderTest.getPrompt(), EQUALS);
    }
    
    @Test
    void testIssuerUri() {
        String result = "IssuerUri";
        classUnderTest.setIssuerUri(result);
        assertEquals(result, classUnderTest.getIssuerUri(), EQUALS);
    }
    
    @Test
    void testClaims() {
        String result = "Claims";
        classUnderTest.setClaims(result);
        assertEquals(result, classUnderTest.getClaims(), EQUALS);
    }
}
