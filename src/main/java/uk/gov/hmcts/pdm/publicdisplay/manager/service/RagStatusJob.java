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

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.IRagStatusService;



/**
 * Quartz job which retrieves the rag status from a court site.
 * 
 * @author uphillj
 *
 */
@DisallowConcurrentExecution
public class RagStatusJob implements Job {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RagStatusJob.class);

    /** The rag status service. */
    @Autowired
    private IRagStatusService ragStatusService;

    /**
     * getRagStatusService.
     * 
     * @return the ragStatusService
     */
    public IRagStatusService getRagStatusService() {
        return ragStatusService;
    }

    /**
     * setRagStatusService.
     * 
     * @param ragStatusService the ragStatusService to set
     */
    public void setRagStatusService(final IRagStatusService ragStatusService) {
        this.ragStatusService = ragStatusService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("execute method starts");

        try {
            final Long xhibitCourtSiteId = context.getJobDetail().getJobDataMap().getLong("id");
            ragStatusService.refreshRagStatus(xhibitCourtSiteId);

            // CHECKSTYLE:OFF Quartz requires that jobs only throw a JobExecutionException
        } catch (final Exception ex) {
            // CHECKSTYLE:ON

            LOGGER.error("failed to refresh rag status", ex);
            final JobExecutionException jex = new JobExecutionException(ex);
            jex.setUnscheduleAllTriggers(true);
            throw jex;
        }

        LOGGER.info("execute method ends");
    }

}
