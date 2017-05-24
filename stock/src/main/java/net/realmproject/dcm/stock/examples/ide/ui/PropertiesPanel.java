package net.realmproject.dcm.stock.examples.ide.ui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.beanutils.PropertyUtils;

import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.impl.flow.filter.filters.PayloadClassFilter;
import net.realmproject.dcm.parcel.impl.receiver.IParcelConsumer;
import net.realmproject.dcm.stock.examples.ide.events.NodeChangeEvent;
import net.realmproject.dcm.stock.examples.ide.events.NodeSelectionEvent;

public class PropertiesPanel extends JPanel {

	GridBagConstraints c = new GridBagConstraints();
	ParcelUI parent;
	Sidebar sidebar;
	
	public PropertiesPanel(ParcelUI parent, Sidebar sidebar) {
		this.parent = parent;
		this.sidebar = sidebar;
		
		setLayout(new GridBagLayout());
		
		c.gridx = 0;
		c.gridy = 0;
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		c.insets = new Insets(0, 0, 0, 0);
		c.ipadx = 3;
		c.ipady = 3;
		
        parent.getEventHub().filter(new PayloadClassFilter(NodeSelectionEvent.class)).link(new IParcelConsumer(parcel -> {
        	SwingUtilities.invokeLater(() -> {
        		nodeSelected((NodeSelectionEvent) parcel.getPayload());
        	});
        }));
		
	}
	
	public void nodeSelected(NodeSelectionEvent event) {
		this.removeAll();
		c.gridy=0;
		
		ParcelNode node = event.getGraphNode().getNode();
		
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(node);
		
		for (PropertyDescriptor pd : pds) {
			if (!String.class.isAssignableFrom(pd.getPropertyType())) {continue;}
			
			c.gridx = 0;
			c.fill = GridBagConstraints.NONE;
			JLabel label = new JLabel(pd.getDisplayName());
			label.setToolTipText(pd.getShortDescription());
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			add(label, c);
			
			c.gridx = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			JTextField textbox = new JTextField();
			textbox.setText(getValue(node, pd.getName()));
			add(textbox, c);
			textbox.addActionListener(e -> {
				setValue(node, pd.getName(), textbox.getText());
				parent.getEventHub().receive(new NodeChangeEvent(event.getGraphNode()));
			});

			c.gridy++;
			
		}
		
		sidebar.revalidate();
		sidebar.repaint();

				
	}
	
	private String getValue(ParcelNode node, String propertyName) {
		try {
			return PropertyUtils.getSimpleProperty(node, propertyName).toString();
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private void setValue(ParcelNode node, String propertyName, String value) {
		try {
			PropertyUtils.setSimpleProperty(node, propertyName, value);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
