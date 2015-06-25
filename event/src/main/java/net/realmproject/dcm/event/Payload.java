package net.realmproject.dcm.event;


public interface Payload<T> {

    T getPayload();

    void setPayload(T payload);

}
