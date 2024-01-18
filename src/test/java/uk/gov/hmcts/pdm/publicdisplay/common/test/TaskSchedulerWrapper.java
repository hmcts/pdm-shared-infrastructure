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

package uk.gov.hmcts.pdm.publicdisplay.common.test;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

/**
 * Test class wrapper for the ThreadPoolTaskScheduler.
 * 
 * @author harrism
 *
 */
public class TaskSchedulerWrapper extends ThreadPoolTaskScheduler {

    /**
     * Serial Id.
     */
    private static final long serialVersionUID = 1L;

    /** The futures. */
    private List<ScheduledFuture<?>> futures;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.scheduling.concurrent.ExecutorConfigurationSupport#initialize()
     */
    @Override
    public void initialize() {
        super.initialize();
        setFutures(new ArrayList<>());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler#schedule(java.lang.
     * Runnable, java.util.Date)
     */
    @Override
    public ScheduledFuture<?> schedule(final Runnable paramRunnable, final Date paramDate) {
        final ScheduledFuture<?> future = super.schedule(paramRunnable, paramDate);
        getFutures().add(future);
        return future;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler#schedule(java.lang.
     * Runnable, org.springframework.scheduling.Trigger)
     */
    @Override
    public ScheduledFuture<?> schedule(final Runnable task, final Trigger trigger) {
        final ScheduledFuture<?> future = super.schedule(task, trigger);
        getFutures().add(future);
        return future;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler#scheduleAtFixedRate(java.
     * lang .Runnable, java.util.Date, long)
     */
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable task, final Date startTime,
        final long period) {
        final ScheduledFuture<?> future = super.scheduleAtFixedRate(task, startTime, period);
        getFutures().add(future);
        return future;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler#scheduleAtFixedRate(java.
     * lang .Runnable, long)
     */
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable task, final long period) {
        final ScheduledFuture<?> future = super.scheduleAtFixedRate(task, period);
        getFutures().add(future);
        return future;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler#scheduleWithFixedDelay(
     * java. lang.Runnable, java.util.Date, long)
     */
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable task, final Date startTime,
        final long delay) {
        final ScheduledFuture<?> future = super.scheduleWithFixedDelay(task, startTime, delay);
        getFutures().add(future);
        return future;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler#scheduleWithFixedDelay(
     * java. lang.Runnable, long)
     */
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable task, final long delay) {
        final ScheduledFuture<?> future = super.scheduleWithFixedDelay(task, delay);
        getFutures().add(future);
        return future;
    }

    /**
     * Gets futures.
     * 
     * @return the futures
     */
    public List<ScheduledFuture<?>> getFutures() {
        return futures;
    }

    /**
     * Sets futures.
     * 
     * @param futures the futures to set
     */
    public void setFutures(final List<ScheduledFuture<?>> futures) {
        this.futures = futures;
    }

    /**
     * Wait for futures to complete or be cancelled.
     * 
     * @throws ExecutionException executionException
     * @throws InterruptedException interruptedException
     */
    public void waitForFutures() throws InterruptedException, ExecutionException {
        for (ScheduledFuture<?> future : futures) {
            future.get();
        }
    }
}
