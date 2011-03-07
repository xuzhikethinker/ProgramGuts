import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.commons.collections15.Transformer;

import com.sun.jdi.IncompatibleThreadStateException;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class ProgramGutsMain {

	static BuilderDebugger bd;
	static int functionCount = 0;

	public static void main(String args[])
			throws IncompatibleThreadStateException, IOException {
		Graph<Integer, String> graphJUNG = new DirectedSparseGraph<Integer, String>();
		//final Vector<Node> graph = sampleGraph();

		/* Set up frame and menu*/
		JFrame f = new JFrame("VisiGuts");
                JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem close = new JMenuItem("Close");
		close.addActionListener(new CloseWindow());
		mb.add(menu);
		menu.add(save);
		menu.add(close);
		f.setJMenuBar(mb);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bd = new BuilderDebugger();

		// builder/debugger part
		// now can run VMtest.main to get threads and stack frame info
		bd.DebugProgram();
		
		final Vector<Node> graph = bd.getGraph();

		// graph viewer part
		// gv = new GraphViewer( bd.getGraph() );

		/* Set up graph for JUNG */
		// graphJUNG = convertToJUNGGraph(graph);
		graphJUNG = convertToJUNGGraph(graph);

		ISOMLayout<Integer, String> layout = new ISOMLayout(graphJUNG);
		layout.setSize(new Dimension(800, 600));

		VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(
				layout);
		vv.setPreferredSize(new Dimension(1200, 800));
                
                /* Menu item listener for saving the Visualization Viewer as a jpg */
                save.addActionListener(new SaveGraph(vv, f));

		/*
		 * Set transformers in the visualization viewer for proper JUNG
		 * formatting
		 */
		vv.getRenderContext().setVertexLabelTransformer(
				new Transformer<Integer, String>() {

					public String transform(Integer i) {
						return graph.get(i).name;
					}
				});
		vv.getRenderContext().setVertexFillPaintTransformer(
				new Transformer<Integer, Paint>() {

					private final Color[] palette = { Color.GREEN, Color.WHITE,
							Color.RED };

					public Paint transform(Integer i) {
						if (graph.get(i).nodeType.equals("object")) {
							return palette[0];
						} else {
							return palette[1];
						}
					}
				});
		vv.getRenderContext().setVertexShapeTransformer(
				new Transformer<Integer, Shape>() {
					private final Shape[] styles = {
							new Rectangle(-20, -10, 40, 20),
							new Ellipse2D.Double(-25, -10, 50, 20) };

					@Override
					public Shape transform(Integer i) {
						if (graph.get(i).nodeType.equals("function")) {
							return styles[0];
						} else {
							return styles[1];
						}
					}
				});

		/* Make final node positional adjustments */
		// <editor-fold>
		for (Node n : graph) {

			/* stack function nodes in the top left corner */
			if (n.nodeType.equals("function")) {
				layout.setLocation(n.vertexID, new Point2D.Double((double) 25,
						(double) (n.stackPosition * 50 + 20)));
			}

			/* offset object nodes to avoid overlapping with stack nodes */
			if (n.nodeType.equals("object")) {
				layout.setLocation(n.vertexID, new Point2D.Double(layout
						.getX(n.vertexID) + 200, layout.getY(n.vertexID) + 50));
			}
		}
		// </editor-fold>

		/* JUNG Mouse configuration */
		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		gm.setMode(ModalGraphMouse.Mode.PICKING);
		vv.setGraphMouse(gm);

		f.getContentPane().add(vv);
		f.pack();

	}

	public static Vector<Node> sampleGraph() {
		Vector<Node> graph = new Vector<Node>();

		Random r = new Random();
		//FunctionNode testFunction1 = new FunctionNode();
		//graph.add(testFunction1);
		graph.add(new FunctionNode((FunctionNode) graph.lastElement(),
				"testFunction1", String.valueOf(r.nextInt()), 1));
		graph.add(new FunctionNode((FunctionNode) graph.lastElement(),
				"testFunction2", String.valueOf(r.nextInt()), 2));
		// graph.add( new FunctionNode((FunctionNode)graph.lastElement(),
		// "testFunction4", 4) );
		FunctionNode testFunction4 = new FunctionNode((FunctionNode) graph
				.lastElement(), "testFunction3",String.valueOf(r.nextInt()), 3);
		graph.add(testFunction4);
		Vector<ObjectNode> objectNodeCollection = new Vector<ObjectNode>();

		for (int i = 0; i < 20; i++) {
			String name = "Object " + i;
			objectNodeCollection.add(new ObjectNode(name, String.valueOf(r.nextInt())));
			graph.add(objectNodeCollection.get(i));
		}

		//Random r = new Random();
		for (int i = 0; i < 20; i++) {
			int rand = r.nextInt(20);
			objectNodeCollection.get(i).addConnection(objectNodeCollection.get(rand));
			rand = r.nextInt(20);
			objectNodeCollection.get(i).addConnection(objectNodeCollection.get(rand));

		}
		//testFunction1.addConnection(objectNodeCollection.get(0));

		return graph;

	}

	/**
	 * Converts a node graph to a JUNG formatted graph
	 * 
	 * @param graph
	 * @return
	 */
	public static Graph convertToJUNGGraph(Vector<Node> graph) {

		Graph<Integer, String> graphJUNG = new DirectedOrderedSparseMultigraph<Integer, String>();
		int edgeCount = 0; /*
							 * monitors the number of edges being added to the
							 * graphJUNG graph
							 */

		/* add each node from the graph to the JUNGgraph */
		for (int i = 0; i < graph.size(); i++) {
			graph.get(i).vertexID = i; /*
										 * give each node from the graph a
										 * vertex ID
										 */
			graphJUNG.addVertex(i);
		}

		/* add all connections from the graph to graphJUNG as edges */
		for (Node n : graph) {
			for ( Node o : n.ObjectsConnectedTo ) {
				String edgeName = "edge" + edgeCount;
				graphJUNG.addEdge(edgeName, n.vertexID, o.vertexID);
				edgeCount++;
			}
		}

		return graphJUNG;
	}
}