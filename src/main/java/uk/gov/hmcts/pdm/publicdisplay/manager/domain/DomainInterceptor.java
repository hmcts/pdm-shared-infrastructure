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

package uk.gov.hmcts.pdm.publicdisplay.manager.domain;


import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject;

import java.io.Serializable;

/**
 * Hibernate Interceptor to set the createdBy and updatedBy fields to the current username from the
 * Spring Security context.
 * 
 * @author uphillj
 *
 */

public class DomainInterceptor extends EmptyInterceptor {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -141894230121425445L;
    private static final String UPDATED_BY = "updatedBy";
    private static final String CREATED_BY = "createdBy";

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.EmptyInterceptor#onFlushDirty(java.lang.Object, java.io.Serializable,
     * java.lang.Object[], java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onFlushDirty(final Object entity, final Serializable id,
        final Object[] currentState, final Object[] previousState, final String[] propertyNames,
        final Type[] types) {
        // If the entity being modified supports the updatedBy field,
        // update that field in the current state array with the
        // username retrieved from the Spring Security context
        if (entity instanceof IDomainObject) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (UPDATED_BY.equals(propertyNames[i])) {
                    currentState[i] = getUsername();
                }
            }
        }

        // This method should return true if the current state has changed,
        // however returning true causes an optimistic lock exception to be
        // incorrectly thrown. So, instead this method always return false
        // which still allows the updatedBy change to be persisted.
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object, java.io.Serializable,
     * java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onSave(final Object entity, final Serializable id, final Object[] state,
        final String[] propertyNames, final Type[] types) {
        boolean modified = false;

        // If the entity being created supports the createdBy and updatedBy
        // fields, update those fields in the state array with the username
        // retrieved from the Spring Security context
        if (entity instanceof IDomainObject) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (CREATED_BY.equals(propertyNames[i]) || UPDATED_BY.equals(propertyNames[i])) {
                    state[i] = getUsername();
                    modified = true;
                }
            }
        }

        return modified;
    }

    /**
     * Get the username of the current logged on user.
     * 
     * @return username
     */
    private String getUsername() {
        // Default username is XHIBIT
        String username = "XHIBIT";

        // Get the principal from the Spring security authentication
        // object which has the username of the current logged on user
        final Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            username = ((org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal()).getUsername();
        }
        
        return username;
    }
}
