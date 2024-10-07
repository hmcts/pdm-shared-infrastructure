package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The Class SecurityToken.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SecurityTokenTest extends AbstractJUnit {

    private static final String EQUALS = "Result is are not Equal";

    private static final String SECURITY_TOKEN = "accessToken";

    private final SecurityToken classUnderTest = new SecurityToken(SECURITY_TOKEN);

    @Test
    void testSecurityToken() {
        String result = classUnderTest.getAccessToken();
        // Checks
        assertEquals(SECURITY_TOKEN, result, EQUALS);

    }
}
