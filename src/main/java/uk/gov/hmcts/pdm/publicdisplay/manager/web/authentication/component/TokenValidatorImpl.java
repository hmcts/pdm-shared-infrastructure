package uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTClaimsSet.Builder;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthProviderConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.model.JwtValidationResult;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenValidatorImpl implements TokenValidator {

    @Override
    public JwtValidationResult validate(String accessToken,
        AuthProviderConfigurationProperties providerConfig,
        AuthConfigurationProperties configuration) {
        log.info("Validating JWT: {}", accessToken);

        JWSVerificationKeySelector<SecurityContext> keySelector =
            new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, providerConfig.getJwkSource());

        DefaultJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        jwtProcessor.setJWSKeySelector(keySelector);

        JWTClaimsSet jwtClaimsSet = new Builder().issuer(configuration.getIssuerUri()).build();
        DefaultJWTClaimsVerifier<SecurityContext> claimsVerifier =
            new DefaultJWTClaimsVerifier<>(configuration.getClientId(), jwtClaimsSet,
                new HashSet<>(Arrays.asList(JWTClaimNames.AUDIENCE, JWTClaimNames.ISSUER,
                    JWTClaimNames.EXPIRATION_TIME, JWTClaimNames.ISSUED_AT, JWTClaimNames.SUBJECT,
                    configuration.getClaims())));
        jwtProcessor.setJWTClaimsSetVerifier(claimsVerifier);

        try {
            jwtProcessor.process(accessToken, null);
            log.info("JWT Token Validation successful");
        } catch (ParseException | JOSEException | BadJOSEException e) {
            log.info("JWT Token Validation failed", e);
            return new JwtValidationResult(false, e.getMessage());
        }

        return new JwtValidationResult(true, null);
    }

}
