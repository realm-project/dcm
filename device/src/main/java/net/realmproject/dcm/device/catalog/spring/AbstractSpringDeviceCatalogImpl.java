/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 *
 * Description: AbstractSpringDeviceFactoryImpl class.
 */
package net.realmproject.dcm.device.catalog.spring;


import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.device.Device;
import net.realmproject.dcm.device.catalog.impl.AbstractDeviceCatalogImpl;

import org.springframework.context.ApplicationContext;


/**
 * @author maxweld
 *
 */
public abstract class AbstractSpringDeviceCatalogImpl extends AbstractDeviceCatalogImpl {

    protected ApplicationContext applicationContext = null;
    protected Map<String, Device> deviceMap = null;

    @Override
    public Device getDevice(String deviceId) {
        return getDevicesById().get(deviceId);
    }

    @Override
    public Map<String, Device> getDevicesById() {
        if (deviceMap == null) {
            Map<String, Device> deviceBeanMap = applicationContext.getBeansOfType(Device.class);
            deviceMap = new HashMap<String, Device>();
            for (Device device : deviceBeanMap.values()) {
                deviceMap.put(device.getId(), device);
            }
        }
        return deviceMap;
    }


}
