package net.realmproject.dcm.stock.camera;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.connection.Connection;
import net.realmproject.dcm.util.DCMInterrupt;
import net.realmproject.dcm.util.DCMThreadPool;


// modified from
// https://github.com/karlqui/RobotProject/blob/master/mjpeg/MjpegRunner.java
public class MjpegCamera extends Camera implements Connection {

    private static final String CONTENT_LENGTH = "Content-Length: ";

    private URL url;
    private HttpURLConnection conn;
    private InputStream input;
    private StringWriter header;
    private int connectionTimeout = 10000;

    Connection.State connectionState;
    Map<String, Object> dcmStateMap = new HashMap<>();

    private boolean shutdown = false;

    protected MjpegCamera(String id, DeviceEventBus bus, String url) throws MalformedURLException {
        super(id, bus);

        this.url = new URL(url);
        header = new StringWriter(128);

        connectionInitialize();

        DCMThreadPool.getPool().submit(this::receive);
        DCMThreadPool.onShutdown(() -> shutdown = true);

    }

    @Override
    public synchronized void connectionOpen() throws Exception {
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(connectionTimeout);
        conn.setConnectTimeout(connectionTimeout);
        conn.connect();
        input = conn.getInputStream();
    }

    @Override
    public synchronized void connectionClose() {

        if (input != null) {
            try {
                input.close();
            }
            catch (IOException e) {}
            input = null;
        }

        if (conn != null) {
            conn.disconnect();
            conn = null;
        }

    }

    private void receive() {
        while (!Thread.interrupted() && !shutdown) {
            DCMInterrupt.handle(() -> setImage(getFrame()), this::connectionFailed);
        }
        Thread.currentThread().interrupt();
    }

    private synchronized byte[] getFrame() throws IOException {
        if (input == null) {
            connectionFailed(new NullPointerException("InputStream was null"));
        }

        header.getBuffer().setLength(0);

        int currByte = -1;
        while ((currByte = input.read()) > -1) {
            if (currByte == 255) {
                break;
            }
            header.write(currByte);
        }

        // rest is the buffer
        int contentLength = contentLength(header.toString());

        byte[] imageBytes = new byte[contentLength + 1];
        // since we ate the original 255 , shove it back in

        imageBytes[0] = (byte) 255;
        int offset = 1;
        int numRead = 0;
        while (offset < imageBytes.length
                && (numRead = input.read(imageBytes, offset, imageBytes.length - offset)) >= 0) {
            offset += numRead;
        }

        return imageBytes;
    }

    // dirty but it works content-length parsing
    private static int contentLength(String header) {

        int indexOfContentLength = header.indexOf(CONTENT_LENGTH);
        int valueStartPos = indexOfContentLength + CONTENT_LENGTH.length();
        int indexOfEOL = header.indexOf('\n', indexOfContentLength);

        String lengthValStr = header.substring(valueStartPos, indexOfEOL).trim();
        int retValue = Integer.parseInt(lengthValStr);

        return retValue;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public State getConnectionState() {
        return connectionState;
    }

    @Override
    public void setConnectionState(State state) {
        connectionState = state;
    }

    @Override
    public Map<String, Object> getDCMStateMap() {
        return dcmStateMap;
    }

}
