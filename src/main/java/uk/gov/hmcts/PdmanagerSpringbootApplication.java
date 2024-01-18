package uk.gov.hmcts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
