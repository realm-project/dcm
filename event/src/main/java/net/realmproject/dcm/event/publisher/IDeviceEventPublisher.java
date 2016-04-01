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

package net.realmproject.dcm.event.publisher;

import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventNode;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.event.relay.AbstractDeviceEventRelay;


/**
 * 
 * @author maxweld, NAS
 *
 */

public class IDeviceEventPublisher extends AbstractDeviceEventRelay implements DeviceEventNode, DeviceEventPublisher, Logging {

    private DeviceEventReceiver receiver;

    public IDeviceEventPublisher(DeviceEventReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void publish(DeviceEvent event) {
        getLog().trace("publish() called for " + event + " from " + this.getId());
        if (event == null) { return; }
        if (!filter(event)) { return; }
        getLog().trace("Publishing event " + event + " from " + this.getId());
        receiver.accept(transform(event));
        getLog().trace("Event " + event + " Published from " + this.getId());
    }

}
