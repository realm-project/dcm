package net.realmproject.dcm.stock.examples.ide.events;

import net.realmproject.dcm.stock.examples.ide.graph.GraphNode;

public class NodeSelectionEvent {

	private GraphNode graphNode;
	
	public NodeSelectionEvent(GraphNode graphNode) {
		this.graphNode = graphNode;
	}

	public GraphNode getGraphNode() {
		return graphNode;
	}
	
	
	
	
	
}
