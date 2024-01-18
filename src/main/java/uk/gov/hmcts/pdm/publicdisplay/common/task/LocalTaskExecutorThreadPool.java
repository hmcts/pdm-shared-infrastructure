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

import org.quartz.SchedulerConfigException;
import org.quartz.spi.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Quartz ThreadPool adapter that delegates to the Spring TaskExecutor configured on the
 * SchedulerFactoryBean. This ensures that the threads Quartz creates for jobs use the task executor
 * configured in Spring. This differs from the one supplied by Spring as it allows the number of
 * threads to be restricted in the application.
 * 
 * @author uphillj
 *
 */
public class LocalTaskExecutorThreadPool implements ThreadPool {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalTaskExecutorThreadPool.class);

    /** Thread timeout. */
    private static final long TIMEOUT_MILLIS = 500;

    /** Spring task executor retrieved from scheduler factory bean. */
    private Executor taskExecutor;

    /** Number of allowed threads. */
    private int threadCount = -1;

    /** Lock used to synchronise access to the permit available condition. */
    private final Lock lock = new ReentrantLock();

    /** Condition used to notify waiting jobs that permit is available. */
    private final Condition runPermitAvailable = lock.newCondition();

    /** Semaphore used to restrict the number of running jobs. */
    private Semaphore runPermits;

    /** Flag to indicate scheduler is shutdown. */
    private boolean schedulerShutdown;

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.spi.ThreadPool#setInstanceId(java.lang.String)
     */
    @Override
    public void setInstanceId(final String schedInstId) {
        // No implementation required
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.spi.ThreadPool#setInstanceName(java.lang.String)
     */
    @Override
    public void setInstanceName(final String schedName) {
        // No implementation required
    }

    /**
     * getThreadCount.
     * 
     * @return the threadCount
     */
    public int getThreadCount() {
        return threadCount;
    }

    /**
     * setThreadCount.
     * 
     * @param threadCount the threadCount to set
     */
    public void setThreadCount(final int threadCount) {
        this.threadCount = threadCount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.spi.ThreadPool#getPoolSize()
     */
    @Override
    public int getPoolSize() {
        return getThreadCount();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.spi.ThreadPool#initialize()
     */
    @Override
    public void initialize() throws SchedulerConfigException {
        this.taskExecutor = SchedulerFactoryBean.getConfigTimeTaskExecutor();
        if (this.taskExecutor == null) {
            throw new SchedulerConfigException("No local TaskExecutor found for configuration - "
                + "'taskExecutor' property must be set on SchedulerFactoryBean");
        }

        if (threadCount <= 0) {
            throw new SchedulerConfigException("Thread count must be > 0");
        }
        runPermits = new Semaphore(threadCount);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.spi.ThreadPool#runInThread(java.lang.Runnable)
     */
    @Override
    public boolean runInThread(final Runnable runnable) {
        LOGGER.info("runInThread method starts");


        // Cannot schedule null job
        if (runnable == null) {
            return false;
        }

        boolean scheduled = false;
        lock.lock();

        try {
            // Schedule job if the scheduler is not shutting down
            if (!schedulerShutdown) {
                scheduled = scheduleJob(runnable);
            }
        } finally {
            lock.unlock();
        }

        LOGGER.info("runInThread method ends");
        return scheduled;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.spi.ThreadPool#blockForAvailableThreads()
     */
    @Override
    public int blockForAvailableThreads() {
        LOGGER.info("blockForAvailableThreads method starts");
        int threads;

        lock.lock();
        try {
            // Wait for a free run permit or the scheduler to be shutdown
            while (runPermits.availablePermits() < 1 && !schedulerShutdown) {
                try {
                    boolean await = runPermitAvailable.await(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                    LOGGER.debug("await = {}", await ? "TRUE" : "FALSE");
                } catch (final InterruptedException ex) { // NOSONAR
                    // Okay to ignore exception as outer loop ensures this
                    // thread will wait until there is a permit available
                }
            }
            threads = runPermits.availablePermits();
        } finally {
            lock.unlock();
        }

        LOGGER.info("blockForAvailableThreads method ends");
        return threads;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.spi.ThreadPool#shutdown(boolean)
     */
    @Override
    public void shutdown(final boolean waitForJobsToComplete) {
        LOGGER.info("shutdown method starts");

        lock.lock();
        try {
            // Set flag to indicate scheduler is shutting down
            schedulerShutdown = true;

            // Give all waiting threads a chance to shutdown
            runPermitAvailable.signalAll();

            // If need to wait for running jobs to complete,
            // wait for all the run permits to be available
            if (waitForJobsToComplete) {
                boolean interrupted = false;
                try {
                    while (runPermits.availablePermits() < threadCount) {
                        try {
                            boolean await =
                                runPermitAvailable.await(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                            LOGGER.debug("await = {}", await ? "TRUE" : "FALSE");
                        } catch (final InterruptedException ex) { // NOSONAR
                            interrupted = true;
                        }
                    }
                } finally {
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } finally {
            lock.unlock();
        }

        LOGGER.info("shutdown method ends");
    }

    /**
     * Schedule job on task executor.
     *
     * @param runnable the runnable
     * @return true, if successful
     */
    private boolean scheduleJob(final Runnable runnable) {


        // Acquire a permit to continue which
        // blocks until one is available
        if (!acquireRunPermit()) {
            return false;
        }

        boolean scheduled;
        // Wrap task in a Runnable instance that
        // releases the permit when job completes
        try {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        LOGGER.info("job starts");
                        runnable.run();
                    } finally {
                        LOGGER.info("job ends");
                        releaseRunPermit();
                    }
                }
            });
            scheduled = true;

            // Must release permit if job does not run
        } catch (final RejectedExecutionException ex) {
            releaseRunPermit();
            scheduled = false;
        }

        // Return whether job has been scheduled
        return scheduled;
    }

    /**
     * Acquire permit to run job.
     *
     * @return true, if successful
     */
    private boolean acquireRunPermit() {
        // Acquire a permit to continue which
        // blocks until one is available
        boolean acquired;
        try {
            runPermits.acquire();
            LOGGER.info("run permit acquired");
            acquired = true;
        } catch (final InterruptedException ex) { // NOSONAR
            acquired = false;
        }
        return acquired;
    }

    /**
     * Release a run permit and notify any waiting thread.
     */
    private void releaseRunPermit() {
        // Release the run permit
        runPermits.release();
        LOGGER.info("run permit released");

        // Notify any waiting thread
        lock.lock();
        try {
            runPermitAvailable.signal();
        } finally {
            lock.unlock();
        }
    }
}
