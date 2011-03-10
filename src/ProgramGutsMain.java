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
	static Graphics graphics;
	static int functionCount = 0;

	public static void main(String args[])
			throws IncompatibleThreadStateException, IOException {

		/* Set up frame and menu */
		JFrame f = new JFrame("VisiGuts");
		
		/* Add menu to frame */
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem close = new JMenuItem("Close");
		close.addActionListener(new CloseWindow());
		mb.add(menu);
		menu.add(save);
		menu.add(close);
		
		/*set up frame */
		f.setJMenuBar(mb);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bd = new BuilderDebugger();

		// builder/debugger part
		// now can run VMtest.main to get threads and stack frame info
		bd.DebugProgram();

		final Vector<Node> graph = bd.getGraph();

		/* Set up graph for JUNG */
		final Graph<Integer, String> graphJUNG = graphics.convertToJUNGGraph(graph);

		/* initialize layout */
		ISOMLayout<Integer, String> layout = new ISOMLayout(graphJUNG);
		layout.setSize(new Dimension(800, 600));

		
		VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(layout);
		vv.setPreferredSize(new Dimension(1200, 800));
		
		graphics.formatGraph(vv,graph,graphJUNG);

		/* Menu item listener for saving the Visualization Viewer as a jpg */
		save.addActionListener(new SaveGraph(vv, f));

		/* JUNG Mouse configuration */
		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		gm.setMode(ModalGraphMouse.Mode.PICKING);
		vv.setGraphMouse(gm);

		f.getContentPane().add(vv);
		f.pack();

	}
}


