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

package uk.gov.hmcts.pdm.publicdisplay.common.domain;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.joda.time.DateTime;
import uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject;

/**
 * Abstract class for all domain objects.
 * 
 * @author uphillj.
 * 
 */
public abstract class AbstractDomainObject implements IDomainObject {

    /** The id. */
    private Long id;

    /** The version. */
    private Integer version;

    /** The created by. */
    private String createdBy;

    /** The created date. */
    private DateTime createdDate;

    /** The updated by. */
    private String updatedBy;

    /** The updated date. */
    private DateTime updatedDate;

    /**
     * Constructor for {@link AbstractDomainObject}.
     */
    protected AbstractDomainObject() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#setId(java.lang.
     * Long)
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#getVersion()
     */
    @Override
    public Integer getVersion() {
        return version;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#setVersion(java.
     * lang. Integer)
     */
    @Override
    public void setVersion(final Integer version) {
        this.version = version;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#getCreatedBy()
     */
    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#setCreatedBy(java.
     * lang .String)
     */
    @Override
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#getCreatedDate()
     */
    @Override
    public DateTime getCreatedDate() {
        return createdDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#setCreatedDate(org.
     * joda.time. DateTime)
     */
    @Override
    public void setCreatedDate(final DateTime createdDate) {
        this.createdDate = createdDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#getUpdatedBy()
     */
    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#setUpdatedBy(java.
     * lang .String)
     */
    @Override
    public void setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#getUpdatedDate()
     */
    @Override
    public DateTime getUpdatedDate() {
        return updatedDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.common.domain.api.IDomainObject#setUpdatedDate(org.
     * joda.time. DateTime)
     */
    @Override
    public void setUpdatedDate(final DateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Get the Object class name and hash id. This is used to wrap domain objects in order to
     * prevent recursive toString () calls and should be applied in the toString () method of all
     * domain objects which are associated with other domain objects.
     * 
     * @param object whose details are to be returned.
     * @return details of object concerned.
     */
    protected String getHashId(final Object object) {
        if (PersistentCollection.class.isInstance(object)) {
            return "PersistentCollection";
        } else if (HibernateProxy.class.isInstance(object)) {
            return "HibernateProxy";
        } else {
            return object == null ? null
                : object.getClass().getSimpleName() + "@" + Integer.toHexString(object.hashCode());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractEntity[" + "id=");
        sb.append(this.getId()).append(", version=").append(this.getVersion()).append(']');
        return sb.toString();
    }

}
