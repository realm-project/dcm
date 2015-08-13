package net.realmproject.dcm.features.value;


import java.io.Serializable;

import net.realmproject.dcm.event.bus.DeviceEventBus;


public class IValueDevice<T extends Serializable> extends AbstractValueDevice<T> {

    private T value;

    public IValueDevice(String id, DeviceEventBus bus) {
        super(id, bus);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T val) {
        this.value = val;
        publishValue();
    }

}
