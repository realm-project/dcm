package net.realmproject.dcm.event.receiver;


import net.realmproject.dcm.event.DeviceEvent;


public interface DeviceEventReceiver {

    boolean accept(DeviceEvent event);

}
