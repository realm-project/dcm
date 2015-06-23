package net.realmproject.dcm.features.recording;


import java.io.Serializable;


/**
 * Interface for persisting data recorded from a {@link Recording} object
 * 
 * @author NAS
 *
 * @param <T>
 *            The type of data this RecordWriter accepts
 */
public interface RecordWriter<T extends Serializable> extends AutoCloseable {

    void write(T data) throws Exception;

    void flush() throws Exception;

}
