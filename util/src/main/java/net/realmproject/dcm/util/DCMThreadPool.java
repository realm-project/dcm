/**
 * Copyright 2014 Realm Project
 * 
 * This file is part of the Device Control Module (DCM).
 * 
 * DCM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DCM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DCM. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.realmproject.dcm.util;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Thread pool for handling event transmission asynchronously
 * 
 * @author NAS
 *
 */
public class DCMThreadPool {

    private static Log log = LogFactory.getLog(DCMThreadPool.class);
    public static boolean daemonThreads = true;

    private static ThreadFactory threadFactory = runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(daemonThreads);
        return t;
    };

    private static ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(10, threadFactory);
    private static ExecutorService pool = Executors.newCachedThreadPool(threadFactory);

    /**
     * Returns the scheduled thread pool execution service. This pool is of a
     * fixed, limited size, and so long running tasks should not be put in this
     * pool. Longer running tasks should be run through the normal task pool.
     * see {@link DCMThreadPool#getPool()}
     * 
     * Threads running in this pool will be interrupted on application
     * termination when {@link DCMThreadPool#stop()} is called. This is useful
     * when running in an environment such as Tomcat.
     * 
     * @return The scheduled thread pool
     */
    public static ScheduledExecutorService getScheduledPool() {
        return scheduledPool;
    }

    /**
     * Returns the thread pool. This pool is of a flexible size. Long running
     * tasks should be submitted to this pool, rather than the scheduled pool,
     * which is of a fixed size.
     * 
     * Threads running in this pool will be interrupted on application
     * termination when {@link DCMThreadPool#stop()} is called. This is useful
     * when running in an environment such as Tomcat.
     * 
     * @return The thread pool
     */
    public static ExecutorService getPool() {
        return pool;
    }

    public static void stop() {
        shutdownPool(scheduledPool);
        shutdownPool(pool);
    }

    private static void shutdownPool(ExecutorService pool) {
        pool.shutdown();
        boolean success;

        try {
            success = pool.awaitTermination(1, TimeUnit.SECONDS);
            if (!success) {
                log.warn("Orderly shutdown of threadpool failed");
            }
        }
        catch (InterruptedException e) {
            log.warn("Orderly shutdown of threadpool failed", e);
            Thread.currentThread().interrupt();
        }

        pool.shutdownNow();

        try {
            success = pool.awaitTermination(10, TimeUnit.SECONDS);
            if (!success) {
                log.warn("Forced shutdown of threadpool failed");
            }
        }
        catch (InterruptedException e) {
            log.warn("Forced shutdown of threadpool failed", e);
            Thread.currentThread().interrupt();
        }
    }

}
