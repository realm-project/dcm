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
