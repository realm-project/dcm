package net.realmproject.dcm.stock.camera;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.realmproject.dcm.command.connected.HeartbeatCommandDevice;
import net.realmproject.dcm.event.bus.DeviceEventBus;

import org.apache.commons.codec.binary.Base64;


public abstract class Camera extends HeartbeatCommandDevice<Frame> {

    protected Frame frame = new Frame();

    protected Camera(String id, DeviceEventBus bus) {
        super(id, bus, 5, true);
    }

    @Override
    protected Frame getState() {
        return frame;
    }

    public void setImage(BufferedImage image, String format) throws IOException {
        frame.image = fromImage(image, format);
        publish();
    }

    public void setImage(String base64image) throws IOException {
        frame.image = base64image;
        publish();
    }

    // takes a buffered image and returns a base64-encoded image
    private String fromImage(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(image, format, baos);

        baos.close();

        return new String(Base64.encodeBase64(baos.toByteArray()), "US-ASCII");
    }

}
