/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 *
 * Description: DeviceMessage class
 * 
 */
package net.realmproject.dcm.messaging;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


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
