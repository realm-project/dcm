package net.realmproject.dcm.stock.examples.ide.ui.properties;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.openide.nodes.BeanNode;

import com.github.sarxos.l2fprod.sheet.DefaultBeanBinder;
import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheetPanel;

import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.flow.filter.filters.PayloadClassFilter;
import net.realmproject.dcm.parcel.impl.receiver.IParcelConsumer;
import net.realmproject.dcm.parcel.impl.service.IParcelService;
import net.realmproject.dcm.stock.examples.ide.events.NodeChangeEvent;
import net.realmproject.dcm.stock.examples.ide.events.NodeSelectionEvent;
import net.realmproject.dcm.stock.examples.ide.ui.ParcelUI;
import net.realmproject.dcm.stock.examples.ide.ui.Sidebar;

public class PropertiesPanel extends JPanel {

	ParcelUI parent;
	Sidebar sidebar;
	
	public PropertiesPanel(ParcelUI parent, Sidebar sidebar) {
		this.parent = parent;
		this.sidebar = sidebar;
				
		setLayout(new BorderLayout());
				
        parent.getEventHub().filter(new PayloadClassFilter(NodeSelectionEvent.class)).link(new IParcelConsumer(parcel -> {
        	SwingUtilities.invokeLater(() -> {
        		nodeSelected((NodeSelectionEvent) parcel.getPayload());
        	});
        }));
		
	}
	
	public void nodeSelected(NodeSelectionEvent event) {


		this.removeAll();
		
		PropertyEditorManager.registerEditor(ParcelService.class, ServiceEditor.class);
		
		ParcelNode node = event.getGraphNode().getNode();
		PropertySheetPanel panel = new PropertySheetPanel();

		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(node.getClass());
//			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
//				if (ParcelService.class.isAssignableFrom(pd.getPropertyType())) {
//					pd.setPropertyEditorClass(ServiceEditor.class);
//				}
//			}
		} catch (IntrospectionException e1) {
			e1.printStackTrace();
		}
		
		new DefaultBeanBinder(node, panel, info);
		panel.getTable().getColumnModel().getColumn(0).setPreferredWidth(80);
		panel.getTable().getColumnModel().getColumn(1).setPreferredWidth(120);
		panel.getTable().setPreferredScrollableViewportSize(new Dimension(200, 400));
		
		panel.addPropertySheetChangeListener(e -> {
			Property prop = (Property) e.getSource();
			prop.writeToObject(node);
			parent.getEventHub().receive(new NodeChangeEvent(event.getGraphNode()));
		});
		
		
		add(panel, BorderLayout.CENTER);
		
		
		sidebar.revalidate();
		sidebar.repaint();

				
	}
	
	
}



