package net.realmproject.dcm.features.recording;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class IFileRecordWriter<T extends Serializable> implements RecordWriter<T> {

    ObjectOutputStream out;

    public IFileRecordWriter(String filename) throws IOException {
        out = new ObjectOutputStream(new FileOutputStream(filename));
    }

    @Override
    public void write(T data) throws IOException {
        out.writeObject(data);
    }

    @Override
    public void close() throws Exception {
        out.flush();
        out.close();
    }

    @Override
    public void flush() throws Exception {
        out.flush();
    }

}
