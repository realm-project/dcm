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

package net.realmproject.dcm.event.receiver;

import java.util.function.Function;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;


/**
 * Forwards events from one DeviceEventBus to another.
 * 
 * @author NAS
 *
 */
public class IDeviceEventRelay implements DeviceEventReceiver {

	private Predicate<DeviceEvent> filter = a -> true;
	private Function<DeviceEvent, DeviceEvent> transform = a -> a;
    private DeviceEventReceiver to;
    
    public IDeviceEventRelay(DeviceEventReceiver to) {
        this.to = to;
    }
    
    public IDeviceEventRelay(DeviceEventBus from, DeviceEventReceiver to) {
        this(to);
        from.subscribe(this::accept);
    }

    @Override
    public boolean accept(DeviceEvent event) {
        if (!filter.test(event)) { return false; }
    	return to.accept(transform.apply(event));
    }

	public Predicate<DeviceEvent> getFilter() {
		return filter;
	}

	public void setFilter(Predicate<DeviceEvent> filter) {
		this.filter = filter;
	}

	public Function<DeviceEvent, DeviceEvent> getTransform() {
		return transform;
	}

	public void setTransform(Function<DeviceEvent, DeviceEvent> transform) {
		this.transform = transform;
	}
    
    
    
}
