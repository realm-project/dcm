package net.realmproject.dcm.stock.camera;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    private int connectionInterval = 10000;

    protected MjpegCamera(String id, DeviceEventBus bus, String url) throws MalformedURLException {
        super(id, bus);
        this.url = new URL(url);
        header = new StringWriter(128);
        initConnection();
        DCMThreadPool.getPool().submit(this::receive);

    }

    @Override
    public void connect() throws Exception {
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(connectionTimeout);
        conn.setConnectTimeout(connectionTimeout);
        conn.connect();
        input = conn.getInputStream();
    }

    private void receive() {
        while (!Thread.interrupted()) {
            try {
                setImage(getFrame());
            }
            catch (Exception e) {
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                } else {
                    logConnectionError(e);
                    disconnected(e);
                }
            }
        }
        Thread.currentThread().interrupt();
    }

    private byte[] getFrame() throws IOException {

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

    @Override
    public void onConnect() throws Exception {}

    @Override
    public void onDisconnect(Exception exception) {

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

        // don's spam the camera
        DCMInterrupt.handle(() -> Thread.sleep(connectionInterval));
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getConnectionInterval() {
        return connectionInterval;
    }

    public void setConnectionInterval(int connectionDelay) {
        this.connectionInterval = connectionDelay;
    }

}
