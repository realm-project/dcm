package net.realmproject.dcm.features.recording;


import java.io.Serializable;


/**
 * Interface to record arbitrary data using a {@link RecordWriter}
 * 
 * @author NAS
 *
 * @param <T>
 */
public interface Recording<T extends Serializable> {

    void record(T data) throws Exception;

    RecordWriter<T> getRecordWriter();

    void setRecordWriter(RecordWriter<T> writer);

}
