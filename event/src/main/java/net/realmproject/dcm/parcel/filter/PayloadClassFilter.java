package net.realmproject.dcm.parcel.filter;


import java.io.Serializable;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.Parcel;


public class PayloadClassFilter implements Predicate<Parcel> {

    Class<?> cls;

    public PayloadClassFilter(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public boolean test(Parcel t) {
        Serializable payload = t.getPayload();
        if (payload == null) { return false; }
        return cls.isAssignableFrom(payload.getClass());
    }

}
