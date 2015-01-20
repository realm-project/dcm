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

package net.realmproject.dcm.messaging;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import net.realmproject.dcm.event.DeviceMessageType;


/**
 * 
 * Wire-format object for device communication
 * 
 * @author maxweld
 *
 */
public class DeviceMessage<T extends Serializable & Map<String, Serializable>> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String deviceId;
    private DeviceMessageType deviceMessageType;

    private T value;

    private String id = UUID.randomUUID().toString();
    private Date timestamp = new Date();

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceMessageType getDeviceMessageType() {
        return deviceMessageType;
    }

    public void setDeviceMessageType(DeviceMessageType deviceMessageType) {
        this.deviceMessageType = deviceMessageType;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
