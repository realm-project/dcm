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

package net.realmproject.dcm.device;


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.Filters;
import net.realmproject.dcm.features.Publishing;
import net.realmproject.dcm.features.Values;


/**
 * Simple implementation of a device which listens for get/set events, calls
 * Values.setValue on set and publishes Values.getValue on get.
 * 
 * @author NAS
 *
 */
public abstract class ValueDevice extends Device implements Values, Publishing {

    public ValueDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        bus.subscribe(Filters.id(getId()).and(Filters.setEvents()), this::onSet);
        bus.subscribe(Filters.id(getId()).and(Filters.getEvents()), this::onGet);
    }

    private void onSet(DeviceEvent e) {
        setValue(e.getPayload());
    }

    private void onGet(DeviceEvent e) {
        publishValue();
    }

}
