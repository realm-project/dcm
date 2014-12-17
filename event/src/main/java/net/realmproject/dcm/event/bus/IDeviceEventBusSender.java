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

package net.realmproject.dcm.event.bus;


import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.sender.AbstractDeviceEventSender;


/**
 * Abstract base class for anything which sends {@link IDeviceEvent}s onto a
 * {@link DeviceEventBus}
 * 
 * @author NAS, maxweld
 *
 */
public abstract class IDeviceEventBusSender extends AbstractDeviceEventSender {

    private DeviceEventBus bus;

    public IDeviceEventBusSender(DeviceEventBus bus) {
        this.bus = bus;
        startSending();
    }

    protected boolean doSend(IDeviceEvent event) {
        return bus.broadcast(event);
    }

}
