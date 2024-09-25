/*
 * Copyrights and Licenses
 * 
 * Copyright (c) 2015-2016 by the Ministry of Justice. All rights reserved. Redistribution and use
 * in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer. - Redistributions in binary form
 * must reproduce the above copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the distribution. - Products derived
 * from this software may not be called "XHIBIT Public Display Manager" nor may
 * "XHIBIT Public Display Manager" appear in their names without prior written permission of the
 * Ministry of Justice. - Redistributions of any form whatsoever must retain the following
 * acknowledgment: "This product includes XHIBIT Public Display Manager." This software is provided
 * "as is" and any expressed or implied warranties, including, but not limited to, the implied
 * warranties of merchantability and fitness for a particular purpose are disclaimed. In no event
 * shall the Ministry of Justice or its contributors be liable for any direct, indirect, incidental,
 * special, exemplary, or consequential damages (including, but not limited to, procurement of
 * substitute goods or services; loss of use, data, or profits; or business interruption). However
 * caused any on any theory of liability, whether in contract, strict liability, or tort (including
 * negligence or otherwise) arising in any way out of the use of this software, even if advised of
 * the possibility of such damage.
 */

package uk.gov.hmcts.pdm.publicdisplay.manager.web.logon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder.JwkSetUriJwtDecoderBuilder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthProviderConfigurationProperties;

import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class WebSecurityConfig.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WebSecurityConfigTest extends AbstractJUnit {

    private static final String NOTNULL = "Result is Null";

    @Mock
    private HttpSecurity mockHttpSecurity;

    @Mock
    private DefaultSecurityFilterChain mockSecurityFilterChain;

    @Mock
    private ObjectPostProcessor<Object> mockObjectPostProcessor;

    @Mock
    private AuthenticationManager mockAuthenticationManager;

    @Mock
    private AuthenticationManagerBuilder mockAuthenticationManagerBuilder;

    @Mock
    private InternalAuthConfigurationProperties mockInternalAuthConfigurationProperties;

    @Mock
    private ApplicationContext mockApplicationContext;

    @Mock
    private AuthorizationEventPublisher mockAuthorizationEventPublisher;

    @Mock
    private NimbusJwtDecoder mockNimbusJwtDecoder;

    @Mock
    private JwkSetUriJwtDecoderBuilder mockJwkSetUriJwtDecoderBuilder;

    @Mock
    private InternalAuthProviderConfigurationProperties mockInternalAuthProviderConfigurationProperties;

    @InjectMocks
    private WebSecurityConfig classUnderTest;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        Mockito.mockStatic(NimbusJwtDecoder.class);
        mockInternalAuthConfigurationProperties =
            Mockito.mock(InternalAuthConfigurationProperties.class);
        mockInternalAuthProviderConfigurationProperties =
            Mockito.mock(InternalAuthProviderConfigurationProperties.class);

        classUnderTest = new WebSecurityConfig(mockInternalAuthConfigurationProperties,
            mockInternalAuthProviderConfigurationProperties);
    }

    /**
     * Teardown.
     */
    @AfterEach
    public void teardown() {
        Mockito.clearAllCaches();
    }

    @Test
    void testFilterChain() {
        try {
            // Setup
            WebSecurityConfig localClassUnderTest =
                new WebSecurityConfig(mockInternalAuthConfigurationProperties,
                    mockInternalAuthProviderConfigurationProperties) {
                    @Override
                    protected HttpSecurity getHttp(HttpSecurity http) {
                        return mockHttpSecurity;
                    }
                };
            // Expects
            Mockito.when(mockHttpSecurity.build()).thenReturn(mockSecurityFilterChain);
            // Run
            SecurityFilterChain result = localClassUnderTest.filterChain(mockHttpSecurity);
            assertNotNull(result, NOTNULL);
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    void testGetHttp() {
        try {
            HttpSecurity dummyHttpSecurity = getDummyHttpSecurity();
            // Run
            HttpSecurity result = classUnderTest.getHttp(dummyHttpSecurity);
            assertNotNull(result, NOTNULL);
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    void testWebSecurityCustomizer() {
        WebSecurityCustomizer result = classUnderTest.webSecurityCustomizer();
        assertNotNull(result, NOTNULL);
    }

    private HttpSecurity getDummyHttpSecurity() {
        String[] emptyStringArray = {};
        // mock HttpSecurity.authorizeHttpRequests()
        Mockito.when(mockApplicationContext.getBeanNamesForType(AuthorizationEventPublisher.class))
            .thenReturn(emptyStringArray);
        Mockito.when(mockApplicationContext.getBeanNamesForType(GrantedAuthorityDefaults.class))
            .thenReturn(emptyStringArray);
        mockCreateAuthenticationEntry();

        HttpSecurity dummyHttpSecurity = new HttpSecurity(mockObjectPostProcessor,
            mockAuthenticationManagerBuilder, new ConcurrentHashMap<>());
        dummyHttpSecurity.authenticationManager(mockAuthenticationManager);
        dummyHttpSecurity.setSharedObject(ApplicationContext.class, mockApplicationContext);
        return dummyHttpSecurity;
    }

    private void mockCreateAuthenticationEntry() {
        // mock jwtIssuerAuthenticationManagerResolver
        Mockito.when(mockInternalAuthConfigurationProperties.getIssuerUri()).thenReturn("Auth");
        Mockito.when(mockInternalAuthProviderConfigurationProperties.getJwkSetUri())
            .thenReturn("Auth");
        // mock mockNimbusJwtDecoder
        Mockito.when(NimbusJwtDecoder.withJwkSetUri(Mockito.isA(String.class)))
            .thenReturn(mockJwkSetUriJwtDecoderBuilder);
        Mockito.when(mockJwkSetUriJwtDecoderBuilder.jwsAlgorithm(SignatureAlgorithm.RS256))
            .thenReturn(mockJwkSetUriJwtDecoderBuilder);
        Mockito.when(mockJwkSetUriJwtDecoderBuilder.build()).thenReturn(mockNimbusJwtDecoder);
    }

}
