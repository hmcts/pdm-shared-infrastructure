package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthenticationError implements PddaApiError {

    FAILED_TO_OBTAIN_ACCESS_TOKEN(
        "AUTHENTICATION_100",
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Failed to obtain access token"
    ),

    FAILED_TO_VALIDATE_ACCESS_TOKEN(
        "AUTHENTICATION_101",
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Failed to validate access token"
    ),

    FAILED_TO_PARSE_ACCESS_TOKEN(
        "AUTHENTICATION_102",
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Failed to parse access token"
    ),

    FAILED_TO_OBTAIN_AUTHENTICATION_CONFIG(
        "AUTHENTICATION_103",
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Failed to find authentication configuration"
    );

    private static final String ERROR_TYPE_PREFIX = "AUTHENTICATION";

    private final String errorTypeNumeric;
    private final HttpStatus httpStatus;
    private final String title;

    @Override
    public String getErrorTypePrefix() {
        return ERROR_TYPE_PREFIX;
    }

}