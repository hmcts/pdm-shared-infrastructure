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
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrlModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite;

import java.util.HashSet;
import java.util.Set;


/**
 * Url.
 * @author uphillj
 *
 */
public class UrlModel extends AbstractDomainObject implements IUrlModel {

    /** The url. */
    private String url;

    /** The description. */
    private String description;

    /** The xhibit court site. */
    private IXhibitCourtSite xhibitCourtSite;

    /** The cdus. */
    private Set<ICduModel> cdus = new HashSet<>();

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrl#getUrl()
     */
    @Override
    public String getUrl() {
        return url;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrl#setUrl(java.lang.String)
     */
    @Override
    public void setUrl(final String url) {
        this.url = url;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrl#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrl#setDescription(java.lang.
     * String)
     */
    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrl#getXhibitCourtSite()
     */
    @Override
    public IXhibitCourtSite getXhibitCourtSite() {
        return xhibitCourtSite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrl#setXhibitCourtSite(uk.gov.
     * hmcts.pdm. publicdisplay.manager.domain.api.IXhibitCourtSite)
     */
    @Override
    public void setXhibitCourtSite(final IXhibitCourtSite xhibitCourtSite) {
        this.xhibitCourtSite = xhibitCourtSite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrl#getCdus()
     */
    @Override
    public Set<ICduModel> getCdus() {
        return cdus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IUrl#setCdus(java.util.Set)
     */
    @Override
    public void setCdus(final Set<ICduModel> cdus) {
        this.cdus = cdus;
    }

}
