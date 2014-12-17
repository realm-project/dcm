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
