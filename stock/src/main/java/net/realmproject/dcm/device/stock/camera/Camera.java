package net.realmproject.dcm.device.stock.camera;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.realmproject.dcm.device.Device;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.Statefulness;
import net.realmproject.dcm.features.connection.Heartbeat;


public abstract class Camera extends Device implements Heartbeat, Statefulness<Frame> {

    protected Frame frame = new Frame();
    private int heartbeatInterval;

    protected Camera(String id, DeviceEventBus bus, int interval) {
        super(id, bus);
        this.heartbeatInterval = interval;
    }

    public void init() {
        initHeartbeat(heartbeatInterval);
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
        frame.image = fromImage(image, format);
        publishState();
    }

    // takes a buffered image and returns a base64-encoded image
    private byte[] fromImage(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(image, format, baos);

        baos.close();

        return baos.toByteArray();
    }

}
