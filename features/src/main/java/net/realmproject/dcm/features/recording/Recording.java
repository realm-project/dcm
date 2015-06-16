package net.realmproject.dcm.features.recording;


import java.io.Serializable;


public interface Recording<T extends Serializable> {

    void record(T data) throws Exception;

    RecordWriter<T> getRecordWriter();

    void setRecordWriter(RecordWriter<T> writer);

}
