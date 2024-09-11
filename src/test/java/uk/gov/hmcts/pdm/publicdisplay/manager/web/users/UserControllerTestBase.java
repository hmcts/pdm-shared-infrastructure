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

import org.easymock.Capture;
import org.easymock.EasyMockExtension;
import org.easymock.IAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.dto.UserDto;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.UserRole;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.UserDetailsService;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IUserDetailsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The Class UserController.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
@SuppressWarnings("PMD.LawOfDemeter")
abstract class UserControllerTestBase extends AbstractJUnit {

    protected static final String NOT_EQUAL = "Not equal";

    protected static final String FALSE = "False";

    protected static final String NULL = "Null";

    protected static final String NOT_NULL = "Not null";

    protected static final String TRUE = "True";

    protected static final String USERNAME = "userName";

    protected static final String BTN_ADD = "btnAdd";

    protected static final String BTN_REMOVE_CONFIRM = "btnRemoveConfirm";

    /** The view name manage user. */
    protected String viewNameManageUser;
    
    /** The view name mapping manage user. */
    protected String mappingNameManageUserUrl;

    /** The mock mvc. */
    protected MockMvc mockMvc;

    /** The mock user service. */
    protected IUserDetailsService mockUserDetailsService;

    /** The mock user add validator. */
    protected UserAddValidator mockUserAddValidator;

    /** The mock user remove validator. */
    protected UserRemoveValidator mockUserRemoveValidator;

    /** The mock user page state holder. */
    protected UserPageStateHolder mockUserPageStateHolder;

    /** The users. */
    protected final List<UserDto> users = getTestUsers();

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        // Setup the mock version of the called classes
        mockUserDetailsService = createMock(UserDetailsService.class);
        mockUserPageStateHolder = createMock(UserPageStateHolder.class);
        mockUserAddValidator = createMock(UserAddValidator.class);
        mockUserRemoveValidator = createMock(UserRemoveValidator.class);
        
        // Create a new version of the class under test
        UserController classUnderTest = new UserController();

        // Map the mock to the class under tests called class
        ReflectionTestUtils.setField(classUnderTest, "userDetailsService", mockUserDetailsService);
        ReflectionTestUtils.setField(classUnderTest, "userPageStateHolder",
            mockUserPageStateHolder);
        ReflectionTestUtils.setField(classUnderTest, "userAddValidator", mockUserAddValidator);
        ReflectionTestUtils.setField(classUnderTest, "userRemoveValidator",
            mockUserRemoveValidator);

        // Get the static variables from the class under test
        mappingNameManageUserUrl =
            (String) ReflectionTestUtils.getField(classUnderTest, "MAPPING_MANAGE_USERS");
        mappingNameManageUserUrl += mappingNameManageUserUrl;
        viewNameManageUser =
            (String) ReflectionTestUtils.getField(classUnderTest, "VIEW_NAME_MANAGE_USERS");

        // Stop circular view path error
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view");
        viewResolver.setSuffix(".jsp");

        // Setup the mock version of the modelMvc
        mockMvc =
            MockMvcBuilders.standaloneSetup(classUnderTest).setViewResolvers(viewResolver).build();
    }

    /**
     * Assert view name.
     *
     * @param results the results
     * @param viewName the view name
     */
    protected void assertViewName(final MvcResult results, final String viewName) {
        assertNotNull(results, NULL);
        final String actualViewName = results.getModelAndView().getViewName();
        if (actualViewName.startsWith("forward:") || actualViewName.startsWith("redirect:")) {
            assertTrue(actualViewName.contains(viewName), FALSE);
        } else {
            assertEquals(actualViewName, viewName, NOT_EQUAL);
        }
    }

    /**
     * Assert manage users model.
     *
     * @param modelMap the model map
     */
    protected void assertManageUsersModel(final ModelMap modelMap) {
        assertTrue(modelMap.get("command") instanceof UserAddCommand, FALSE);
        assertTrue(modelMap.get("userRemoveCommand") instanceof UserRemoveCommand, FALSE);
        assertTrue(Arrays.equals(UserRole.values(), (UserRole[]) modelMap.get("roleList")), FALSE);
        assertEquals(users, modelMap.get("userList"), NOT_EQUAL);
    }

    /**
     * Expect user add validator.
     *
     * @param capturedCommand the captured command
     * @param capturedErrors the captured errors
     * @param isSuccess the is success
     */
    protected void expectUserAddValidator(final Capture<UserAddCommand> capturedCommand,
        final Capture<BindingResult> capturedErrors, final boolean isSuccess) {
        mockUserAddValidator.validate(capture(capturedCommand), capture(capturedErrors));
        expectValidatorSuccess(isSuccess);
        replay(mockUserAddValidator);
    }

    /**
     * Expect user remove validator.
     *
     * @param capturedCommand the captured command
     * @param capturedErrors the captured errors
     * @param isSuccess the is success
     */
    protected void expectUserRemoveValidator(final Capture<UserRemoveCommand> capturedCommand,
        final Capture<BindingResult> capturedErrors, final boolean isSuccess) {
        mockUserRemoveValidator.validate(capture(capturedCommand), capture(capturedErrors));
        expectValidatorSuccess(isSuccess);
        replay(mockUserRemoveValidator);
    }

    /**
     * Expect validator success.
     *
     * @param isSuccess the is success
     */
    protected void expectValidatorSuccess(final boolean isSuccess) {
        if (isSuccess) {
            expectLastCall();
        } else {
            expectLastCall().andAnswer(new IAnswer<Void>() {
                @Override
                public Void answer() {
                    ((BindingResult) getCurrentArguments()[1]).reject("mock error message");
                    return null;
                }
            });
        }
    }

    /**
     * Gets the test user.
     *
     * @param id the id
     * @param userRole the user role
     * @return the test user
     */
    protected UserDto getTestUser(final Long id, final UserRole userRole) {
        final UserDto user = new UserDto();
        user.setUserName(USERNAME + id);
        user.setUserRole(userRole);
        return user;
    }

    /**
     * Gets the test users.
     *
     * @return the test users
     */
    protected List<UserDto> getTestUsers() {
        final List<UserDto> users = new ArrayList<>();
        users.add(getTestUser(1L, UserRole.ROLE_ADMIN));
        users.add(getTestUser(2L, UserRole.ROLE_USER));
        return users;
    }

    /**
     * Gets the test user add command.
     *
     * @param userName the user name
     * @param userRole the user role
     * @return the test user add command
     */
    protected UserAddCommand getTestUserAddCommand(final String userName, final UserRole userRole) {
        final UserAddCommand userCommand = new UserAddCommand();
        userCommand.setUserName(userName);
        userCommand.setUserRole(userRole);
        return userCommand;
    }

    /**
     * Gets the test user remove command.
     *
     * @param userName the user name
     * @return the test user remove command
     */
    protected UserRemoveCommand getTestUserRemoveCommand(final String userName) {
        final UserRemoveCommand userCommand = new UserRemoveCommand();
        userCommand.setUserName(userName);
        return userCommand;
    }
}
