package net.realmproject.dcm.stock.examples.breakout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.realmproject.dcm.features.command.Command;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.flow.filter.FilterBuilder;
import net.realmproject.dcm.parcel.impl.flow.filter.IParcelFilterLink;
import net.realmproject.dcm.parcel.impl.flow.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.stock.camera.Frame;
import net.realmproject.dcm.stock.examples.breakout.engine.Axes;
import net.realmproject.dcm.util.DCMUtil;

public class SwingUI extends IParcelLink {

	//private ParcelHub hub;
	private Display display; 
	private JFrame frame;
	
	public SwingUI() {
		super();

		frame = new JFrame();
		
		frame.setTitle("Breakout");
		
		display = new Display();
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(display, BorderLayout.CENTER);
		

		
		
		frame.addKeyListener(new KeyListener() {

			boolean left, right;
			Timer timer;
			{
				timer = new Timer(false);
				timer.scheduleAtFixedRate(new TimerTask() {
					
					@Override
					public void run() {
						
						if ((left && !right) || (right && !left)) { 
							Axes axes = new Axes();
							axes.axis0 = left ? -1 : 1;

							Command cmd = new Command("move");
							cmd.setProperty("axes", axes);
							
							send(new IParcel<>().targetId("breakout-engine").payload(cmd));
						}
					}
				}, 33, 33);
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					left = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					right = false;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					left = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					right = true;
				}
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}


	@Override
	public void receive(Parcel<?> parcel) {
		
		if (parcel.getPayload() instanceof Frame) {
			try {
				Frame frame = (Frame) parcel.getPayload();
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(frame.image));
				display.image = image;
				display.repaint();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



		
	
}

class Display extends JPanel {
    
	public BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
	
	public Display() {
		setPreferredSize(new Dimension(800, 600));
	}
		
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }
}
