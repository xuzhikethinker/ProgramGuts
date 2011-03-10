import java.awt.Color;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;


public class Graphics {
	
	VisualizationViewer<Integer, String> vv;
	Vector<Node> graph;
	static Graph<Integer, String> graphJUNG;

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
			// for ( int i = 0; i < n.ObjectsConnectedTo.size(); i++ )
			Set<String> keys = n.ObjectsConnectedTo.keySet();
			for (String s : keys) {
				String edgeName = "edge" + edgeCount;
				graphJUNG.addEdge(edgeName,
						n.ObjectsConnectedTo.get(s).vertexID, n.vertexID);
				// graphJUNG.addEdge(edgeName,
				// n.ObjectsConnectedTo.get(i).vertexID, n.vertexID);
				edgeCount++;
			}
		}
		return graphJUNG;
	}
	
	
    /*
	 * Set transformers in the visualization viewer for proper JUNG
	 * formatting
	 */
	
	static void formatGraph(VisualizationViewer<Integer, String> vv, final Vector<Node> graph, final Graph<Integer, String> graphJUNG ){
		
		/*
		 * Set transformers in the visualization viewer for proper JUNG
		 * formatting
		 */

		/* Render Colors for Object and Function Vertex */
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

		/* Render Shape for Object and Function Vertex */
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

		/* Render Edge Label Edges */
		vv.getRenderContext().setEdgeLabelTransformer(
				new Transformer<String, String>() {

					public String transform(String edgeName) {
						if (graph.get(graphJUNG.getEndpoints(edgeName)
								.getSecond()).nodeType.equals("object"))
							return graph.get(graphJUNG.getEndpoints(edgeName)
									.getSecond()).name;
						else
							return "";
					}
				});
		/* Render Vertex Labels */
		vv.getRenderContext().setVertexLabelTransformer(
				new Transformer<Integer, String>() {

					public String transform(Integer i) {
						if (graph.get(i).nodeType.equals("function")) {
							return graph.get(i).name;
						} else {
							return graph.get(i).value;

						}
					}
				});
	}
	
	
}

