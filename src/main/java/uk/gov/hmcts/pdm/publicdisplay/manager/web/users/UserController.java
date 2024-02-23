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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.users;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.hmcts.pdm.publicdisplay.common.exception.XpdmException;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.UserDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.UserRole;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.UserDetailsService;

import java.util.List;

/**
 * The Class UserController.
 *
 * @author harrism
 */
@Controller
@RequestMapping("/users")
public class UserController {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String METHOD = "Method ";
    private static final String THREE_PARAMS = "{}{}{}";
    private static final String FOUR_PARAMS = "{}{}{}{}";
    private static final String STARTS = " - starts";
    private static final String ENDS = " - ends";
    private static final String ADDING_USERS_TO_MODEL = " adding user data to model";
    private static final String ADDING_ROLES_TO_MODEL = " adding role data to model";
    private static final String ROLELIST = "roleList";
    private static final String SETTING_VIEW_NAME = " setting view name";
    private static final String USERLIST = "userList";
    private static final String USERERRORS = "userErrors";

    /** The Constant for the JSP Folder. */
    private static final String FOLDER_USERS = "users";
    
    /**
     * Manage Users Url.
     */
    private static final String MAPPING_MANAGE_USERS = "/users";

    /**
     * View Manage Users View.
     */
    private static final String VIEW_NAME_MANAGE_USERS = FOLDER_USERS + MAPPING_MANAGE_USERS;

    /**
     * Validator for UserAddValidator class.
     */
    @Autowired
    private UserAddValidator userAddValidator;

    /**
     * Validator for UserRemoveValidator class.
     */
    @Autowired
    private UserRemoveValidator userRemoveValidator;

    /**
     * Our UserPageStateHolder.
     */
    @Autowired
    private UserPageStateHolder userPageStateHolder;

    /**
     * The user details service.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Show manage users.
     *
     * @param model the model
     * @return the model and view
     */
    @RequestMapping(value = MAPPING_MANAGE_USERS, method = RequestMethod.GET)
    public ModelAndView showManageUsers(final ModelAndView model) {
        final String methodName = "showManageUsers";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        // Clear the previously stored values for the page
        userPageStateHolder.reset();

        // retrieve and add the user/role list to the pageStateHolder
        setPageStateUserList();

        // Add blank add command objects to the page
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, " adding userAddCommand to model");
        model.addObject("userAddCommand", new UserAddCommand());
        // Do the same for command to fix the JspTagException error
        model.addObject("command", new UserAddCommand());

        // Add blank remove command objects to the page
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, " adding userRemoveCommand to model");
        model.addObject("userRemoveCommand", new UserRemoveCommand());

        // Add the user data to model
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ADDING_USERS_TO_MODEL);
        model.addObject(USERLIST, userPageStateHolder.getUsers());

        // Add the role data to model
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ADDING_ROLES_TO_MODEL);
        model.addObject(ROLELIST, UserRole.values());

        // Set the view name
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, SETTING_VIEW_NAME);
        model.setViewName(VIEW_NAME_MANAGE_USERS);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return model;
    }

    /**
     * Adds the user.
     *
     * @param userAddCommand the user add command
     * @param result the result
     * @param model the model
     * @return the model and view
     */
    @RequestMapping(value = MAPPING_MANAGE_USERS, method = RequestMethod.POST, params = "btnAdd")
    public ModelAndView addUser(@Valid final UserAddCommand userAddCommand,
        final BindingResult result, final ModelAndView model) {
        final String methodName = "addUser";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        userAddValidator.validate(userAddCommand, result);
        if (!result.hasErrors()) {
            try {
                // Add the user
                userDetailsService.addUser(userAddCommand);
                LOGGER.debug(THREE_PARAMS, METHOD, methodName, " - added user");

                // Reset the command variables
                userAddCommand.reset();

                // retrieve and add the user list to the pageStateHolder
                setPageStateUserList();

                model.addObject("successMessage", "User has been added successfully.");
            } catch (final DataAccessException | XpdmException ex) {
                // Log the error
                LOGGER.error(FOUR_PARAMS, METHOD, methodName, " Unable to add user ", ex);
                // Reject
                result.reject(USERERRORS, "Unable to add user: " + ex.getMessage());
            }
        }

        // Add blank remove command object to the page
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, " adding userRemoveCommand to model");
        model.addObject("userRemoveCommand", new UserRemoveCommand());

        // Add the user data to model
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ADDING_USERS_TO_MODEL);
        model.addObject(USERLIST, userPageStateHolder.getUsers());

        // Add the role data to model
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ADDING_ROLES_TO_MODEL);
        model.addObject(ROLELIST, UserRole.values());

        // Set the view name
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, SETTING_VIEW_NAME);
        model.setViewName(VIEW_NAME_MANAGE_USERS);

        model.addObject("command", userAddCommand);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return model;
    }

    /**
     * Remove the user.
     *
     * @param userRemoveCommand the user remove command
     * @param result the result
     * @param model the model
     * @return the model and view
     */
    @RequestMapping(value = MAPPING_MANAGE_USERS, method = RequestMethod.POST,
        params = "btnRemoveConfirm")
    public ModelAndView removeUser(@Valid final UserRemoveCommand userRemoveCommand,
        final BindingResult result, final ModelAndView model) {
        final String methodName = "removeUser";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        userRemoveValidator.validate(userRemoveCommand, result);
        if (!result.hasErrors()) {
            try {
                // Remove the user
                userDetailsService.removeUser(userRemoveCommand);
                LOGGER.debug(THREE_PARAMS, METHOD, methodName, " - removed user");

                // Reset the command variables
                userRemoveCommand.reset();

                // retrieve and add the user list to the pageStateHolder
                setPageStateUserList();

                // Add successMessage for display on page
                model.addObject("successMessage", "User has been removed successfully.");
            } catch (final DataAccessException | XpdmException ex) {
                // Log the error
                LOGGER.error(THREE_PARAMS, METHOD, methodName, " Unable to remove user ", ex);
                // Reject
                result.reject(USERERRORS, "Unable to remove user: " + ex.getMessage());
            }
        }

        // Add blank add command object to the page
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, " adding userAddCommand to model");
        model.addObject("command", new UserAddCommand());

        // Add the user data to model
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ADDING_USERS_TO_MODEL);
        model.addObject(USERLIST, userPageStateHolder.getUsers());

        // Add the role data to model
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, ADDING_ROLES_TO_MODEL);
        model.addObject(ROLELIST, UserRole.values());

        // Set the view name
        LOGGER.debug(THREE_PARAMS, METHOD, methodName, SETTING_VIEW_NAME);
        model.setViewName(VIEW_NAME_MANAGE_USERS);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
        return model;
    }

    /**
     * Sets the page state user list.
     */
    private void setPageStateUserList() {
        final String methodName = "setPageStateUserList";
        LOGGER.info(THREE_PARAMS, METHOD, methodName, STARTS);

        // retrieve the users
        final List<UserDto> usersList = userDetailsService.getUsers();
        userPageStateHolder.setUsers(usersList);

        LOGGER.info(THREE_PARAMS, METHOD, methodName, ENDS);
    }
}
