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

package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import com.pdm.hb.jpa.AuthorizationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.pdm.publicdisplay.common.exception.ServiceException;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.UserDetails;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUserDetails;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.UserDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.UserModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.UserRole;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IUserDetailsService;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.users.UserAddCommand;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.users.UserRemoveCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class UserDetailsService.
 * 
 * @author uphillj
 *
 */
@Component
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@SuppressWarnings("PMD.LawOfDemeter")
public class UserDetailsService extends UserDetailsServiceRepository
    implements IUserDetailsService {
    /**
     * Set up our logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

    private static final String METHOD = "Method ";
    private static final String THREE_PARAMS = "{}{}{}";
    private static final String STARTS = " - starts";
    private static final String ENDS = " - ends";

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.
     * lang. String)
     */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(
        final String userName) {
        // Default user is one with no authorities
        UserModel user = new UserModel(userName, "N/A", AuthorityUtils.NO_AUTHORITIES);

        // If user has been authorised to use the application, create user with their role
        final IUserDetails userDetails =
            getXhbDispMgrUserDetailsRepository().findUserDetailsByUserName(userName);
        if (userDetails != null) {
            user = new UserModel(userName, "N/A",
                AuthorityUtils.createAuthorityList(userDetails.getUserRole()));
        }

        // Return user with their authorities
        return user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IUserDetailsService#addUser
     * (uk.gov.hmcts.pdm.publicdisplay.manager.web.users.UserAddCommand)
     */
    @Override
    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addUser(final UserAddCommand userAddCommand) {
        final String methodName = "addUser";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        // Create the user details
        final IUserDetails userDetails = new UserDetails();
        userDetails.setUserName(userAddCommand.getUserName());
        userDetails.setUserRole(userAddCommand.getUserRole().toString());

        // Add the user
        getXhbDispMgrUserDetailsRepository().saveDaoFromBasicValue(userDetails);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IUserDetailsService#removeUser
     * (uk.gov.hmcts.pdm.publicdisplay.manager.web.users.UserRemoveCommand)
     */
    @Override
    @Secured(UserRole.ROLE_ADMIN_VALUE)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeUser(final UserRemoveCommand userRemoveCommand) {
        final String methodName = "removeUser";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        final IUserDetails userDetails = getXhbDispMgrUserDetailsRepository()
            .findUserDetailsByUserName(userRemoveCommand.getUserName());
        if (userDetails == null) {
            throw new ServiceException("Unable to remove user. User does not exist");
        }
        // Remove the user
        getXhbDispMgrUserDetailsRepository().deleteDaoFromBasicValue(userDetails);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IUserDetailsService#getUsers()
     */
    @Override
    @Secured(UserRole.ROLE_ADMIN_VALUE)
    public List<UserDto> getUsers() {
        final String methodName = "getUsers";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        final List<UserDto> userDtoList = new ArrayList<>();

        // retrieve the user details from the database
        final List<IUserDetails> userDetails =
            getXhbDispMgrUserDetailsRepository().getUserDetails();
        for (IUserDetails userDetail : userDetails) {
            final UserDto userDto = createUserDto(userDetail);
            userDtoList.add(userDto);
        }

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return userDtoList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IUserDetailsService# isSessionUser
     * (java.lang.String)
     */
    @Override
    public boolean isSessionUser(final String userName) {
        boolean isMatch = false;
        if (userName != null) {
            final String currentUser = getSessionUserName();
            isMatch = currentUser != null && currentUser.equals(userName);
        }
        return isMatch;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IUserDetailsService#
     * isSessionUserExist()
     */
    @Override
    public boolean isSessionUserExist() {
        final String currentUser = getSessionUserName();
        return isUserWithUserName(currentUser);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IUserDetailsService#
     * isUserWithUserName(java.lang. String)
     */
    @Override
    public boolean isUserWithUserName(final String userName) {
        boolean isExist = false;
        if (userName != null) {
            isExist = getXhbDispMgrUserDetailsRepository().isUserDetailsWithUserName(userName);
        }
        return isExist;
    }

    /**
     * Gets the session user name.
     *
     * @return the session user name
     */
    private String getSessionUserName() {
        // Get the Spring security authentication
        // object which has the user name of the current logged on user
        if (!"".equals(AuthorizationUtil.getUsername())) {
            return AuthorizationUtil.getUsername();
        }

        return null;
    }

    /**
     * Creates the user dto.
     *
     * @param userDetails the user details
     * @return the user dto
     */
    private UserDto createUserDto(final IUserDetails userDetails) {
        final UserDto userDto = new UserDto();
        if (userDetails != null) {
            userDto.setUserName(userDetails.getUserName());
            userDto.setUserRole(UserRole.valueOf(userDetails.getUserRole()));
        }
        return userDto;
    }
}
