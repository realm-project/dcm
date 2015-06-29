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


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.source.AbstractDeviceEventSource;


/**
 * Abstract base class for anything which sends {@link DeviceEvent}s onto a
 * {@link DeviceEventBus}
 * 
 * @author NAS, maxweld
 *
 */
public abstract class IDeviceEventBusSource extends AbstractDeviceEventSource {

    private DeviceEventBus bus;

    public IDeviceEventBusSource(DeviceEventBus bus) {
        this.bus = bus;
        startSending();
    }

    protected boolean doSend(DeviceEvent event) {
        return bus.broadcast(event);
    }

}
