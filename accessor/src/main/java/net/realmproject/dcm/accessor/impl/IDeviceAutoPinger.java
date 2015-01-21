package net.realmproject.dcm.accessor.impl;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.util.DCMThreadPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class IDeviceAutoPinger extends IDevicePinger {

    private Log log = LogFactory.getLog(getClass());

    public IDeviceAutoPinger(String id, DeviceEventBus bus) {
        super(id, bus);

        DCMThreadPool.getPool().scheduleAtFixedRate(this::logPing, 10, 10, TimeUnit.SECONDS);

    }

    private void logPing() {

        try {
            Future<Long> future = pingAndWait(10000);
            Long response = future.get();
            if (response == null) {
                // There wasn't a response to ping
                // TODO: Handle this better
                return;
            }

            log.debug("Ping time for device " + getDeviceId() + " is " + response + " ms");

        }
        catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
