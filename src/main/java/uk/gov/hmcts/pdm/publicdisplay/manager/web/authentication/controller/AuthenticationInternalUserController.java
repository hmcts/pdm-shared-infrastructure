package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthConfigurationPropertiesStrategy;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.service.AuthenticationService;


@Slf4j
@RestController
@RequestMapping("/internal-user")
public class AuthenticationInternalUserController extends AbstractUserController {

    public AuthenticationInternalUserController(AuthenticationService authenticationService,
        InternalAuthConfigurationPropertiesStrategy uriProvider) {
        super(authenticationService, uriProvider);
    }
}
