package net.realmproject.dcm.stock.examples.ide.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.util.function.Supplier;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.flow.filter.IParcelFilterLink;
import net.realmproject.dcm.parcel.impl.flow.filter.filters.PayloadClassFilter;
import net.realmproject.dcm.parcel.impl.flow.hub.IParcelHub;
import net.realmproject.dcm.parcel.impl.flow.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.flow.misc.IParcelBeacon;
import net.realmproject.dcm.parcel.impl.flow.misc.IParcelPrinter;
import net.realmproject.dcm.parcel.impl.flow.transform.IParcelTransformLink;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.parcel.impl.receiver.IParcelConsumer;
import net.realmproject.dcm.stock.examples.ide.NodeUtil;
import net.realmproject.dcm.stock.examples.ide.events.NodeChangeEvent;
import net.realmproject.dcm.stock.examples.ide.events.NodeSelectionEvent;
import net.realmproject.dcm.stock.examples.ide.graph.GraphNode;
import net.realmproject.dcm.stock.examples.ide.graph.ParcelGraph;
import net.realmproject.dcm.stock.examples.ide.graph.ParcelGraphScene;
import net.realmproject.dcm.stock.examples.ide.ui.properties.PropertiesPanel;

public class ParcelUI extends JPanel {
	
	public static ParcelUI INSTANCE = null;
	
	ParcelGraphScene scene;
	public ParcelGraph graph;
	JTextField id;
	
	Sidebar sidebar;
	

	GraphNode selectedNode = null;
	
	ParcelHub eventHub = new IParcelHub();
	{
		eventHub.setCopying(false);
	}
	
    public ParcelUI() {
        initComponents();
        INSTANCE = this;
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        
        
        

        sidebar = new Sidebar();
        JScrollPane sidescroll = new JScrollPane(sidebar);
        sidescroll.getVerticalScrollBar().setUnitIncrement(10);
        sidescroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(sidescroll, BorderLayout.WEST);
        
        JScrollPane scrollPane = new JScrollPane();        
        add(scrollPane, BorderLayout.CENTER);
        
        NodesPanel nodePanel = new NodesPanel(this);
        registerPanel("Components", makeScrolledPanel(nodePanel, 400));
        
        graph = new ParcelGraph(this);
        
        //Get the scene:
        scene = graph.getScene();
        //Add it to the JScrollPane:
        scrollPane.setViewportView(scene.createView());
        //Add the SatellitView to the scene:
        //sidebar.add(scene.createSatelliteView());
        registerPanel("GraphOverview", scene.createSatelliteView());
       
        
        id = new JTextField("                    ");
        id.addActionListener(e -> {
        	if (selectedNode != null) {
        		selectedNode.getNode().setId(id.getText());
        		getEventHub().receive(new NodeChangeEvent(selectedNode));
        	}
        });
        //id.setMinimumSize(new Dimension(200, 0));
        PropertiesPanel properties = new PropertiesPanel(this, sidebar);
        registerPanel("Node Properties", properties);
        
        getEventHub().filter(new PayloadClassFilter(NodeSelectionEvent.class)).link(new IParcelConsumer(parcel -> {
        	onNodeSelect((NodeSelectionEvent) parcel.getPayload());
        }));
        
        sidebar.finalizeSidebar();
        
    }
    
    private void onNodeSelect(NodeSelectionEvent event) {
    	selectedNode = event.getGraphNode();
    }
    
    public void addNodeFromClass(Class<? extends ParcelNode> nodeClass) {
    	try {
        	String type = NodeUtil.getType(nodeClass).toString().toLowerCase();
        	ImageIcon icon48 = new ImageIcon(ParcelUI.class.getResource("icons/48-black/" + type + ".png"));
			GraphNode gn = new GraphNode(nodeClass.newInstance(), icon48.getImage());
			graph.addNode(gn);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
    }

    private ScrollSizedPanel makeScrolledPanel(JComponent component, int height) {
    	//first put the component in a scrollpane
    	JScrollPane scroll = new JScrollPane(component);
    	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scroll.getVerticalScrollBar().setUnitIncrement(10);
    	
    	//then wrap the scrollpane in a panel to limit the height
    	ScrollSizedPanel panel = new ScrollSizedPanel(scroll, height);
    	
    	return panel;
    }
    
    private void registerPanel(String title, JComponent component) {

    	Expander exp = new Expander(title, component);
    	//sidebar.add(scroll, sidebarConstraints);
    	sidebar.addSidebarPanel(exp);
    }
    
    public static void main(String args[]) {
    	
//    	try {
//    	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//    	        if ("Nimbus".equals(info.getName())) {
//    	            UIManager.setLookAndFeel(info.getClassName());
//    	            break;
//    	        }
//    	    }
//    	} catch (Exception e) {
//    	    // If Nimbus is not available, you can set the GUI to another look and feel.
//    	}
    	
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setMinimumSize(new Dimension(1024, 768));
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
