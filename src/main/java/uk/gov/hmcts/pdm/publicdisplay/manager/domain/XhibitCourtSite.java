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

import uk.gov.hmcts.pdm.publicdisplay.common.domain.AbstractDomainObject;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrlModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSiteWelsh;

import java.util.HashSet;
import java.util.Set;


/**
 * The Class XhibitCourtSite.
 *
 * @author uphillj
 */
public class XhibitCourtSite extends AbstractDomainObject implements IXhibitCourtSite {

    /** The court site name. */
    private String courtSiteName;

    /** The display name. */
    private String displayName;

    /** The short name. */
    private String shortName;

    /** The xhibit court site welsh. */
    private IXhibitCourtSiteWelsh xhibitCourtSiteWelsh;

    /** The urls. */
    private Set<IUrlModel> urls = new HashSet<>();

    /** The court site. */
    private ICourtSite courtSite;

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#getCourtSiteName
     * ()
     */
    @Override
    public String getCourtSiteName() {
        return courtSiteName;
    }

    // CHECKSTYLE:OFF Generated javadoc line too long
    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#
     * setCourtSiteName( java.lang.String)
     */
    // CHECKSTYLE:ON
    @Override
    public void setCourtSiteName(final String courtSiteName) {
        this.courtSiteName = courtSiteName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#getDisplayName()
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#setDisplayName(
     * java.lang.String)
     */
    @Override
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#getShortName()
     */
    @Override
    public String getShortName() {
        return shortName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#setShortName(
     * java. lang.String)
     */
    @Override
    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#
     * getXhibitCourtSiteWelsh()
     */
    @Override
    public IXhibitCourtSiteWelsh getXhibitCourtSiteWelsh() {
        return xhibitCourtSiteWelsh;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#
     * setXhibitCourtSiteWelsh(uk.gov.
     * hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSiteWelsh)
     */
    @Override
    public void setXhibitCourtSiteWelsh(final IXhibitCourtSiteWelsh xhibitCourtSiteWelsh) {
        this.xhibitCourtSiteWelsh = xhibitCourtSiteWelsh;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#getUrls()
     */
    @Override
    public Set<IUrlModel> getUrls() {
        return urls;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#setUrls(java.
     * util. Set)
     */
    @Override
    public void setUrls(final Set<IUrlModel> urls) {
        this.urls = urls;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#getCourtSite()
     */
    @Override
    public ICourtSite getCourtSite() {
        return courtSite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite#setCourtSite(uk.
     * gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite)
     */
    @Override
    public void setCourtSite(final ICourtSite courtSite) {
        this.courtSite = courtSite;
    }

}
