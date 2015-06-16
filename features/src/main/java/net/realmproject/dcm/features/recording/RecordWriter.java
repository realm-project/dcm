package net.realmproject.dcm.features.recording;


import java.io.Serializable;


public interface RecordWriter<T extends Serializable> extends AutoCloseable {

    void write(T data) throws Exception;

    void flush() throws Exception;

}
