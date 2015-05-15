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

package net.realmproject.dcm.event.filter.deviceeventtype;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;


/**
 * DeviceEvent filter to allow only events intended for a 'backend', something
 * only interested in listening to get/set events, and not messages or status
 * updates from other devices
 * 
 * @author NAS
 *
 */
public class ValueGetSetFilter implements Predicate<DeviceEvent> {

    @Override
    public boolean test(DeviceEvent e) {
        DeviceEventType type = e.getDeviceEventType();
        return type == DeviceEventType.VALUE_GET || type == DeviceEventType.VALUE_SET;
    }

}