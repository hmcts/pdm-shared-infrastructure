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

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder.JwkSetUriJwtDecoderBuilder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.AuthenticationConfigurationPropertiesStrategy;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthConfigurationProperties;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.authentication.InternalAuthProviderConfigurationProperties;

import java.io.IOException;
import java.net.URI;
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
@SuppressWarnings({"PMD.ExcessiveImports", "PMD.TooManyFields", "PMD.CouplingBetweenObjects"})
class WebSecurityConfigTest extends AbstractJUnit {

    private static final String NOTNULL = "Result is Null";
    private static final String HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME =
        "mvcHandlerMappingIntrospector";

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
    private RequestMatcher mockRequestMatcher;

    @Mock
    private HandlerMappingIntrospector mockHandlerMappingIntrospector;

    @Mock
    private HttpServletRequest mockHttpServletRequest;

    @Mock
    private HttpServletResponse mockHttpServletResponse;

    @Mock
    private FilterChain mockFilterChain;

    @Mock
    private URI mockUri;

    @Mock
    private InternalAuthProviderConfigurationProperties mockInternalAuthProviderConfigurationProperties;

    @Mock
    private AuthenticationConfigurationPropertiesStrategy mockAuthenticationConfigurationPropertiesStrategy;

    @InjectMocks
    private WebSecurityConfig classUnderTest;

    private LocalWebSecurityConfig classUnderTestNoHttp;

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

        classUnderTest = new WebSecurityConfig(mockAuthenticationConfigurationPropertiesStrategy,
            mockInternalAuthConfigurationProperties,
            mockInternalAuthProviderConfigurationProperties);
        ReflectionTestUtils.setField(classUnderTest, "internalAuthConfigurationProperties",
            mockInternalAuthConfigurationProperties);
        ReflectionTestUtils.setField(classUnderTest, "internalAuthProviderConfigurationProperties",
            mockInternalAuthProviderConfigurationProperties);
        ReflectionTestUtils.setField(classUnderTest, "uriProvider",
            mockAuthenticationConfigurationPropertiesStrategy);

        classUnderTestNoHttp =
            new LocalWebSecurityConfig(mockAuthenticationConfigurationPropertiesStrategy,
                mockInternalAuthConfigurationProperties,
                mockInternalAuthProviderConfigurationProperties);
        ReflectionTestUtils.setField(classUnderTestNoHttp, "internalAuthConfigurationProperties",
            mockInternalAuthConfigurationProperties);
        ReflectionTestUtils.setField(classUnderTestNoHttp,
            "internalAuthProviderConfigurationProperties",
            mockInternalAuthProviderConfigurationProperties);
        ReflectionTestUtils.setField(classUnderTestNoHttp, "uriProvider",
            mockAuthenticationConfigurationPropertiesStrategy);
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
            // Expects
            Mockito.when(mockHttpSecurity.build()).thenReturn(mockSecurityFilterChain);
            // Run
            SecurityFilterChain result = classUnderTestNoHttp.filterChain(mockHttpSecurity);
            assertNotNull(result, NOTNULL);
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    void testGetAuthHttp() {
        try {
            HttpSecurity dummyHttpSecurity = getDummyHttpSecurity();
            // Run
            HttpSecurity result = classUnderTest.getAuthHttp(dummyHttpSecurity);
            assertNotNull(result, NOTNULL);
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    void testGetSecurityHttp() {
        try {
            HttpSecurity dummyHttpSecurity = getDummyHttpSecurity();
            // Run
            HttpSecurity result = classUnderTest.getSecurityHttp(dummyHttpSecurity);
            assertNotNull(result, NOTNULL);
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    void testPatternFilterChain() {
        try {
            // Expects
            Mockito.when(mockHttpSecurity.build()).thenReturn(mockSecurityFilterChain);
            // Run
            SecurityFilterChain result = classUnderTestNoHttp.patternFilterChain(mockHttpSecurity);
            assertNotNull(result, NOTNULL);
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    void testAuthorisationTokenExistenceFilter() {
        try {
            Mockito.when(mockAuthenticationConfigurationPropertiesStrategy.getLoginUri(null))
                .thenReturn(mockUri);
            classUnderTestNoHttp.testFilter();
            Mockito.when(mockHttpServletRequest.getHeader(Mockito.isA(String.class)))
                .thenReturn("Bearer");
            classUnderTestNoHttp.testFilter();
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    private HttpSecurity getDummyHttpSecurity() {
        String[] emptyStringArray = {};
        // mock HttpSecurity.authorizeHttpRequests()
        Mockito.when(mockApplicationContext.getBeanNamesForType(AuthorizationEventPublisher.class))
            .thenReturn(emptyStringArray);
        Mockito.when(mockApplicationContext.getBeanNamesForType(GrantedAuthorityDefaults.class))
            .thenReturn(emptyStringArray);
        mockCreateAuthenticationEntry();
        mockSecurityMatchers();

        HttpSecurity dummyHttpSecurity = new HttpSecurity(mockObjectPostProcessor,
            mockAuthenticationManagerBuilder, new ConcurrentHashMap<>());
        dummyHttpSecurity.authenticationManager(mockAuthenticationManager);
        dummyHttpSecurity.setSharedObject(ApplicationContext.class, mockApplicationContext);
        dummyHttpSecurity.securityMatcher(mockRequestMatcher);
        return dummyHttpSecurity;
    }

    private void mockSecurityMatchers() {
        Mockito.when(mockApplicationContext.getBean(ObjectPostProcessor.class))
            .thenReturn(mockObjectPostProcessor);

        // mock HandlerMappingIntrospector
        Mockito.when(mockApplicationContext.containsBean(HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME))
            .thenReturn(true);
        Mockito.when(mockApplicationContext.getBean(HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME))
            .thenReturn(mockHandlerMappingIntrospector);
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

    class LocalWebSecurityConfig extends WebSecurityConfig {

        public LocalWebSecurityConfig(AuthenticationConfigurationPropertiesStrategy uriProvider,
            InternalAuthConfigurationProperties internalAuthConfigurationProperties,
            InternalAuthProviderConfigurationProperties internalAuthProviderConfigurationProperties) {
            super(uriProvider, internalAuthConfigurationProperties,
                internalAuthProviderConfigurationProperties);
        }

        @Override
        protected HttpSecurity getAuthHttp(HttpSecurity http) {
            return mockHttpSecurity;
        }

        @Override
        protected HttpSecurity getSecurityHttp(HttpSecurity http) {
            return mockHttpSecurity;
        }

        public void testFilter() {
            AuthorisationTokenExistenceFilter filter = new AuthorisationTokenExistenceFilter();
            try {
                filter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse,
                    mockFilterChain);
            } catch (ServletException | IOException ex) {
                fail(ex.getMessage());
            }
        }
    }
}
