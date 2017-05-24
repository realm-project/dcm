package net.realmproject.dcm.stock.examples.ide.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;


public class ScrollSizedPanel extends JPanel {

	public ScrollSizedPanel(Component child, int height) {
		setLayout(new BorderLayout());
		add(child, BorderLayout.CENTER);
		//child.setPreferredSize(new Dimension(260, -1));
		ScrollSizedPanel.this.setPreferredSize(new Dimension(260, (int)Math.min(child.getPreferredSize().getHeight(), height)));
		
		child.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				Dimension preferred = e.getComponent().getPreferredSize();
				ScrollSizedPanel.this.setPreferredSize(new Dimension(260, (int)Math.min(preferred.getHeight(), height)));
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}