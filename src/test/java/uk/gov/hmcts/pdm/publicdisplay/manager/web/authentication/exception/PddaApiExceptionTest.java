package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception.PddaApiException.PddaApiErrorCommon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class PddaApiException.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PddaApiExceptionTest extends AbstractJUnit {

    private static final String FORMAT_STRING = "%s - %s";
    private static final String NOTNULL = "Result is Null";

    @Mock
    private PddaApiError mockPddaApiError;

    @Mock
    private Throwable mockThrowable;

    @Test
    void testConstructors() {
        PddaApiException result = new PddaApiException(mockPddaApiError);
        assertNotNull(result, String.format(FORMAT_STRING, NOTNULL, "Constructor1"));
        result = new PddaApiException(mockPddaApiError, mockThrowable);
        assertNotNull(result, String.format(FORMAT_STRING, NOTNULL, "Constructor2"));
        String detail = "Detail";
        result = new PddaApiException(mockPddaApiError, detail);
        assertNotNull(result, String.format(FORMAT_STRING, NOTNULL, "Constructor3"));
        Map<String, Object> customProperties = new ConcurrentHashMap<>();
        result = new PddaApiException(mockPddaApiError, customProperties);
        assertNotNull(result, String.format(FORMAT_STRING, NOTNULL, "Constructor4"));
        result = new PddaApiException(mockPddaApiError, detail, customProperties);
        assertNotNull(result, String.format(FORMAT_STRING, NOTNULL, "Constructor5"));
    }

    @Test
    void testEnum() {
        PddaApiErrorCommon result = PddaApiErrorCommon.FEATURE_FLAG_NOT_ENABLED;
        assertNotNull(result, NOTNULL);
        assertNotNull(result.getTitle(), String.format(FORMAT_STRING, NOTNULL, "getTitle"));
        assertNotNull(result.getErrorTypePrefix(),
            String.format(FORMAT_STRING, NOTNULL, "getErrorTypePrefix"));
    }
}
