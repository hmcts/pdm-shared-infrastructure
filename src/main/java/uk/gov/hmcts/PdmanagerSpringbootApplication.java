package uk.gov.hmcts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import uk.gov.hmcts.config.WebAppInitializer;

@SpringBootApplication
@EntityScan
@EnableAutoConfiguration
public class PdmanagerSpringbootApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PdmanagerSpringbootApplication.class);
    
    public static void main(String[] args) {
        String dbUsername = System.getenv("DB_USER_NAME");
        LOG.info("Database username: {}", dbUsername);
        SpringApplication.run(new Class[] {PdmanagerSpringbootApplication.class, WebAppInitializer.class}, args);
    }

    protected PdmanagerSpringbootApplication() {
        /*
         * empty constructor
         */
    }
}
