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

package net.realmproject.dcm.features.values;


import net.realmproject.dcm.event.Device;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.FilterBuilder;


/**
 * Simple implementation of a device which listens for get/set events, calls
 * Values.setValue on set and publishes Values.getValue on get.
 * 
 * @author NAS
 *
 */
public abstract class IValueDevice extends Device implements Values {

    public IValueDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        bus.subscribe(FilterBuilder.start().target(getId()).eventSet(), this::onSet);
        bus.subscribe(FilterBuilder.start().target(getId()).eventGet(), this::onGet);
    }

    private void onSet(DeviceEvent e) {
        setValue(e.getPayload());
    }

    private void onGet(DeviceEvent e) {
        publishValue();
    }

}
