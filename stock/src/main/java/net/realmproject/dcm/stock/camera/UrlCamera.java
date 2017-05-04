package net.realmproject.dcm.stock.camera;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import net.realmproject.dcm.parcel.flow.hub.ParcelHub;


public class UrlCamera extends Camera {

    private URL cameraURL;
    private long lastGetFrameException = 0; // limit exception spamming

    protected UrlCamera(String id, ParcelHub bus, String url) throws MalformedURLException {
        super(id, bus);
        cameraURL = new URL(url);
    }

    protected void getFrame() {
        try {

            HttpURLConnection conn = (HttpURLConnection) cameraURL.openConnection();
            conn.setConnectTimeout(250);
            conn.setReadTimeout(250);
            conn.connect();
            InputStream is = conn.getInputStream();
            byte[] bytes = IOUtils.toByteArray(is);
            is.close();
            conn.disconnect();
            frame.image = bytes;
            publishState();
        }
        catch (Exception e) {
            if (lastGetFrameException < System.currentTimeMillis() - 60) {
                getLog().debug("Error getting camera frame: " + e.getMessage());
                getLog().trace("Error getting camera frame", e);
            }
            lastGetFrameException = System.currentTimeMillis();
        }
    }

}
