import java.util.Vector;

import javax.swing.JFrame;


public class ProgramGutsMain {
	
	static BuilderDebugger bd;
	static GraphViewer gv;
	static int functionCount = 0;

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
		FunctionNode testFunction1 = new FunctionNode((FunctionNode)graph.lastElement(), "testFunction1", 1);
		graph.add(testFunction1);
		graph.add( new FunctionNode((FunctionNode)graph.lastElement(), "testFunction2", 2) );
		graph.add( new FunctionNode((FunctionNode)graph.lastElement(), "testFunction3", 3) );
		graph.add( new FunctionNode((FunctionNode)graph.lastElement(), "testFunction4", 4) );
		
		
		ObjectNode testObject1 = new ObjectNode("Object 1");
		ObjectNode testObject2 = new ObjectNode("Object 2");
		ObjectNode testObject3 = new ObjectNode("Object 3");
		
		graph.add(testObject1);
		graph.add(testObject2);
		graph.add(testObject3);

		testObject1.addConnection(testObject2);
		testObject1.ObjectsConnectedTo.add(testObject3);
		testObject2.ObjectsConnectedTo.add(testFunction1);
		
	
		
		
		
		return graph;
	}
}
