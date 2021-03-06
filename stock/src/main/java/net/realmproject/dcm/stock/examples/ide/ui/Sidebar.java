package net.realmproject.dcm.stock.examples.ide.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


public class Sidebar extends JPanel {

	//SpringLayout layout = new SpringLayout();
	//Component last = null;
	GridBagConstraints c = new GridBagConstraints();
	
	public Sidebar() {
		setLayout(new GridBagLayout());
		
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.insets = new Insets(5, 5, 5, 15);
			
		
	}
	
	public void addSidebarPanel(Component panel) {
		add(panel, c);
	}
	
	public void finalizeSidebar() {
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1.0;
		JPanel filler = new JPanel();
		
		add(filler, c);
	}

	
}

