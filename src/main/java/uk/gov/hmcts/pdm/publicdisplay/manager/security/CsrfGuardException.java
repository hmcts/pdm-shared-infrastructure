package uk.gov.hmcts.pdm.publicdisplay.manager.security;

public class CsrfGuardException extends RuntimeException {

    private static final long serialVersionUID = -4918761881222399818L;

    public CsrfGuardException(String message) {
        super(message);
    }


}
