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

package uk.gov.hmcts.pdm.publicdisplay.common.task;

import org.quartz.spi.ThreadExecutor;
import org.springframework.scheduling.SchedulingAwareRunnable;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.concurrent.Executor;
/**
 * Quartz ThreadExecutor adapter that delegates to the Spring TaskExecutor configured on the
 * SchedulerFactoryBean. This ensures that the internal threads which Quartz creates for managing
 * the jobs also use the task executor configured in Spring for the jobs themselves.
 * 
 * @author uphillj
 *
 */

@SuppressWarnings("PMD.DoNotUseThreads")
public class LocalTaskExecutorThreadExecutor implements ThreadExecutor {

    /** Spring task executor retrieved from scheduler factory bean. */
    private Executor taskExecutor;

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.spi.ThreadExecutor#initialize()
     */
    @Override
    public void initialize() {
        taskExecutor = SchedulerFactoryBean.getConfigTimeTaskExecutor();
        if (taskExecutor == null) {
            throw new IllegalStateException("No local TaskExecutor found for configuration - "
                + "'taskExecutor' property must be set on SchedulerFactoryBean");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.spi.ThreadExecutor#execute(java.lang.Thread)
     */
    @Override
    public void execute(final Thread thread) {
        taskExecutor.execute(new LongLivedRunnable(thread));
    }

    /**
     * Runnable wrapper to tell the Spring task executor it is a long lived task.
     * 
     * @author uphillj
     *
     */
    private static class LongLivedRunnable implements SchedulingAwareRunnable {

        /** Runnable being wrapped. */
        private final Runnable runnable;

        /**
         * LongLivedRunnable.
         * 
         * @param runnable runnable
         */
        public LongLivedRunnable(final Runnable runnable) {
            this.runnable = runnable;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            this.runnable.run();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.springframework.scheduling.SchedulingAwareRunnable#isLongLived()
         */
        @Override
        public boolean isLongLived() {
            return true;
        }
    }
}
