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

package net.realmproject.dcm.messaging.mq.impl;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageSender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Base class for listening for events on a {@link DeviceEventBus}, encoding
 * them into {@link DeviceMessage}s and publishing them to a distributed
 * messaging system (eg ActiveMQ)
 * 
 * @author maxweld, NAS
 *
 */
public abstract class AbstractDeviceMessageEncoder implements DeviceMessageSender {

    protected Log log = LogFactory.getLog(getClass());

    public AbstractDeviceMessageEncoder(DeviceEventBus bus) {
        bus.subscribe(this::transmit);
    }

    @SuppressWarnings("unchecked")
    public void transmit(DeviceEvent deviceEvent) {

        if (deviceEvent.getDeviceMessageType() != DeviceEventType.VALUE_CHANGED) {
            log.warn("DeviceEvent type is not handled.");
            return;
        }

        HashMap<String, Serializable> valueMap = new HashMap<String, Serializable>();

        if (deviceEvent.getValue() instanceof Map) {
            try {
                valueMap.putAll((Map<String, Serializable>) deviceEvent.getValue());
            }
            catch (ClassCastException e) {
                log.info("DeviceEvent value is not Map<String,Serializable>.");
            }
        } else if (deviceEvent.getValue() instanceof Serializable) {
            valueMap.put("value", deviceEvent.getValue());
        } else {
            log.warn("DeviceEvent value is not Serializable.");
        }

        DeviceMessage<HashMap<String, Serializable>> deviceMessage = new DeviceMessage<HashMap<String, Serializable>>();

        deviceMessage.setValue(valueMap);
        deviceMessage.setDeviceId(deviceEvent.getDeviceId());
        deviceMessage.setTimestamp(deviceEvent.getTimestamp());
        deviceMessage.setDeviceMessageType(DeviceEventType.VALUE_CHANGED);

        send(deviceMessage);

    }

}
