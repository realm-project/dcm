/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 *
 * Description: DelegatingDeviceEventListenerMessageSender class.
 * 
 */
package net.realmproject.dcm.event.messaging;


import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageSender;


/**
 * Adapts a specific method of transmitting {@link DeviceMessage}s on a
 * distributed messaging system (eg ActiveMQ) for an
 * {@link AbstractDeviceMessageEncoder}
 * 
 * @author maxweld
 *
 */
public class DelegatingDeviceMessageEncoder extends AbstractDeviceMessageEncoder {

    private DeviceMessageSender deviceMessageSender;

    public DelegatingDeviceMessageEncoder(DeviceEventBus bus) {
        super(bus);
    }

    @Override
    public void send(DeviceMessage<?> deviceMessage) {
        deviceMessageSender.send(deviceMessage);
    }

    public void setDeviceMessageSender(DeviceMessageSender deviceMessageSender) {
        this.deviceMessageSender = deviceMessageSender;
    }
}
