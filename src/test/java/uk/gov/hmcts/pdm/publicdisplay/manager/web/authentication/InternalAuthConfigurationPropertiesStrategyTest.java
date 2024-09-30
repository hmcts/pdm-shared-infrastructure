package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The Class InternalAuthConfigurationPropertiesStrategy.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InternalAuthConfigurationPropertiesStrategyTest extends AbstractJUnit {

    private static final String FALSE = "Result is True";
    private static final String NOTNULL = "Result is Null";
    private static final String TRUE = "Result is False";


    @InjectMocks
    private final AuthenticationConfigurationPropertiesStrategy classUnderTest =
        new InternalAuthConfigurationPropertiesStrategy(
            Mockito.mock(InternalAuthConfigurationProperties.class),
            Mockito.mock(InternalAuthProviderConfigurationProperties.class));

    @Test
    void testGetConfiguration() {
        AuthConfigurationProperties result = classUnderTest.getConfiguration();
        assertNotNull(result, NOTNULL);
    }

    @Test
    void testGetProviderConfiguration() {
        AuthProviderConfigurationProperties result = classUnderTest.getProviderConfiguration();
        assertNotNull(result, NOTNULL);
    }
    
    @Test
    void testDoesMatch() {
        StringBuffer validSb = new StringBuffer();
        validSb.append("/login");
        HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockHttpServletRequest.getRequestURL()).thenReturn(validSb);
        
        boolean result = classUnderTest.doesMatch(mockHttpServletRequest);
        assertTrue(result, TRUE);
    }
    
    @Test
    void testDoesMatchFailure() {
        StringBuffer validSb = new StringBuffer();
        validSb.append("/invalid");
        HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockHttpServletRequest.getRequestURL()).thenReturn(validSb);
        
        boolean result = classUnderTest.doesMatch(mockHttpServletRequest);
        assertFalse(result, FALSE);
    }
}
