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

package net.realmproject.dcm.accessor.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.event.DeviceMessageType;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageSender;


public class DeviceWriterUtil {

    public static boolean send(String deviceId, DeviceMessageSender sender, DeviceMessageType type) {
        return send(deviceId, sender, type, new HashMap<String, Serializable>());
    }

    public static boolean send(String deviceId, DeviceMessageSender sender, DeviceMessageType type,
            Map<? extends String, ? extends Serializable> values) {
        if (sender == null) return false;

        DeviceMessage<HashMap<String, Serializable>> deviceMessage = new DeviceMessage<>();

        deviceMessage.setDeviceId(deviceId);
        deviceMessage.setTimestamp(new Date());
        deviceMessage.setValue(new HashMap<>(values));
        deviceMessage.setDeviceMessageType(type);

        sender.send(deviceMessage);
        return true;
    }
}
