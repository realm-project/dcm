package net.realmproject.dcm.device.stock.camera;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.realmproject.dcm.event.bus.DeviceEventBus;

//modified from https://github.com/karlqui/RobotProject/blob/master/mjpeg/MjpegRunner.java
public class MJpegCamera extends Camera {

	private static final String CONTENT_LENGTH = "Content-Length: ";
	private static final String TIMESTAMP = "X-Timestamp: ";
	
	private String urlString;
	private URLConnection conn;
	private InputStream cameraStream;
	private StringWriter stringWriter;
	
	protected MJpegCamera(String id, DeviceEventBus bus, String url) throws IOException {
		super(id, bus);
		this.urlString = url;
		connect();
	}
	
	protected void connect() throws IOException {
		URL url = new URL(urlString);
		conn = url.openConnection();
		conn.setReadTimeout(2000);
		conn.setConnectTimeout(2000);
		conn.connect();
		cameraStream = conn.getInputStream();
		stringWriter = new StringWriter();
	}
	
	protected void receive() {
		System.out.println("Receive camera started");
		while (true) {
			try {
				byte[] image = retrieveNextImage();
				System.out.println("grabbed frame");
				frame.image = image;
				publishState();
			} catch (Exception e) {
				getLog().error("Failed to read frame", e);
			}
		}
	}
	
	
	private byte[] retrieveNextImage() throws IOException
	{
		boolean haveHeader = false; 
		int currByte = -1;
		
		String header = null;
		while((currByte = cameraStream.read()) > -1 && !haveHeader) //read bytes until header is found
		{
			stringWriter.write(currByte); //read bytes as string
			
			String tempString = stringWriter.toString(); 
			int indexOf = tempString.indexOf(TIMESTAMP); //make sure to get content length in the header			
			if(indexOf > 0)
			{
				haveHeader = true;
				header = tempString; //save the header(or atleast the important parts of it)
				
			}
		}		
		
		// 255 indicates the start of the jpeg image data
		while((cameraStream.read()) != 255)
		{
			//read the rest of the header data
		}
		
		// rest is the buffer
		int contentLength = contentLength(header);
		
		byte[] imageBytes = new byte[contentLength + 1];
		// since we ate the original 255 , shove it back in
		
		imageBytes[0] = (byte)255;
		int offset = 1;
        int numRead = 0;
        while (offset < imageBytes.length
               && (numRead=cameraStream.read(imageBytes, offset, imageBytes.length-offset)) >= 0) {
            offset += numRead;
        }       
		
		stringWriter = new StringWriter(128);
		
		return imageBytes;
	}
	
	// dirty but it works content-length parsing
	private static int contentLength(String header)
	{
		int indexOfContentLength = header.indexOf(CONTENT_LENGTH);
		
		//System.out.println(indexOfContentLength + "asdasd" );
		//System.out.println(header);	
		int valueStartPos = indexOfContentLength + CONTENT_LENGTH.length();
		int indexOfEOL = header.indexOf('\n', indexOfContentLength);
		
		String lengthValStr = header.substring(valueStartPos, indexOfEOL).trim();
		
		int retValue = Integer.parseInt(lengthValStr);
		
		return retValue;
	}

}
