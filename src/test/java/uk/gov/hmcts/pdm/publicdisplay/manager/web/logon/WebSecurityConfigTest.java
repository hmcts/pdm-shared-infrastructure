/*
 * Copyrights and Licenses
 * 
 * Copyright (c) 2015-2024 by the Ministry of Justice. All rights reserved. Redistribution and use
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

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The Class WebSecurityConfigTest.
 *
 * @author harrism
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WebSecurityConfigTest extends AbstractJUnit {

    private static final String CUSTOM_LOGINPAGE = "custom.loginPage";
    private static final String EQUALS = "Result are not Equal";
    private static final String NULL = "Result are not Null";
    private static final String TRUE = "Result is False";

    @Mock
    private Environment mockEnvironment;

    @Mock
    private ObjectPostProcessor<Object> mockObjectPostProcessor;

    @Mock
    private AuthenticationManager mockAuthenticationManager;

    @Mock
    private AuthenticationManagerBuilder mockAuthenticationManagerBuilder;

    @Mock
    private HttpSecurity mockHttpSecurity;

    @Mock
    private DefaultSecurityFilterChain mockDefaultSecurityFilterChain;

    @Mock
    private HttpServletRequest mockHttpServletRequest;

    @Mock
    private HttpServletResponse mockHttpServletResponse;

    @Mock
    private Authentication mockAuthentication;

    @Mock
    private AuthenticationException mockAuthenticationException;

    @Test
    void testSecurityFilterChainSuccess() {
        try {
            // Setup
            HttpSecurity dummyHttpSecurity = getDummyHttpSecurity();
            WebSecurityConfig classUnderTest = new WebSecurityConfig(mockEnvironment) {
                @Override
                protected HttpSecurity getHttp(HttpSecurity http) {
                    // Test the getHttp() using the dummy version
                    Mockito.when(mockEnvironment.getProperty(CUSTOM_LOGINPAGE))
                        .thenReturn("/logon/signin");
                    super.getHttp(dummyHttpSecurity);
                    // Return the mockHttp version for the build()
                    return http;
                }
            };

            // Expects
            Mockito.when(mockHttpSecurity.build()).thenReturn(mockDefaultSecurityFilterChain);

            // Run
            SecurityFilterChain result = classUnderTest.securityFilterChain(mockHttpSecurity);

            // Checks
            assertEquals(result, mockDefaultSecurityFilterChain, EQUALS);
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    void testSecurityFilterChainFailure() throws Exception {
        // Setup
        HttpSecurity dummyHttpSecurity = getDummyHttpSecurity();
        WebSecurityConfig classUnderTest = new WebSecurityConfig(mockEnvironment) {
            @Override
            protected HttpSecurity getHttp(HttpSecurity http) {
                return super.getHttp(dummyHttpSecurity);
            }
        };
        // Expects
        Mockito
            .when(mockHttpSecurity.formLogin(
                formLogin -> formLogin.loginPage(mockEnvironment.getProperty(CUSTOM_LOGINPAGE))))
            .thenThrow(new RuntimeException());
        // Run
        SecurityFilterChain result = classUnderTest.securityFilterChain(mockHttpSecurity);
        // Checks
        assertNull(result, NULL);
    }

    @Test
    void testConfigureSuccess() {
        // Setup
        WebSecurityConfig classUnderTest = new WebSecurityConfig(mockEnvironment);
        // Run
        boolean result = false;
        try {
            classUnderTest.configure(new AuthenticationManagerBuilder(mockObjectPostProcessor));
            result = true;
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testConfigureFailure() {
        try {
            // Setup
            WebSecurityConfig classUnderTest = new WebSecurityConfig(mockEnvironment);
            // Expect
            Mockito.when(mockAuthenticationManagerBuilder.ldapAuthentication())
                .thenThrow(new RuntimeException());
            // Run
            classUnderTest.configure(mockAuthenticationManagerBuilder);
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    void testSuccessHandler() {
        // Setup
        WebSecurityConfig classUnderTest = new WebSecurityConfig(mockEnvironment);
        AuthenticationSuccessHandler handlerUnderTest = classUnderTest.getSuccessHandler();
        // Expects
        Mockito.when(mockAuthentication.getName()).thenReturn("username");
        // Run
        boolean result = false;
        try {
            handlerUnderTest.onAuthenticationSuccess(mockHttpServletRequest,
                mockHttpServletResponse, mockAuthentication);
            result = true;
        } catch (IOException | ServletException exception) {
            fail(exception.getMessage());
        }
        assertTrue(result, TRUE);
    }

    @Test
    void testFailureHandler() {
        // Setup
        WebSecurityConfig classUnderTest = new WebSecurityConfig(mockEnvironment);
        AuthenticationFailureHandler handlerUnderTest = classUnderTest.getFailureHandler();
        // Expects
        Mockito.when(mockAuthentication.getName()).thenReturn("username");
        // Run
        boolean result = false;
        try {
            handlerUnderTest.onAuthenticationFailure(mockHttpServletRequest,
                mockHttpServletResponse, mockAuthenticationException);
            result = true;
        } catch (IOException | ServletException exception) {
            fail(exception.getMessage());
        }
        assertTrue(result, TRUE);
    }

    private HttpSecurity getDummyHttpSecurity() {
        HttpSecurity dummyHttpSecurity = new HttpSecurity(mockObjectPostProcessor,
            mockAuthenticationManagerBuilder, new ConcurrentHashMap<>());
        dummyHttpSecurity.authenticationManager(mockAuthenticationManager);
        return dummyHttpSecurity;
    }
}
