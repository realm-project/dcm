package net.realmproject.dcm.event;


public interface Payload<T> {

    <S extends T> S getPayload();

    void setPayload(T payload);

}
