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

package net.realmproject.dcm.messaging.impl;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBusSender;
import net.realmproject.dcm.event.filter.AcceptFilter;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageReceiver;


/**
 * Decodes {@link DeviceMessage}s from a distributed messaging system (eg
 * ActiveMQ) and publishes them to the given {@link DeviceEventBus}
 * 
 * @author maxweld, NAS
 *
 */
public class DeviceMessageDecoder extends IDeviceEventBusSender implements DeviceMessageReceiver {

    private Predicate<IDeviceEvent> filter;

    public DeviceMessageDecoder(DeviceEventBus bus) {
        this(bus, new AcceptFilter());
    }

    public DeviceMessageDecoder(DeviceEventBus bus, Predicate<IDeviceEvent> filter) {
        super(bus);
        this.filter = filter;
    }

    @Override
    public void receive(DeviceMessage<?> deviceMessage) {

        DeviceEventType type = deviceMessage.getDeviceMessageType();
        IDeviceEvent deviceEvent = new IDeviceEvent(type, deviceMessage.getDeviceId(), deviceMessage.getValue());

        if (!filter.test(deviceEvent)) { return; }
        send(deviceEvent);

    }
}
