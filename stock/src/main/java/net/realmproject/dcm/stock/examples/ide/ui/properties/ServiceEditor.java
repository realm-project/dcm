package net.realmproject.dcm.stock.examples.ide.ui.properties;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.Icon;
import javax.swing.UIManager;

import com.l2fprod.common.beans.editor.ComboBoxPropertyEditor;

import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.flow.filter.filters.PayloadClassFilter;
import net.realmproject.dcm.stock.examples.ide.events.NodeSetChangedEvent;
import net.realmproject.dcm.stock.examples.ide.ui.ParcelUI;

public class ServiceEditor extends ComboBoxPropertyEditor {
	
	
	public ServiceEditor() {
		super();
		
		System.out.println("construct");
		ParcelUI.INSTANCE.getEventHub().filter(new PayloadClassFilter(NodeSetChangedEvent.class)).link(p -> {
			update();
			System.out.println("update");
		});
		update();
		
//		setAvailableValues(new String[] { "Spring", "Summer", "Fall", "Winter", });
//		Icon[] icons = new Icon[4];
//		Arrays.fill(icons, UIManager.getIcon("Tree.openIcon"));
//		setAvailableIcons(icons);
	}
	
	private void update() {
		Object[] services = ParcelUI.INSTANCE.graph.getNodes()
				.stream()
				.map(gn -> gn.getNode())
				.filter(n -> n instanceof ParcelService)
				.collect(Collectors.toList())
				.toArray();
		setAvailableValues(services);
	}
	
}
