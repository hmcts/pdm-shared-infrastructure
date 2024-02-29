package uk.gov.hmcts.pdm.mockipdmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuppressWarnings("HideUtilityClassConstructor") // Spring needs a constructor, its not a utility class
public class MockipdmanagerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MockipdmanagerApplication.class, args);
    }
}
