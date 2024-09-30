package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class InternalAuthConfigurationPropertiesStrategy.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InternalAuthConfigurationPropertiesStrategyTest extends AbstractJUnit {

    private static final String NOTNULL = "Result is Null";


    @InjectMocks
    private final InternalAuthConfigurationPropertiesStrategy classUnderTest =
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
}
