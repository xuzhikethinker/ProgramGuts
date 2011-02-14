import java.util.Vector;

import javax.swing.JFrame;


public class ProgramGutsMain {
	
	static BuilderDebugger bd;
	static GraphViewer gv;

	public static void main(String args[]) {
		/* set up frame */
		JFrame f = new JFrame("VisiGuts");
		
		f.setSize(800, 600);
        f.setVisible(true);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		bd = new BuilderDebugger();
		
		//builder/debugger part
		bd.DebugProgram();
		
		// graph viewer part
		//gv = new GraphViewer( bd.getGraph() );
		
		// test graph to draw
		gv = new GraphViewer(sampleGraph());
		
		/* add graph viewer to the frame */ 
		f.add(gv);
		gv.repaint();
		

	}
	
	public static Vector<Node> sampleGraph() {
		Vector<Node> graph = new Vector<Node>();
		
		graph.add(new FunctionNode());
		graph.add( new FunctionNode((FunctionNode)graph.lastElement(), "testFunction1") );
		graph.add( new FunctionNode((FunctionNode)graph.lastElement(), "testFunction2") );
		graph.add( new FunctionNode((FunctionNode)graph.lastElement(), "testFunction3") );
		graph.add( new FunctionNode((FunctionNode)graph.lastElement(), "testFunction4") );
		graph.add(new ObjectNode());
		graph.add(new ObjectNode());
		graph.add(new ObjectNode());
	
		
		
		
		return graph;
	}
}
