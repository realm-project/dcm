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

package net.realmproject.dcm.event.relay;


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventNode;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;


/**
 * Forwards events to a {@link DeviceEventReceiver}. Optionally accepts a
 * {@link DeviceEventBus} to subscribe to for events.
 * 
 * @author NAS
 *
 */
public class IDeviceEventForwarder extends IDeviceEventRelay implements DeviceEventNode, DeviceEventReceiver {

    private DeviceEventReceiver to;

    public IDeviceEventForwarder(DeviceEventReceiver to) {
        this.to = to;
    }

    public IDeviceEventForwarder(DeviceEventBus from, DeviceEventReceiver to) {
        this(to);
        from.subscribe(this::accept);
    }

    @Override
    public void accept(DeviceEvent event) {
        if (!filter(event)) { return; }
        to.accept(transform(event));
    }

}
