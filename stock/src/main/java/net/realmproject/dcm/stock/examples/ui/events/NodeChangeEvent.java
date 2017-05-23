package net.realmproject.dcm.stock.examples.ui.events;

import net.realmproject.dcm.stock.examples.ui.graph.GraphNode;

public class NodeChangeEvent {
	private GraphNode graphNode;

	public NodeChangeEvent(GraphNode graphNode) {
		this.graphNode = graphNode;
	}

	public GraphNode getGraphNode() {
		return graphNode;
	}

}
