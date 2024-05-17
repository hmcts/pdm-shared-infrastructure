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

import com.azure.spring.cloud.autoconfigure.implementation.aad.security.AadWebApplicationHttpSecurityConfigurer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.io.IOException;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends AadWebApplicationHttpSecurityConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);
    private static final String HOST = "spring.ldap.host";
    private static final String PORT = "spring.ldap.embedded.port";
    private static final String BASE_DN = "spring.ldap.embedded.base-dn";
    private static final String CUSTOM_LOGINPAGE = "custom.loginPage";
    private static final String HOME_URL = "/pdm/home";
    private static final String LOGIN_URL = "/login";
    private static final String LOGINERROR_URL = "/loginError";
    private static final String INVALIDSESSION_URL = "/invalidSession";


    @Autowired
    private Environment env;


    protected WebSecurityConfig() {
        super();
    }

    // Junit constructor
    protected WebSecurityConfig(Environment env) {
        this();
        this.env = env;
    }

//    @Bean
//    public SecurityFilterChain authProvider(HttpSecurity http){
//        String azureEnabled = env.getProperty("spring.cloud.azure.active-directory.enabled");
//
//        if (azureEnabled.equals("false")) {
//
//            try {
//                return getHttp(http).build();
//            } catch (Exception exception) {
//                LOG.error("securityFilterChain: {}", exception.getMessage());
//                return null;
//            }
//
//        }
//            return null;
//    }

    /**
     * Build the SecurityFilterChain from the http.
     */

    @ConditionalOnProperty(name = "spring.cloud.azure.active-directory.enabled", havingValue = "false")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            return getHttp(http).build();
        } catch (Exception exception) {
            LOG.error("securityFilterChain: {}", exception.getMessage());
            return null;
        }
    }

    /**
     * Get the http with the custom login form.
     */
    protected HttpSecurity getHttp(HttpSecurity http) {
        try {
            http.formLogin(formLogin -> formLogin.loginPage("/oauth2/authorization/azure")
                .loginProcessingUrl(LOGIN_URL).successHandler(getSuccessHandler())
                .failureHandler(getFailureHandler()).failureUrl(LOGINERROR_URL))
                .sessionManagement(session -> session.invalidSessionUrl(INVALIDSESSION_URL))
                .csrf(csrf -> csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));

            return http;
        } catch (Exception exception) {
            LOG.error("getHttp: {}", exception.getMessage());
            return null;
        }
    }

    /**
     * Configure the Ldap/Ldif authentication.
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) {
        try {
            auth.ldapAuthentication().userDnPatterns("uid={0},ou=people")
                .groupSearchFilter("member={0}").groupSearchBase("ou=groups").contextSource()
                .url(getLdapUrl()).and().passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder()).passwordAttribute("userPassword");
        } catch (Exception exception) {
            LOG.error("configure: {}", exception.getMessage());
        }
    }

    /**
     * Build the ldap url from the application.properties entries.
     *
     * @return url
     */
    private String getLdapUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(env.getProperty(HOST)).append(':').append(env.getProperty(PORT))
            .append('/').append(env.getProperty(BASE_DN));
        return stringBuilder.toString();
    }

    /**
     * Handle the login success. Redirect the page to /home.
     *
     * @return AuthenticationSuccessHandler
     */
    @Bean
    public AuthenticationSuccessHandler getSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {
                LOG.debug("The user {} has logged in.", authentication.getName());
                for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                    LOG.debug("Role = {}", grantedAuthority.getAuthority());
                }
                response.sendRedirect(HOME_URL);
            }
        };
    }

    /**
     * Handle the login failure. No redirection required as this is handled by the http.failureUrl()
     * off the custom loginPage().
     *
     * @return AuthenticationFailureHandler
     */
    @Bean
    public AuthenticationFailureHandler getFailureHandler() {
        return new AuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request,
                HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
                LOG.debug("Login Failure");
            }
        };
    }
}
