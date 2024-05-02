package uk.gov.hmcts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import uk.gov.hmcts.config.WebAppInitializer;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EntityScan
//@EnableAutoConfiguration
public class PdmanagerSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class[] {PdmanagerSpringbootApplication.class, WebAppInitializer.class}, args);
    }

    protected PdmanagerSpringbootApplication() {
        /*
         * empty constructor
         */
    }
}
