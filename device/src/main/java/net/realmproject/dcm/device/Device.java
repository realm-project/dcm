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

package net.realmproject.dcm.device;


import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBusSender;
import net.realmproject.dcm.features.Identity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Device extends IDeviceEventBusSender implements Identity, Logging {

    private String id = null;
    private final Log log = LogFactory.getLog(getClass());

    public Device(String id, DeviceEventBus bus) {
        super(bus);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public Log getLog() {
        return log;
    }

}
