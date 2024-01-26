package uk.gov.hmcts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PdmanagerSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdmanagerSpringbootApplication.class, args);
    }

    protected PdmanagerSpringbootApplication() {
        /*
         * empty constructor
         */
    }
}
