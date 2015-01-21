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


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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

    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

    public static ScheduledExecutorService getPool() {
        return pool;
    }

    public static void stop() {

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
        }

    }

}
