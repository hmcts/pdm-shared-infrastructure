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

package uk.gov.hmcts.pdm.publicdisplay.manager.service.api;

import org.springframework.security.core.userdetails.UserDetailsService;
import uk.gov.hmcts.pdm.publicdisplay.common.exception.ServiceException;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.UserDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.users.UserAddCommand;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.users.UserRemoveCommand;

import java.util.List;


/**
 * The Interface IUserDetailsService.
 *
 * @author uphillj
 */
public interface IUserDetailsService extends UserDetailsService {

    /**
     * Adds the user.
     *
     * @param userAddCommand the user add command
     */
    void addUser(UserAddCommand userAddCommand);

    /**
     * Removes the user.
     *
     * @param userRemoveCommand the user remove command
     * @throws ServiceException the service exception
     */
    void removeUser(UserRemoveCommand userRemoveCommand);

    /**
     * Gets the users.
     *
     * @return the users
     */
    List<UserDto> getUsers();

    /**
     * Checks if is session user.
     *
     * @param userName the user name
     * @return true, if is session user
     */
    boolean isSessionUser(String userName);

    /**
     * Checks if is session user exist.
     *
     * @return true, if is session user exist
     */
    boolean isSessionUserExist();

    /**
     * Checks if is user with user name.
     *
     * @param userName the user name
     * @return true, if is user with user name
     */
    boolean isUserWithUserName(String userName);
}
