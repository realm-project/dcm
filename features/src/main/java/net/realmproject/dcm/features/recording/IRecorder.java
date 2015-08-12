package net.realmproject.dcm.features.recording;


import java.io.Serializable;


/**
 * Generic implementation of the {@link Recording} interface which manages a
 * {@link RecordWriter} and implements a default record method using it
 * 
 * @author NAS
 *
 * @param <T>
 */
public class IRecorder<T extends Serializable> implements Recording<T> {

    RecordWriter<T> writer;

    public IRecorder(RecordWriter<T> writer) {
        setRecordWriter(writer);
    }

    @Override
    public RecordWriter<T> getRecordWriter() {
        return writer;
    }

    @Override
    public void setRecordWriter(RecordWriter<T> writer) {
        this.writer = writer;
    }

    @Override
    public void record(T data) throws Exception {
        RecordWriter<T> writer = getRecordWriter();
        if (writer == null) { return; }
        writer.write(data);
    }

}
