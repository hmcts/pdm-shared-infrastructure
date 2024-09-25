package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication;

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = -2822981336624374965L;

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(String message, String cause) {
        super(String.format("%s: %s", message, cause));
    }

}