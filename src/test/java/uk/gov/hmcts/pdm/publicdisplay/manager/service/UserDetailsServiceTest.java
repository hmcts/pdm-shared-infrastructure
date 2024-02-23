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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import uk.gov.hmcts.pdm.business.entities.xhbdispmgruserdetails.XhbDispMgrUserDetailsRepository;
import uk.gov.hmcts.pdm.publicdisplay.common.exception.ServiceException;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUserDetails;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.UserRole;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IUserDetailsService;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.users.UserAddCommand;
import uk.gov.hmcts.pdm.publicdisplay.manager.web.users.UserRemoveCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Unit test for UserDetailsService which uses Mockito in addition to EasyMock to be able to mock a
 * static method on a Spring class.
 *
 * @author boparaij
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
abstract class UserDetailsServiceTest extends AbstractJUnit {

    /** The Constant USER_NAME. */
    protected static final String USER_NAME = "User";

    protected static final String NOT_EQUAL = "Not equal";

    protected static final String FALSE = "False";

    protected static final String NULL = "Null";

    protected static final String NOT_NULL = "Not null";

    protected static final String TRUE = "True";

    /** The class under test. */
    @InjectMocks
    protected final IUserDetailsService classUnderTest = new UserDetailsService();

    /** The mock disp mgr user service repo. */
    @Mock
    protected XhbDispMgrUserDetailsRepository mockUserDetailsRepo;

    // /** The user details. */
    // protected final IUserDetails userDetails = getTestUserDetails("");

    /** The user add command. */
    protected final UserAddCommand userAddCommand = getTestUserAddCommand();

    /** The user remove command. */
    protected final UserRemoveCommand userRemoveCommand = getTestUserRemoveCommand();

    // /** The user details list. */
    // protected final List<IUserDetails> userDetailsList = getTestUserDetailList();

    /** The mock security context. */
    @Mock
    protected SecurityContext mockSecurityContext;

    /** The mock authentication. */
    @Mock
    protected Authentication mockAuthentication;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        Mockito.mockStatic(SecurityContextHolder.class);
    }

    /**
     * Teardown.
     */
    @AfterEach
    public void teardown() {
        Mockito.clearAllCaches();
    }

    // /**
    // * Test valid username.
    // */
    // @Test
    // void testLoadUserByUsernameValid() {
    // // Capture the UserName
    // final ArgumentCaptor<String> capturedUserName = ArgumentCaptor.forClass(String.class);
    //
    // // Define a mock version of the called methods, to retrieve user details via a passed in
    // // user
    // // name
    // Mockito.when(mockUserDetailsRepo.findUserDetailsByUserName(capturedUserName.capture()))
    // .thenReturn(userDetails);
    //
    // // Perform the test
    // final UserDetails result = classUnderTest.loadUserByUsername(USER_NAME);
    //
    // // Capture
    // Mockito.verify(mockUserDetailsRepo).findUserDetailsByUserName(capturedUserName.capture());
    //
    // // Check the results
    // assertNotNull(result, NULL);
    // assertEquals(result.getUsername(), capturedUserName.getValue(), NOT_EQUAL);
    // assertTrue(
    // result.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_USER_VALUE)),
    // NOT_EQUAL);
    // }
    //
    // /**
    // * Test load user error.
    // *
    // * @throws Exception the exception
    // */
    // @Test
    // void testLoadUserByUsernameWithException() throws Exception {
    //
    // DataRetrievalFailureException dataRetrievalFailureException =
    // new DataRetrievalFailureException("Mock data access exception");
    //
    // // Capture the UserName
    // final ArgumentCaptor<String> capturedUserName = ArgumentCaptor.forClass(String.class);
    //
    // // Define a mock version of method calls with exception handling
    // Mockito.when(mockUserDetailsRepo.findUserDetailsByUserName(Mockito.isA(String.class)))
    // .thenThrow(dataRetrievalFailureException);
    //
    // UserDetails result = null;
    // try {
    // // Perform the test
    // result = classUnderTest.loadUserByUsername(USER_NAME);
    // } catch (Exception e) {
    // assertEquals(dataRetrievalFailureException.getClass(), e.getClass(), NOT_EQUAL);
    // } finally {
    // // Capture
    // verify(mockUserDetailsRepo).findUserDetailsByUserName(capturedUserName.capture());
    //
    // // Check the results
    // assertNull(result, NOT_NULL);
    // assertEquals(USER_NAME, capturedUserName.getValue(), NOT_EQUAL);
    // }
    // }
    //
    // /**
    // * Test invalid username.
    // */
    // @Test
    // void testLoadUserByUsernameInvalid() {
    // // Capture the UserName
    // final ArgumentCaptor<String> capturedUserName = ArgumentCaptor.forClass(String.class);
    //
    // // Define a mock version of the called methods with invalid/null user name
    // Mockito.when(mockUserDetailsRepo.findUserDetailsByUserName(capturedUserName.capture()))
    // .thenReturn(null);
    //
    // // Perform the test
    // final UserDetails result = classUnderTest.loadUserByUsername(USER_NAME);
    //
    // // Check the results
    // assertNotNull(result, NULL);
    // assertEquals(result.getUsername(), capturedUserName.getValue(), NOT_EQUAL);
    // assertEquals(0, result.getAuthorities().size(), NOT_EQUAL);
    // }

    /**
     * Test remove user valid.
     */
    @Test
    void testAddUserValid() {
        // Capture the UserDetails
        ArgumentCaptor<IUserDetails> capturedUserDetails =
            ArgumentCaptor.forClass(IUserDetails.class);

        // Perform the test
        classUnderTest.addUser(userAddCommand);

        // Capture
        verify(mockUserDetailsRepo).saveDaoFromBasicValue(capturedUserDetails.capture());

        // Assert that the objects are as Mockito.whened
        assertEquals(capturedUserDetails.getValue().getUserName(), userAddCommand.getUserName(),
            NOT_EQUAL);
        assertEquals(capturedUserDetails.getValue().getUserRole(),
            userAddCommand.getUserRole().toString(), NOT_EQUAL);
    }

    // /**
    // * Test add user valid.
    // */
    // @Test
    // void testRemoveUserValid() {
    // // Capture the UserName
    // final ArgumentCaptor<String> capturedUserName = ArgumentCaptor.forClass(String.class);
    //
    // // Define a mock version of the called methods
    // Mockito.when(mockUserDetailsRepo.findUserDetailsByUserName(capturedUserName.capture()))
    // .thenReturn(userDetails);
    // mockUserDetailsRepo.deleteDaoFromBasicValue(userDetails);
    // Mockito.atLeastOnce();
    //
    // // Perform the test
    // classUnderTest.removeUser(userRemoveCommand);
    //
    // // Assert that the objects are as Mockito.whened
    // assertEquals(userRemoveCommand.getUserName(), capturedUserName.getValue(), NOT_EQUAL);
    // }

    /**
     * Test remove user is null.
     */
    @Test
    void testRemoveUserIsNull() {
        // Capture the UserName
        final ArgumentCaptor<String> capturedUserName = ArgumentCaptor.forClass(String.class);

        // Define a mock version of the called methods
        Mockito.when(mockUserDetailsRepo.findUserDetailsByUserName(capturedUserName.capture()))
            .thenReturn(null);
        try {
            // Perform the test
            classUnderTest.removeUser(userRemoveCommand);
        } catch (Exception e) {
            assertEquals(e.getClass(), ServiceException.class, NOT_EQUAL);
        } finally {
            // Assert that the objects are as Mockito.whened
            assertEquals(userRemoveCommand.getUserName(), capturedUserName.getValue(), NOT_EQUAL);
        }
    }

    // /**
    // * Test get users valid.
    // */
    // @Test
    // void testGetUsersValid() {
    // // Define a mock version of the called methods
    // Mockito.when(mockUserDetailsRepo.getUserDetails()).thenReturn(userDetailsList);
    //
    // // Perform the test
    // final List<UserDto> results = classUnderTest.getUsers();
    //
    // // Assert that the objects are as Mockito.whened
    // assertNotNull(results, NULL);
    // assertEquals(results.size(), userDetailsList.size(), NOT_EQUAL);
    // }

    /**
     * Test is user with user name success.
     */
    @Test
    void testIsUserWithUserNameTrue() {
        // Capture the UserName
        final ArgumentCaptor<String> capturedUserName = ArgumentCaptor.forClass(String.class);

        // Define a mock version of the called methods
        Mockito.when(mockUserDetailsRepo.isUserDetailsWithUserName(capturedUserName.capture()))
            .thenReturn(true);

        // Perform the test
        final boolean result = classUnderTest.isUserWithUserName(USER_NAME);

        // Assert that the objects are as Mockito.whened
        assertTrue(result, FALSE);
        assertEquals(USER_NAME, capturedUserName.getValue(), NOT_EQUAL);
    }

    /**
     * Test is user with user name false.
     */
    @Test
    void testIsUserWithUserNameFalse() {
        // Capture the UserName
        final ArgumentCaptor<String> capturedUserName = ArgumentCaptor.forClass(String.class);

        // Define a mock version of the called methods
        Mockito.when(mockUserDetailsRepo.isUserDetailsWithUserName(Mockito.isA(String.class)))
            .thenReturn(false);

        // Perform the test
        final boolean result = classUnderTest.isUserWithUserName(USER_NAME);

        // Capture
        verify(mockUserDetailsRepo).isUserDetailsWithUserName(capturedUserName.capture());

        // Assert that the objects are as Mockito.whened
        assertFalse(result, TRUE);
        assertEquals(USER_NAME, capturedUserName.getValue(), NOT_EQUAL);
    }

    /**
     * Test is user with user name null.
     */
    @Test
    void testIsUserWithUserNameNull() {
        // Perform the test
        final boolean result = classUnderTest.isUserWithUserName(null);

        // Assert that the objects are as Mockito.whened
        assertFalse(result, TRUE);
    }

    // /**
    // * Test is session user success.
    // */
    // @Test
    // void testIsSessionUserTrue() {
    // // Define a mock version of the called methods via private method
    // mockGetSessionUserName(true);
    //
    // // Perform the test
    // final boolean result = classUnderTest.isSessionUser(USER_NAME);
    //
    // // Assert that the objects are as Mockito.whened
    // assertTrue(result, FALSE);
    // }
    //
    // /**
    // * Test is session user false.
    // */
    // @Test
    // void testIsSessionUserFalse() {
    // // Define a mock version of the called methods via private method
    // mockGetSessionUserName(false);
    //
    // // Perform the test
    // final boolean result = classUnderTest.isSessionUser(USER_NAME);
    //
    // // Assert that the objects are as Mockito.whened
    // assertFalse(result, TRUE);
    // }
    //
    // /**
    // * Test is session user null.
    // */
    // @Test
    // void testIsSessionUserNull() {
    // // Perform the test
    // final boolean result = classUnderTest.isSessionUser(null);
    //
    // // Assert that the objects are as Mockito.whened
    // assertFalse(result, TRUE);
    // }
    //
    // /**
    // * Test is session authentication null.
    // */
    // @Test
    // void testIsSessionAuthenticationNull() {
    // // Define a mock version of the called methods
    // Mockito.when(SecurityContextHolder.getContext()).thenReturn(mockSecurityContext);
    // Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(null);
    //
    // // Perform the test
    // final boolean result = classUnderTest.isSessionUser(USER_NAME);
    //
    // // Assert that the objects are as Mockito.whened
    // assertFalse(result, TRUE);
    // }
    //
    // /**
    // * Test is session user exists true.
    // */
    // @Test
    // void testIsSessionUserExistsTrue() {
    // // Capture the UserName
    // final ArgumentCaptor<String> capturedUserName = ArgumentCaptor.forClass(String.class);
    //
    // // Define a mock version of the called methods via private method
    // mockGetSessionUserName(true);
    // // Define a mock version of the other called methods
    // Mockito.when(mockUserDetailsRepo.isUserDetailsWithUserName(capturedUserName.capture()))
    // .thenReturn(true);
    //
    // // Perform the test
    // final boolean result = classUnderTest.isSessionUserExist();
    //
    // // Assert that the objects are as Mockito.whened
    // assertTrue(result, FALSE);
    // assertEquals(USER_NAME, capturedUserName.getValue(), NOT_EQUAL);
    // }

    // /**
    // * Gets the test user details.
    // *
    // * @param appendToUserName the append to user name
    // * @return the test user details
    // */
    // protected IUserDetails getTestUserDetails(final String appendToUserName) {
    // final IUserDetails user =
    // new uk.gov.hmcts.pdm.publicdisplay.manager.domain.UserDetails();
    // user.setUserName(USER_NAME + "_" + appendToUserName);
    // user.setUserRole(UserRole.ROLE_USER_VALUE);
    // return user;
    // }

    /**
     * Gets the test user add command.
     *
     * @return the test user add command
     */
    protected UserAddCommand getTestUserAddCommand() {
        final UserAddCommand addCommand = new UserAddCommand();
        addCommand.setUserName("TEST_USER_NAME");
        addCommand.setUserRole(UserRole.ROLE_USER);
        return addCommand;
    }

    /**
     * Gets the test user remove command.
     *
     * @return the test user remove command
     */
    protected UserRemoveCommand getTestUserRemoveCommand() {
        final UserRemoveCommand removeCommand = new UserRemoveCommand();
        removeCommand.setUserName("TEST_USER_NAME");

        return removeCommand;
    }

    // /**
    // * Gets the test user detail list.
    // *
    // * @return the test user detail list
    // */
    // protected List<IUserDetails> getTestUserDetailList() {
    // final List<IUserDetails> userDetailsList = new ArrayList<>();
    // final IUserDetails user1 = getTestUserDetails("ONE");
    // final IUserDetails user2 = getTestUserDetails("TWO");
    // final IUserDetails user3 = getTestUserDetails("THREE");
    //
    // userDetailsList.add(user1);
    // userDetailsList.add(user2);
    // userDetailsList.add(user3);
    //
    // return userDetailsList;
    // }

    /**
     * Mock get session user name.
     *
     * @param isMatch the is match. Used to determine if user names match.
     */
    protected void mockGetSessionUserName(final boolean isMatch) {
        // set the user dependent on if a match is required. I.e. is same user of not when comparing
        // in
        // assert
        String user;
        if (isMatch) {
            user = USER_NAME;
        } else {
            user = USER_NAME + "_NO_MATCH";
        }

        // Define a mock version of the called methods
        Mockito.when(SecurityContextHolder.getContext()).thenReturn(mockSecurityContext);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        Mockito.when(mockAuthentication.getName()).thenReturn(user);
    }

}
