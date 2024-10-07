package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;

public interface PddaApiError {

    String getErrorTypePrefix();

    String getErrorTypeNumeric();

    HttpStatus getHttpStatus();

    String getTitle();

    default URI getType() {
        return URI.create(
            getErrorTypeNumeric()
        );
    }

}