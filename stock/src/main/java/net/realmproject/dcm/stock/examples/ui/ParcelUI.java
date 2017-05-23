package net.realmproject.dcm.stock.examples.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.function.Supplier;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.flow.filter.IParcelFilterLink;
import net.realmproject.dcm.parcel.impl.flow.filter.filters.PayloadClassFilter;
import net.realmproject.dcm.parcel.impl.flow.hub.IParcelHub;
import net.realmproject.dcm.parcel.impl.flow.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.flow.misc.IParcelBeacon;
import net.realmproject.dcm.parcel.impl.flow.misc.IParcelPrinter;
import net.realmproject.dcm.parcel.impl.flow.transform.IParcelTransformLink;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;
import net.realmproject.dcm.parcel.impl.receiver.IParcelConsumer;
import net.realmproject.dcm.stock.examples.ui.events.NodeSelectionEvent;
import net.realmproject.dcm.stock.examples.ui.graph.GraphNode;
import net.realmproject.dcm.stock.examples.ui.graph.ParcelGraph;
import net.realmproject.dcm.stock.examples.ui.graph.ParcelGraphScene;

public class ParcelUI extends JPanel {
	
	ParcelGraphScene scene;
	ParcelGraph graph;
	JToolBar toolbar;
	JLabel properties;
	
	ParcelHub eventHub = new IParcelHub();
	{
		eventHub.setCopying(false);
	}
	
    public ParcelUI() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
    
        graph = new ParcelGraph(this);
        
        //Get the scene:
        scene = graph.getScene();
        //Add it to the JScrollPane:
        scrollPane.setViewportView(scene.createView());
        //Add the SatellitView to the scene:
        add(scene.createSatelliteView(), BorderLayout.WEST);
        
        toolbar = new JToolBar();
        toolbar.setFloatable(false);
        add(toolbar, BorderLayout.NORTH);

        registerNodeType("generic", IParcelLink::new);
        registerNodeType("filter", IParcelFilterLink::new);
        registerNodeType("transform", IParcelTransformLink::new);
        registerNodeType("hub", IParcelHub::new);
        registerNodeType("beacon", IParcelBeacon::new);
        registerNodeType("output", IParcelPrinter::new);
        
        
        properties = new JLabel("Properties");
        properties.setMinimumSize(new Dimension(300, 100));
        add(properties, BorderLayout.EAST);
        
        
        
        getEventHub().filter(new PayloadClassFilter(NodeSelectionEvent.class)).link(new IParcelConsumer(parcel -> {
        	onNodeSelect((NodeSelectionEvent) parcel.getPayload());
        }));
        
    }
    
    private void onNodeSelect(NodeSelectionEvent event) {
    	updateProperties(event.getGraphNode());
    }
    
    private void updateProperties(GraphNode gn) {
    	System.out.println("node selected");
    }
    
    private void registerNodeType(String type, Supplier<IParcelNode> creator) {
        ImageIcon icon24 = new ImageIcon(ParcelUI.class.getResource("icons/24-black/" + type + ".png"));
        ImageIcon icon48 = new ImageIcon(ParcelUI.class.getResource("icons/48-black/" + type + ".png"));
    	JButton button = new JButton(icon24);
        button.addActionListener(e -> graph.addNode(new GraphNode(creator.get(), icon48.getImage())));
        toolbar.add(button);
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setMinimumSize(new Dimension(800, 600));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new ParcelUI());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
	
	public ParcelHub getEventHub() {
		return eventHub;
	}
    
}
