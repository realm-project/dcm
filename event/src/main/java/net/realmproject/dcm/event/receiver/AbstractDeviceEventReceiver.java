package net.realmproject.dcm.event.receiver;


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.filter.DeviceEventFilterer;
import net.realmproject.dcm.event.filter.IDeviceEventFilterer;


public abstract class AbstractDeviceEventReceiver extends IDeviceEventFilterer
        implements DeviceEventReceiver, DeviceEventFilterer {

    @Override
    public boolean accept(DeviceEvent event) {
        if (filter(event)) {
            return doReceive(event);
        } else {
            return false;
        }
    }

    public abstract boolean doReceive(DeviceEvent event);

}
