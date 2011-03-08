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


        /* Set up graph for JUNG */
        final Graph<Integer, String> graphJUNG = convertToJUNGGraph(graph);
       

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
                    if(graph.get(graphJUNG.getEndpoints(edgeName).getSecond()).nodeType.equals("object"))
                        return graph.get(graphJUNG.getEndpoints(edgeName).getSecond()).name;
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
       
       


       

        /* JUNG Mouse configuration */
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(gm);

        f.getContentPane().add(vv);
        f.pack();

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
                graphJUNG.addEdge(edgeName, o.vertexID, n.vertexID);
                edgeCount++;
            }
        }

        return graphJUNG;
    }
}