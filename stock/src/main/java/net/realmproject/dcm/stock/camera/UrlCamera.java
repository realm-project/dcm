package net.realmproject.dcm.stock.camera;


import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import net.realmproject.dcm.event.bus.DeviceEventBus;


public class UrlCamera extends Camera {

    private URL cameraURL;
    private boolean logGetFrameException = true; // limit exception spamming

    protected UrlCamera(String id, DeviceEventBus bus, String url) throws MalformedURLException {
        super(id, bus);
        cameraURL = new URL(url);
    }

    protected void getFrame() {
        try {
            InputStream is = cameraURL.openStream();
            byte[] bytes = IOUtils.toByteArray(is);
            is.close();
            setImage(bytes);
            logGetFrameException = true;
        }
        catch (Exception e) {
            if (logGetFrameException) {
                getLog().error("Error getting camera frame", e);
            }
            logGetFrameException = false;
        }
    }

}
