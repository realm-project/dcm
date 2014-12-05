/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 *
 * Description: AbstractDeviceEventPublisher class.
 * 
 */
package net.realmproject.dcm.event.bus;


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.sender.AbstractDeviceEventSender;


/**
 * Abstract base class for anything which sends {@link DeviceEvent}s onto a
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

    protected boolean doSend(DeviceEvent event) {
        return bus.broadcast(event);
    }

}
