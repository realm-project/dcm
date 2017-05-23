package net.realmproject.dcm.stock.examples.ui.graph;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.ImageUtilities;

import net.realmproject.dcm.parcel.core.ParcelNode;

public class GraphNode {

	private ParcelNode node;
	private Image image;
	private Widget widget;
	
	public GraphNode(ParcelNode node, Image image) {
		this.node = node;
		this.image = image;
	}

	public ParcelNode getNode() {
		return node;
	}

	public void setNode(ParcelNode node) {
		this.node = node;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}
	
	
	
	
	
	
	
	
}
