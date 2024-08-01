package uk.gov.hmcts;

import jakarta.ws.rs.ApplicationPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import uk.gov.hmcts.config.WebAppInitializer;

@SpringBootApplication
@EntityScan
@EnableAutoConfiguration
@ApplicationPath("/pdm")
public class PdmanagerSpringbootApplication {

    @Value("${spring.cloud.azure.active-directory.credential.client-id}")
    private static String clientID;
    @Value("${spring.cloud.azure.active-directory.profile.tenant-id}")
    private static String tenantID;
    @Value("${spring.cloud.azure.active-directory.credential.client-secret}")
    private static String clientSecret;
    private static final Logger LOGGER = LoggerFactory.getLogger(PdmanagerSpringbootApplication.class);

    public static void main(String[] args) {

        LOGGER.info("{} - the clientID retrieved from key vault", clientID);
        LOGGER.info("{} - the tenantID retrieved from key vault", tenantID);
        LOGGER.info("{} - the clientSecret retrieved from key vault", clientSecret);

        SpringApplication.run(new Class[] {PdmanagerSpringbootApplication.class, WebAppInitializer.class}, args);
    }

    protected PdmanagerSpringbootApplication() {
        /*
         * empty constructor
         */
    }
}
