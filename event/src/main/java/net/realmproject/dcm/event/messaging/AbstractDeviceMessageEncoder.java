/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 *
 * Description: AbstractDeviceEventListenerMessageSender class.
 * 
 */
package net.realmproject.dcm.event.messaging;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageSender;
import net.realmproject.dcm.messaging.DeviceMessageType;

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

        if (deviceEvent.getDeviceMessageType() != DeviceMessageType.VALUE_CHANGED) {
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
        deviceMessage.setDeviceMessageType(DeviceMessageType.VALUE_CHANGED);

        send(deviceMessage);

    }

}
