package net.realmproject.dcm.stock.examples.ide.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Expander extends JPanel {

	Component component;
	JPanel header;
	
	public Expander(String title, Component component) {
		super();
		
		setBorder(new EmptyBorder(1, 1, 1, 1));
		
		this.component = component;
		setLayout(new BorderLayout());
		add(component, BorderLayout.CENTER);
		
		this.header = new JPanel();
		header.setLayout(new BorderLayout());
		JLabel headerTitle = new JLabel(title);
		headerTitle.setFont(headerTitle.getFont().deriveFont(Font.BOLD));
		header.add(headerTitle, BorderLayout.CENTER);
		JButton expand = new JButton("\u229F");
		expand.addActionListener(e -> {
			if (component.isVisible()) {
				component.setVisible(false);
				expand.setText("\u229E");
			} else {
				component.setVisible(true);
				expand.setText("\u229F");
			}
		});
		
		expand.setBorder(new EmptyBorder(5, 5, 5, 5));
		header.add(expand, BorderLayout.EAST);
		add(header, BorderLayout.NORTH);
		
	}
	
}
