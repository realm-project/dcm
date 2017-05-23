package net.realmproject.dcm.stock.examples.ui.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.netbeans.api.visual.action.WidgetAction.State;
import org.netbeans.api.visual.widget.Widget;

import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.core.linkable.ListLinkStart;
import net.realmproject.dcm.parcel.core.linkable.NamedLinkStart;
import net.realmproject.dcm.parcel.core.linkable.SingleLinkStart;
import net.realmproject.dcm.parcel.impl.flow.hub.IParcelHub;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.stock.examples.ui.ParcelUI;
import net.realmproject.dcm.stock.examples.ui.events.NodeSelectionEvent;
import net.realmproject.dcm.stock.examples.ui.graph.actions.ClickAction;

public class ParcelGraph {

	private List<GraphNode> nodes = new ArrayList<>();
	private ParcelGraphScene scene;
	
	private ParcelUI parent;
	
	
	
	public ParcelGraph(ParcelUI parent) {
		this.parent = parent;
		scene = new ParcelGraphScene(this);
	}
	
	public void addNode(GraphNode node) {
		nodes.add(node);
		Widget w = scene.addNode(node);
		w.setPreferredLocation(new Point(10, 100));
		node.setWidget(w);
		scene.validate();
		
		w.getActions().addAction(new ClickAction((widget, event) -> {
			select(nodeForWidget(widget));
			return State.CONSUMED;
		}));
		
	}
	
	private void select(GraphNode gn) {
		parent.getEventHub().receive(new IParcel<>().payload(new NodeSelectionEvent(gn)));
	}
	
	private GraphNode nodeForWidget(Widget w) {
		for (GraphNode gn : nodes) {
			if (gn.getWidget() == w){return gn;}
		}
		return null;
	}
	
	
	//Convenience method for addLink(GraphNodes1/2)
	public boolean addLink(Widget w1, Widget w2) {
		GraphNode gn1=nodeForWidget(w1), gn2=nodeForWidget(w2);
		if (gn1 == null || gn2 == null) { return false; }
		return addLink(gn1, gn2);
	}
	
	public boolean addLink(GraphNode gn1, GraphNode gn2) {
		ParcelNode node1 = gn1.getNode();
		ParcelNode node2 = gn2.getNode();
				
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
		GraphNode gn1=nodeForWidget(w1), gn2=nodeForWidget(w2);
		if (gn1 == null || gn2 == null) { return false; }
		return removeLink(gn1, gn2);
	}
	
	public boolean removeLink(GraphNode gn1, GraphNode gn2) {
		ParcelNode node1 = gn1.getNode();
		ParcelNode node2 = gn2.getNode();
		
		if (node1 instanceof ParcelSender && node2 instanceof ParcelReceiver) {
			ParcelSender sender = (ParcelSender) node1;
			ParcelReceiver receiver = (ParcelReceiver) node2;
			
			if (sender instanceof SingleLinkStart) {
				SingleLinkStart sl = (SingleLinkStart) sender;
				sl.unlink();
				return true;
			}
			
			if (sender instanceof ListLinkStart) {
				ListLinkStart sl = (ListLinkStart) sender;
				sl.unlink(receiver);
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

	
	public ParcelGraphScene getScene() {
		return scene;
	}


	
	
	
	
}
