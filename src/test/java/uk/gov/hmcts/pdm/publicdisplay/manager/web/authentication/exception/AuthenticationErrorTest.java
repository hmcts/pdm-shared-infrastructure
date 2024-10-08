package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class AuthenticationError.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationErrorTest extends AbstractJUnit {

    private static final String FORMAT_STRING = "%s - %s";
    private static final String NOTNULL = "Result is Null";

    @Test
    void testConstructors() {
        AuthenticationError result = AuthenticationError.FAILED_TO_OBTAIN_ACCESS_TOKEN;
        assertNotNull(result,
            String.format(FORMAT_STRING, NOTNULL, "Failed to obtain access token"));
        result = AuthenticationError.FAILED_TO_OBTAIN_AUTHENTICATION_CONFIG;
        assertNotNull(result,
            String.format(FORMAT_STRING, NOTNULL, "Failed to obtain authentication config"));
        assertNotNull(result.getErrorTypePrefix(), String.format(FORMAT_STRING, NOTNULL, "getErrorTypePrefix()"));
    }
}
