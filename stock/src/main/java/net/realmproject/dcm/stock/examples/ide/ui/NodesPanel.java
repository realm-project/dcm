package net.realmproject.dcm.stock.examples.ide.ui;

import java.awt.Color;
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
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.stock.examples.ide.NodeUtil;
import net.realmproject.dcm.stock.examples.ide.events.NodeSetChangedEvent;

public class NodesPanel extends JPanel {

	private GridBagConstraints c = new GridBagConstraints();
	private NodeStore nodeStore = new NodeStore();
	
	public NodesPanel(ParcelUI ui) {
	
		setLayout(new GridBagLayout());
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		for (Class<? extends ParcelNode> clazz : nodeStore.getParcelNodeClasses()) {
			String type = NodeUtil.getType(clazz).toString().toLowerCase();
			String name = NodeUtil.getName(clazz);

			Icon icon = new ImageIcon(ParcelUI.class.getResource("icons/24-black/" + type + ".png"));
			JButton btn = new JButton(name, icon);
			btn.setHorizontalAlignment(SwingConstants.LEFT);
			add(btn, c);
			
			btn.setBackground(new Color(UIManager.getColor("Panel.background").getRGB()));
			btn.setBorder(new BevelBorder(BevelBorder.RAISED));
			
			btn.addActionListener(e -> {
				ui.addNodeFromClass(clazz);
			});
		}
		
	}
	
}
