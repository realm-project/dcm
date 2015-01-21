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

package net.realmproject.dcm.event.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;


/**
 * DeviceEvent filter to allow only events intended for a 'frontend', something
 * only interested in listening to the status of devices
 * 
 * @author NAS
 *
 */
public class FrontendFilter implements Predicate<IDeviceEvent> {

    @Override
    public boolean test(IDeviceEvent e) {
        DeviceEventType type = e.getDeviceMessageType();
        return type == DeviceEventType.VALUE_CHANGED;
    }

}
