package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.controller;

import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthConfigurationPropertiesStrategy;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.service.AuthenticationService;

import java.text.ParseException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/internal-user")
@SuppressWarnings("PMD.LawOfDemeter")
public class AuthenticationInternalUserController extends AbstractUserController {

    public AuthenticationInternalUserController(AuthenticationService authenticationService,
        InternalAuthConfigurationPropertiesStrategy uriProvider) {
        super(authenticationService, uriProvider);
    }

    @Override
    Optional<String> parseEmailAddressFromAccessToken(String accessToken) throws ParseException {
        SignedJWT jwt = SignedJWT.parse(accessToken);
        final String emailAddresses =
            jwt.getJWTClaimsSet().getStringClaim(uriProvider.getConfiguration().getClaims());
        if (emailAddresses == null || emailAddresses.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(emailAddresses);
    }
}
