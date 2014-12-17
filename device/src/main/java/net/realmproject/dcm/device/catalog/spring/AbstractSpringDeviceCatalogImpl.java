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
