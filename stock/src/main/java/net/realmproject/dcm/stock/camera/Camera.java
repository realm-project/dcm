package net.realmproject.dcm.stock.camera;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.realmproject.dcm.device.CommandDevice;
import net.realmproject.dcm.event.bus.DeviceEventBus;


public abstract class Camera extends CommandDevice<Frame> {

    protected Frame frame = new Frame();

    protected Camera(String id, DeviceEventBus bus) {
        super(id, bus);
    }

    @Override
    public Frame getState() {
        return frame;
    }

    public void setImage(byte[] data) {
        frame.image = process(data);
        publishState();
    }

    protected byte[] fromBufferedImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        baos.close();
        return process(baos.toByteArray());
    }

    protected byte[] process(byte[] frame) {
        return frame;
    }
}
