package net.realmproject.dcm.stock.examples.ui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class NodeUI extends JPanel {

	private GraphNode node;
	
	public NodeUI(GraphNode node) {
		this.node = node;
		setLayout(new BorderLayout());
		add(new JLabel(node.getNode().getId()), BorderLayout.SOUTH);
		add(new JLabel(new ImageIcon(node.getImage())), BorderLayout.CENTER);
	}
	
}
