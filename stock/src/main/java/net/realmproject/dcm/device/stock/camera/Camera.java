package net.realmproject.dcm.device.stock.camera;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.realmproject.dcm.device.Device;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.Statefulness;


public abstract class Camera extends Device implements Statefulness<Frame> {

    protected Frame frame = new Frame();

    protected Camera(String id, DeviceEventBus bus) {
        super(id, bus);
    }

    @Override
    public Frame getState() {
        return frame;
    }

    public void setImage(byte[] data) {
        frame.image = data;
        publishState();
    }

    public void setImage(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        baos.close();
        frame.image = baos.toByteArray();
        publishState();
    }
}
