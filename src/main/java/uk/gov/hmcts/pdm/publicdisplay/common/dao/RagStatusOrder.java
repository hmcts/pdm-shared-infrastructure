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

package uk.gov.hmcts.pdm.publicdisplay.common.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

/**
 * Sub-class of Hibernate Order class for sorting by a RAG status property. Red is regarded as the
 * highest status, followed by amber and then green.
 *
 * @author uphillj
 */
public class RagStatusOrder extends Order {

    /** The serial version UID. */
    private static final long serialVersionUID = 8595554536790985518L;

    /** The RAG status ascending order SQL. */
    private static final String RAG_STATUS_ORDER_ASC =
        "case ? when 'R' then 3 when 'A' then 2 else 1 end";

    /** The RAG status descending order SQL. */
    private static final String RAG_STATUS_ORDER_DESC =
        "case ? when 'R' then 1 when 'A' then 2 else 3 end";

    /**
     * Instantiates a new rag status order.
     *
     * @param propertyName the property name
     * @param ascending ascending or descending
     */
    public RagStatusOrder(final String propertyName, final boolean ascending) {
        super(propertyName, ascending);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.criterion.Order#toSqlString(org.hibernate.Criteria,
     * org.hibernate.criterion.CriteriaQuery)
     */
    @Override
    public String toSqlString(final Criteria criteria, final CriteriaQuery criteriaQuery) {
        // Assumption is there will only be one rag status column for the property name
        final String[] columns =
            criteriaQuery.getColumnsUsingProjection(criteria, getPropertyName());
        final String column = columns[0];

        // Create the order by fragment from either the ascending or descending sql
        String fragment = RAG_STATUS_ORDER_ASC;
        if (!isAscending()) {
            fragment = RAG_STATUS_ORDER_DESC;
        }
        fragment = fragment.replace("?", column);
        return fragment;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.criterion.Order#toString()
     */
    @Override
    public String toString() {
        if (isAscending()) {
            return RAG_STATUS_ORDER_ASC.replace("?", getPropertyName());
        } else {
            return RAG_STATUS_ORDER_DESC.replace("?", getPropertyName());
        }
    }

    /**
     * Create new ascending RagStatusOrder instance.
     *
     * @param propertyName the property name
     * @return RagStatusOrder
     */
    public static Order asc(final String propertyName) {
        return new RagStatusOrder(propertyName, true);
    }

    /**
     * Create new ascending RagStatusOrder instance.
     *
     * @param propertyName the property name
     * @return RagStatusOrder
     */
    public static Order desc(final String propertyName) {
        return new RagStatusOrder(propertyName, false);
    }
}
