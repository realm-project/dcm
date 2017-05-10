package net.realmproject.dcm.parcel.impl.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.parcel.core.Parcel;


public class PayloadClassFilter implements Predicate<Parcel<?>> {

    Class<?> cls;

    public PayloadClassFilter(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public boolean test(Parcel<?> t) {
        Object payload = t.getPayload();
        if (payload == null) { return false; }
        return cls.isAssignableFrom(payload.getClass());
    }

}
