package net.realmproject.dcm.stock.examples.ide.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.StrokeBorder;

public class Expander extends JPanel {

	Component component;
	JPanel header;
	boolean expanded = true;
	
	public Expander(String title, Component component) {
		super();
		Color dark = UIManager.getColor("Panel.background");
		float scale = 0.85f;
		dark = new Color((int)(dark.getRed()*scale), (int)(dark.getGreen()*scale), (int)(dark.getBlue()*scale));
		Color darker = new Color((int)(dark.getRed()*scale), (int)(dark.getGreen()*scale), (int)(dark.getBlue()*scale)); 
		
		
		Border border;
		//border = new LineBorder(Color.black);
		border = new EmptyBorder(1, 1, 1, 1);
		setBorder(border);
		
		this.component = component;
		setLayout(new BorderLayout());
		add(component, BorderLayout.CENTER);
		
		this.header = new JPanel();
		header.setBackground(dark);
		header.setBorder(new LineBorder(darker));
		header.setLayout(new BorderLayout());
		JLabel headerTitle = new JLabel(title);
		headerTitle.setFont(headerTitle.getFont().deriveFont(Font.BOLD));
		header.add(headerTitle, BorderLayout.CENTER);
		JButton expand = new JButton("\u229F");
		expand.setBackground(dark);
		expand.addActionListener(e -> {
			if (expanded) {
				remove(component);
				expand.setText("\u229E");
				expanded = false;
			} else {
				add(component, BorderLayout.CENTER);
				expand.setText("\u229F");
				expanded = true;
			}
		});
		
		expand.setBorder(new EmptyBorder(5, 5, 5, 5));
		header.add(expand, BorderLayout.EAST);
		add(header, BorderLayout.NORTH);
		
	}
	
}
