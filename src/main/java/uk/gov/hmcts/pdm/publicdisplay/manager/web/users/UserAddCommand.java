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

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import uk.gov.hmcts.pdm.publicdisplay.manager.security.UserRole;


/**
 * Command object used to hold the values passed to the add User function.
 * 
 * @author harrism
 *
 */
public class UserAddCommand {
    /**
     * Maximum length for user name field.
     */
    private static final int USERNAME_MAX_LENGTH = 30;

    /** The user name. */
    @Pattern(regexp = "[A-Za-z0-9_]*", message = "{userCommand.userName.invalid}")
    @NotBlank(message = "{userCommand.userName.notBlank}")
    @Length(max = USERNAME_MAX_LENGTH, message = "{userCommand.userName.tooLong}")
    private String userName;

    /** The user role. */
    @NotNull(message = "{userCommand.userRole.notNull}")
    private UserRole userRole;

    /**
     * Reset.
     */
    public void reset() {
        setUserName(null);
        setUserRole(null);
    }

    /**
     * getUserName.
     * 
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * setUserName.
     * 
     * @param userName the user name to set
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * getUserRole.
     * 
     * @return the user role
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * setUserRole.
     * 
     * @param userRole the user role to set
     */
    public void setUserRole(final UserRole userRole) {
        this.userRole = userRole;
    }
}
