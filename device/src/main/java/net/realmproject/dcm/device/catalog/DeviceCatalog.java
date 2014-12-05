/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 * 
 * Description: DeviceFactory abstract class.
 */
package net.realmproject.dcm.device.catalog;


import java.util.Map;

import net.realmproject.dcm.device.Device;


/**
 * Implementors should probably be Singletons.
 * 
 * @author maxweld
 *
 */
public abstract class DeviceCatalog {

    static DeviceCatalogImpl deviceFactoryImpl = null;

    public static DeviceCatalogImpl getDeviceFactoryImpl() {
        return deviceFactoryImpl;
    }

    public static void setDeviceFactoryImpl(DeviceCatalogImpl deviceFactoryImpl) {
        DeviceCatalog.deviceFactoryImpl = deviceFactoryImpl;
    }

    public static Device getDevice(String deviceId) {
        return getDeviceFactoryImpl().getDevice(deviceId);
    }

    public static Map<String, Device> getDevicesById() {
        return getDeviceFactoryImpl().getDevicesById();
    }

}
