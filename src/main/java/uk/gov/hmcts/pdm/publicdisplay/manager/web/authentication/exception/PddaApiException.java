package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
@SuppressWarnings("PMD.NullAssignment")
public class PddaApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final PddaApiError error;
    private final String detail;
    private final Map<String, Object> customProperties = new ConcurrentHashMap<>();

    public PddaApiException(PddaApiError error) {
        super(error.getTitle());

        this.error = error;
        this.detail = null;
    }

    public PddaApiException(PddaApiError error, Throwable throwable) {
        super(error.getTitle(), throwable);

        this.error = error;
        this.detail = null;
    }

    public PddaApiException(PddaApiError error, String detail) {
        super(String.format("%s. %s", error.getTitle(), detail));

        this.error = error;
        this.detail = detail;
    }

    public PddaApiException(PddaApiError error, Map<String, Object> customProperties) {
        super(error.getTitle());

        this.error = error;
        this.detail = null;
        this.customProperties.putAll(customProperties);
    }

    public PddaApiException(PddaApiError error, String detail, Map<String, Object> customProperties) {
        super(String.format("%s. %s", error.getTitle(), detail));

        this.error = error;
        this.detail = detail;
        this.customProperties.putAll(customProperties);
    }

    public PddaApiException(PddaApiError error, String detail, Throwable throwable) {
        super(String.format("%s. %s", error.getTitle(), detail), throwable);

        this.error = error;
        this.detail = detail;
    }

    @Getter
    @RequiredArgsConstructor
    public enum PddaApiErrorCommon implements PddaApiError {
        FEATURE_FLAG_NOT_ENABLED(
            "FEATURE_FLAG_NOT_ENABLED",
            HttpStatus.NOT_IMPLEMENTED,
            "Feature flag not enabled"
        );

        private static final String ERROR_TYPE_PREFIX = "COMMON";
        private final String errorTypeNumeric;
        private final HttpStatus httpStatus;
        private final String title;

        @Override
        public String getErrorTypePrefix() {
            return ERROR_TYPE_PREFIX;
        }

    }

}