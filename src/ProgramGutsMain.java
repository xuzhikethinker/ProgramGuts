import java.io.IOException;
import java.util.Vector;

import javax.swing.JFrame;

/**
 * main controller for ProgramGuts program
 * @author afavia.student
 *
 */
public class ProgramGutsMain {
	
	static BuilderDebugger bd;
	static GraphViewer gv;

	/**
	 * main function
	 */
	public static void main(String args[]) {
		/* set up frame */
		JFrame f = new JFrame("VisiGuts");
		
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		bd = new BuilderDebugger();
		
		// execute test program from within this program
		try {
			Runtime.getRuntime().exec("java Interesting.java");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//builder/debugger part
		bd.DebugProgram();
		
		// graph viewer part
		//gv = new GraphViewer( bd.getGraph() );
		
		// test graph to draw
		gv = new GraphViewer(sampleGraph());
		
		/* add graph viewer to the frame */ 
		f.add(gv);
		f.setSize(800, 600);
        f.setVisible(true);
        f.pack();
		gv.repaint();
	}
	
	/**
	 * Test function that generates a test graph 
	 * passed to the GraphViewer class.
	 * 
	 * @return Vector<Node>
	 */
	public static Vector<Node> sampleGraph() {
		Vector<Node> graph = new Vector<Node>();
		
		graph.add(new FunctionNode());
		
		FunctionNode F1 = new FunctionNode((FunctionNode)graph.lastElement(), "testFunction1");
		FunctionNode F2 = new FunctionNode((FunctionNode)graph.lastElement(), "testFunction2");
		FunctionNode F3 = new FunctionNode((FunctionNode)graph.lastElement(), "testFunction3");
		FunctionNode F4 = new FunctionNode((FunctionNode)graph.lastElement(), "testFunction4");
		ObjectNode O1 = new ObjectNode("obj1");
		ObjectNode O2 = new ObjectNode("obj2");
		ObjectNode O3 = new ObjectNode("obj3");
		graph.add( F1 );
		graph.add( F2 );
		graph.add( F3 );
		graph.add( F4 );
		graph.add( O1 );
		graph.add( O2 );
		graph.add( O3 );
		
		O1.AddConnection(O2);
		O1.AddConnection(O3);
		F1.AddConnectionToObject(O1);
		F2.AddConnectionToObject(O2);
		F4.AddConnectionToObject(O3);
	
		return graph;
	}
}
