package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SecurityToken {

    private String accessToken;

}
