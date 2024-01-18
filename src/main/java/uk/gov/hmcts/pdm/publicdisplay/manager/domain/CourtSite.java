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
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ISchedule;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.IXhibitCourtSite;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * The Class CourtSite.
 *
 * @author uphillj
 */
public class CourtSite extends AbstractDomainObject implements ICourtSite {

    /** The title. */
    private String title;

    /** The notification. */
    private String notification;

    /** The page url. */
    private String pageUrl;

    /** The RAG status (R, A or G). */
    private String ragStatus;

    /** The RAG status date. */
    private LocalDateTime ragStatusDate;

    /** The xhibit court site. */
    private IXhibitCourtSite xhibitCourtSite;

    /** The schedule. */
    private ISchedule schedule;

    /** The local proxy. */
    private ILocalProxy localProxy;

    /** The cdus. */
    private Set<ICduModel> cdus = new HashSet<>();

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#getTitle()
     */
    @Override
    public String getTitle() {
        return title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#setTitle(java.lang.
     * String)
     */
    @Override
    public void setTitle(final String title) {
        this.title = title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#getNotification()
     */
    @Override
    public String getNotification() {
        return notification;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#setNotification(java.
     * lang.String)
     */
    @Override
    public void setNotification(final String notification) {
        this.notification = notification;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#getPageUrl()
     */
    @Override
    public String getPageUrl() {
        return pageUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#setPageUrl(java.lang.
     * String)
     */
    @Override
    public void setPageUrl(final String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#getRagStatus()
     */
    @Override
    public String getRagStatus() {
        return ragStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#setRagStatus(java.
     * lang. Character)
     */
    @Override
    public void setRagStatus(final String ragStatus) {
        this.ragStatus = ragStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#getRagStatusDate()
     */
    @Override
    public LocalDateTime getRagStatusDate() {
        return ragStatusDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#setRagStatusDate(
     * org.joda.time.DateTime)
     */
    @Override
    public void setRagStatusDate(final LocalDateTime ragStatusDate) {
        this.ragStatusDate = ragStatusDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#getXhibitCourtSite()
     */
    @Override
    public IXhibitCourtSite getXhibitCourtSite() {
        return xhibitCourtSite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#setXhibitCourtSite(uk.
     * gov.hmcts.pdm.publicdisplay.manager.domain.IXhibitCourtSite)
     */
    @Override
    public void setXhibitCourtSite(final IXhibitCourtSite xhibitCourtSite) {
        this.xhibitCourtSite = xhibitCourtSite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#getSchedule()
     */
    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#setSchedule(uk.gov.
     * hmcts.pdm. publicdisplay.manager.domain.api.ISchedule)
     */
    @Override
    public void setSchedule(final ISchedule schedule) {
        this.schedule = schedule;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#getLocalProxy()
     */
    @Override
    public ILocalProxy getLocalProxy() {
        return localProxy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#setLocalProxy(uk.gov.
     * hmcts.pdm. publicdisplay.manager.domain.api.ILocalProxy)
     */
    @Override
    public void setLocalProxy(final ILocalProxy localProxy) {
        this.localProxy = localProxy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#getCdus()
     */
    @Override
    public Set<ICduModel> getCdus() {
        return cdus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite#setCdus(java.util.Set)
     */
    @Override
    public void setCdus(final Set<ICduModel> cdus) {
        this.cdus = cdus;
    }

}
