package net.realmproject.dcm.accessor;


import net.realmproject.dcm.device.Device;


/**
 * Common interface for any class/interface acting as a go-between from a
 * {@link Device} to
 * 
 * @author NAS
 *
 */
public interface DeviceAccessor {

    String getDeviceId();
}
