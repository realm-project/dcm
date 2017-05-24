package net.realmproject.dcm.stock.examples.ide.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.realmproject.dcm.parcel.core.ParcelNode;

public class NodesPanel extends JPanel {

	private GridBagConstraints c = new GridBagConstraints();
	private NodeStore nodeStore = new NodeStore();
	
	public NodesPanel(Consumer<Class<? extends ParcelNode>> consumer) {
	
		setLayout(new GridBagLayout());
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		for (Class<? extends ParcelNode> clazz : nodeStore.getParcelNodeClasses()) {
			String type = "generic";
			Icon icon = new ImageIcon(ParcelUI.class.getResource("icons/24-black/" + type + ".png"));
			JButton btn = new JButton(clazz.getSimpleName(), icon);
			btn.setHorizontalAlignment(SwingConstants.LEFT);
			add(btn, c);
			btn.addActionListener(e -> {
				consumer.accept(clazz);
			});
		}
		
	}
	
}
