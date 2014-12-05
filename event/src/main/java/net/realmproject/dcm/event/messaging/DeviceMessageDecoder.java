/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 *
 * Description: SimpleDeviceMessageReceiverEventPublisher class.
 */
package net.realmproject.dcm.event.messaging;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBusSender;
import net.realmproject.dcm.event.filter.AcceptFilter;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageReceiver;
import net.realmproject.dcm.messaging.DeviceMessageType;


/**
 * Decodes {@link DeviceMessage}s from a distributed messaging system (eg
 * ActiveMQ) and publishes them to the given {@link DeviceEventBus}
 * 
 * @author maxweld, NAS
 *
 */
public class DeviceMessageDecoder extends IDeviceEventBusSender implements DeviceMessageReceiver {

    private Predicate<DeviceEvent> filter;

    public DeviceMessageDecoder(DeviceEventBus bus) {
        this(bus, new AcceptFilter());
    }

    public DeviceMessageDecoder(DeviceEventBus bus, Predicate<DeviceEvent> filter) {
        super(bus);
        this.filter = filter;
    }

    @Override
    public void receive(DeviceMessage<?> deviceMessage) {

        DeviceMessageType type = deviceMessage.getDeviceMessageType();
        DeviceEvent deviceEvent = new DeviceEvent(type, deviceMessage.getDeviceId(), deviceMessage.getValue());

        if (!filter.test(deviceEvent)) { return; }
        send(deviceEvent);

    }
}
