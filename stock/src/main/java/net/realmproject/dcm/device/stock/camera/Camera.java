package net.realmproject.dcm.device.stock.camera;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.realmproject.dcm.device.Device;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.Publishing;
import net.realmproject.dcm.features.Statefulness;
import net.realmproject.dcm.features.connection.Heartbeat;

import org.apache.commons.codec.binary.Base64;


public abstract class Camera extends Device implements Heartbeat, Statefulness<Frame> {

    protected Frame frame = new Frame();

    protected Camera(String id, DeviceEventBus bus, int interval) {
        super(id, bus);
        initHeartbeat(interval);
    }

    @Override
    public Frame getState() {
        return frame;
    }

    public void setImage(BufferedImage image, String format) throws IOException {
        frame.image = fromImage(image, format);
        publishState();
    }

    public void setImage(String base64image) throws IOException {
        frame.image = base64image;
        publishState();
    }

    // takes a buffered image and returns a base64-encoded image
    private String fromImage(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(image, format, baos);

        baos.close();

        return new String(Base64.encodeBase64(baos.toByteArray()), "US-ASCII");
    }

}
