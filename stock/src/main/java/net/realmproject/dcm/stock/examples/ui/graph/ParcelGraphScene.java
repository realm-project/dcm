package net.realmproject.dcm.stock.examples.ui.graph;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.action.WidgetAction.State;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseEvent;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

import net.realmproject.dcm.stock.examples.ui.graph.actions.ClickAction;


public class ParcelGraphScene extends GraphScene<GraphNode, String> {

	private LayerWidget mainLayer;
	private LayerWidget connectionLayer;
	private LayerWidget interactionLayer;
	
	private ParcelGraph graph;
	

	public ParcelGraphScene(ParcelGraph graph) {
		this.graph = graph;
		
		mainLayer = new LayerWidget(this);
		connectionLayer = new LayerWidget(this);
		interactionLayer = new LayerWidget(this);
		addChild(mainLayer);
		addChild(connectionLayer);
		addChild(interactionLayer);

		getActions().addAction(ActionFactory.createZoomAction());
	}


	
	@Override
	protected Widget attachNodeWidget(GraphNode node) {
		IconNodeWidget widget = new IconNodeWidget(this);//

		
		widget.getActions().addAction(ActionFactory.createExtendedConnectAction(connectionLayer, new MyConnectProvider(graph)));


		
		widget.getActions().addAction(ActionFactory.createAlignWithMoveAction(mainLayer, interactionLayer,
				ActionFactory.createDefaultAlignWithMoveDecorator()));

		widget.setImage(node.getImage());
		widget.setLabel(node.getNode().getId());
		mainLayer.addChild(widget);
		return widget;
	}


	@Override
	protected void attachEdgeSourceAnchor(String arg0, GraphNode arg1, GraphNode arg2) {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	protected void attachEdgeTargetAnchor(String arg0, GraphNode arg1, GraphNode arg2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected Widget attachEdgeWidget(String arg0) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	
	private class MyConnectProvider implements ConnectProvider {

		private ParcelGraph graph;
		
		public MyConnectProvider(ParcelGraph graph) {
			this.graph = graph;
		}
		
		public boolean isSourceWidget(Widget source) {
			return source instanceof IconNodeWidget && source != null ? true : false;
		}

		public ConnectorState isTargetWidget(Widget src, Widget trg) {
			return src != trg && trg instanceof IconNodeWidget ? ConnectorState.ACCEPT : ConnectorState.REJECT;
		}

		public boolean hasCustomTargetWidgetResolver(Scene arg0) {
			return false;
		}

		public Widget resolveTargetWidget(Scene arg0, Point arg1) {
			return null;
		}

		@Override
		public void createConnection(Widget source, Widget target) {
			boolean accepted = graph.addLink(source, target);
			if (!accepted) { return; }
			
			ConnectionWidget conn = new ConnectionWidget(ParcelGraphScene.this);
			conn.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
			conn.setTargetAnchor(AnchorFactory.createRectangularAnchor(target));
			conn.setSourceAnchor(AnchorFactory.createRectangularAnchor(source));

			conn.getActions().addAction(new ClickAction((widget, event) -> {
				if (event.isControlDown()) {
					if (graph.removeLink(source, target)) {
						connectionLayer.removeChild(conn);
					}
					return State.CONSUMED;
				}
				return State.REJECTED;
			}));
			
			connectionLayer.addChild(conn);
			
		}

	}


}
