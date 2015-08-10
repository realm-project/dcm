package net.realmproject.dcm.event.receiver;


import net.realmproject.dcm.event.DeviceEvent;


/**
 * Base interface for DCM event graph nodes which receive {@link DeviceEvent}s
 * 
 * @author NAS
 *
 */
public interface DeviceEventReceiver {

    boolean accept(DeviceEvent event);

}
