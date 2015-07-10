package net.realmproject.dcm.device.stock.camera;


import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import net.realmproject.dcm.event.bus.DeviceEventBus;


public class URLCamera extends Camera {

    private URL cameraURL;
    private long lastGetFrameException = 0; // limit exception spamming

    protected URLCamera(String id, DeviceEventBus bus, String url) throws MalformedURLException {
        super(id, bus);
        cameraURL = new URL(url);
    }

    protected void getFrame() {
        try {
        	
        	URLConnection conn = cameraURL.openConnection();
        	conn.setConnectTimeout(250);
        	conn.setReadTimeout(250);
        	conn.connect();
        	InputStream is = conn.getInputStream();
            byte[] bytes = IOUtils.toByteArray(is);
            is.close();
            frame.image = bytes;
            publishState();
        }
        catch (Exception e) {
            if (lastGetFrameException < System.currentTimeMillis() - 60) {
                getLog().error("Error getting camera frame", e);
            }
            lastGetFrameException = System.currentTimeMillis();
        }
    }

}
