/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 *
 * Description: DeviceFactoryImpl interface.
 * 
 */
package net.realmproject.dcm.device.catalog;


import java.util.Map;

import net.realmproject.dcm.device.Device;


/**
 * @author maxweld
 *
 */
public interface DeviceCatalogImpl {

    public Device getDevice(String deviceId);

    public Map<String, Device> getDevicesById();

}
