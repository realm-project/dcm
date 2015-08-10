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


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventNode;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.event.relay.IDeviceEventRelay;


/**
 * 
 * @author maxweld, NAS
 *
 */

public abstract class AbstractDeviceEventPublisher extends IDeviceEventRelay
        implements DeviceEventNode, DeviceEventPublisher {

    private DeviceEventReceiver receiver;

    public AbstractDeviceEventPublisher(DeviceEventReceiver receiver) {
        this.receiver = receiver;
    }

    // this method is intended to allow different implementations of the sending
    // action but any attempt to actually send an event should go send(event),
    // since it wraps this method and performs required bookkeeping that this
    // method does not
    /**
     * This method is for internal use only, and should not be called. Call
     * publish(event) instead.
     * 
     * @param event
     *            the event to send
     */

    protected void doPublish(DeviceEvent event) {
        receiver.accept(event);
    }

    @Override
    public void publish(DeviceEvent event) {
        if (event == null) { return; }
        if (!filter(event)) { return; }
        doPublish(transform(event));
    }

}
