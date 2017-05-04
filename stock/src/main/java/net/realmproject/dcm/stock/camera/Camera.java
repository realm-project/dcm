package net.realmproject.dcm.stock.camera;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import net.realmproject.dcm.features.command.ICommandDevice;
import net.realmproject.dcm.parcel.hub.ParcelHub;


public abstract class Camera extends ICommandDevice<Frame> {

    protected Frame frame = new Frame();
    protected float quality = 0.5f;

    protected Camera(String id, ParcelHub bus) {
        super(id, bus);

        try {
            InputStream framestream = Camera.class
                    .getResourceAsStream("/net/realmproject/dcm/stock/camera/nosignal.png");
            BufferedImage image = ImageIO.read(framestream);
            setImage(fromBufferedImage(image, quality));

        }
        catch (IOException e) {
            getLog().error(e.getMessage(), e);
        }

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
        return fromBufferedImage(image, quality);
    }

    protected byte[] fromBufferedImage(BufferedImage image, float quality) throws IOException {

        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = new JPEGImageWriteParam(null);
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        writer.write(null, new IIOImage(image, null, null), param);
        writer.dispose();
        return os.toByteArray();

    }

    protected byte[] process(byte[] frame) {
        return frame;
    }
}
