package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model;

public record JwtValidationResult(boolean valid, String reason) {

}