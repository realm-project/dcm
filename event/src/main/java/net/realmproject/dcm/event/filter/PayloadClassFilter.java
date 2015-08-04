package net.realmproject.dcm.event.filter;


import java.io.Serializable;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;


public class PayloadClassFilter implements Predicate<DeviceEvent> {

    Class<?> cls;

    public PayloadClassFilter(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public boolean test(DeviceEvent t) {
        Serializable payload = t.getPayload();
        if (payload == null) { return false; }
        return cls.isAssignableFrom(payload.getClass());
    }

}
