package uk.gov.hmcts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import uk.gov.hmcts.config.WebAppInitializer;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PdmanagerSpringbootApplicationTest {

    private static final String NOT_TRUE = "Result is not True";

    @Mock
    private ConfigurableApplicationContext mockContext;

    @Test
    void testApplication() {
        boolean result;

        // Setup
        try (MockedStatic<SpringApplication> mockSpringApplication = Mockito.mockStatic(
            SpringApplication.class)) {
            mockSpringApplication.when((MockedStatic.Verification) SpringApplication.run(
                new Class[] {PdmanagerSpringbootApplication.class, WebAppInitializer.class},
                new String[] {})).thenReturn(mockContext);
            // Run
            try {
                PdmanagerSpringbootApplication.main(new String[] {});
                result = true;
            } catch (Exception exception) {
                result = false;
            }
        }
        assertTrue(result, NOT_TRUE);
    }
}
