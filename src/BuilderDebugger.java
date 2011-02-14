import java.util.Vector;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;


/*
 * This is where the debugging and graph builder guys will put 
 * all of their code.
 * 
 * Can make more classes but have everything return to here.
 * 
 * Goal for first revision is to be able to get into that programs'
 * memory and construct a graph to pass via getGraph function
 * 
 * Functions should be represented by a rectangles and objects are circles
 * 
 */

public class BuilderDebugger {

	Vector<Node> progGraph;
	
	public BuilderDebugger() {
		progGraph = new Vector<Node>();
		
		// starts node list with main FunctionNode
		progGraph.add( new FunctionNode() );
	}
	
	// entrance point from main class
	public void DebugProgram() {
		
	}
	
	// returns graph to main when called to pass to drawing part
	public Vector<Node> getGraph()
	{
		return progGraph;
	}
}
