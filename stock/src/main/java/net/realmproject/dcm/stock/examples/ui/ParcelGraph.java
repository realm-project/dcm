package net.realmproject.dcm.stock.examples.ui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.netbeans.api.visual.widget.Widget;

import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.linkable.ListLinkStart;
import net.realmproject.dcm.parcel.core.linkable.ListLinkable;
import net.realmproject.dcm.parcel.core.linkable.NamedLinkStart;
import net.realmproject.dcm.parcel.core.linkable.NamedLinkable;
import net.realmproject.dcm.parcel.core.linkable.SingleLinkStart;
import net.realmproject.dcm.parcel.core.linkable.SingleLinkable;

public class ParcelGraph {

	private List<GraphNode> nodes = new ArrayList<>();
	private ParcelGraphScene scene;
	
	public ParcelGraph() {
		scene = new ParcelGraphScene(this);
	}
	
	public void addNode(GraphNode node) {
		nodes.add(node);
		Widget w = scene.addNode(node);
		w.setPreferredLocation(new Point(10, 100));
		node.setWidget(w);
		scene.validate();
	}
	
	
	//Convenience method for addLink(GraphNodes1/2)
	public boolean addLink(Widget w1, Widget w2) {
		
		GraphNode gn1=null, gn2=null;
		for (GraphNode gn : nodes) {
			if (gn.getWidget() == w1){gn1 = gn;}
			if (gn.getWidget() == w2){gn2 = gn;}
		}
		
		if (gn1 == null || gn2 == null) { return false; }
		return addLink(gn1, gn2);
	}
	
	public boolean addLink(GraphNode gn1, GraphNode gn2) {
		ParcelNode node1 = gn1.getNode();
		ParcelNode node2 = gn2.getNode();
		
		
		System.out.println(node1);
		System.out.println(node2);
				
		if (node1 instanceof ParcelSender && node2 instanceof ParcelReceiver) {
			ParcelSender sender = (ParcelSender) node1;
			ParcelReceiver receiver = (ParcelReceiver) node2;
			
			
			if (sender instanceof SingleLinkStart) {
				SingleLinkStart sl = (SingleLinkStart) sender;
				sl.link(receiver);
				return true;
			}
			
			if (sender instanceof ListLinkStart) {
				ListLinkStart sl = (ListLinkStart) sender;
				sl.link(receiver);
				return true;
			}
			
			if (sender instanceof NamedLinkStart) {
				NamedLinkStart sl = (NamedLinkStart) sender;
				//TODO: how to support named links?
				//sl.link(receiver);
				//return true;
			}
			
		}
		return false;
		
	}
	
	//Convenience method for addLink(GraphNodes1/2)
	public boolean removeLink(Widget w1, Widget w2) {
		GraphNode gn1=null, gn2=null;
		for (GraphNode gn : nodes) {
			if (gn.getWidget() == w1){gn1 = gn;}
			if (gn.getWidget() == w2){gn2 = gn;}
		}
		
		if (gn1 == null || gn2 == null) { return false; }
		return removeLink(gn1, gn2);
	}
	
	public boolean removeLink(GraphNode gn1, GraphNode gn2) {
		ParcelNode node1 = gn1.getNode();
		ParcelNode node2 = gn2.getNode();
		
		if (node1 instanceof ParcelSender && node2 instanceof ParcelReceiver) {
			ParcelSender sender = (ParcelSender) node1;
			ParcelReceiver receiver = (ParcelReceiver) node2;
			
			if (sender instanceof SingleLinkable) {
				SingleLinkable sl = (SingleLinkable) sender;
				sl.unlink();
				return true;
			}
			
			if (sender instanceof ListLinkable) {
				ListLinkable sl = (ListLinkable) sender;
				sl.unlink(receiver);
				return true;
			}
			
			if (sender instanceof NamedLinkable) {
				NamedLinkable sl = (NamedLinkable) sender;
				//TODO: how to support named links?
				//sl.link(receiver);
				//return true;
			}
			
		}
		return false;
	}

	
	public ParcelGraphScene getScene() {
		return scene;
	}
	
	
	
}
