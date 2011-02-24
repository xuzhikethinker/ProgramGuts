import java.util.Vector; 
import com.sun.jdi.IncompatibleThreadStateException;

/**
 * This is where the debugging and graph builder guys will put all of their
 * code.
 * 
 * Can make more classes but have everything return to here.
 * 
 * Goal for first revision is to be able to get into that programs' memory and
 * construct a graph to pass via getGraph function
 * 
 * Functions should be represented by a rectangles and objects are circles
 * 
 */
public class BuilderDebugger {

	Vector<Node> progGraph;

	// int VectorCount = 0; -> only need if we can call stack trace on an
	// external program

	public BuilderDebugger() {
		progGraph = new Vector<Node>();

		// starts node list with main FunctionNode
		// progGraph.add( new FunctionNode() );
	}

	/**
	 * entrance point into debugger part from main class
	 * 
	 * @throws IncompatibleThreadStateException
	 */
	public void DebugProgram() throws IncompatibleThreadStateException {

		/*
		 * makes call to VMtest main
		 */
		String[] args = { "java", "../tools.jar:." };
		progGraph = VMtest.main(args);
		//progGraph = VMtest.nodeMain(args);

		// makeNodes();

		// AddObjectNode(getClassName()); -> use only if we can get a stack
		// trace for Interesting.Java

	}

	/**
	 * returns graph to main when called to pass to drawing part
	 */
	public Vector<Node> getGraph() {
		return progGraph;
	}

	/**
	 * can use this func to add more objects nodes to the graph
	 * 
	 * @param name
	 */
	public void AddObjectNode(String name) {
		ObjectNode newObject = new ObjectNode(name);

		progGraph.add(newObject);
	}

	/**
	 * creates a connection(edge) b/w Object nodes
	 * 
	 * @param from
	 * @param to
	 */
	public void AddObjectNodeConnection(ObjectNode from, ObjectNode to, String edgeName) {
		//to.ObjectsConnectedTo.add(from);
		from.ObjectsConnectedTo.put(edgeName, to);
	}

	/**
	 * can use this func to add more function nodes to the graph
	 */
	public void AddFunctionNode(FunctionNode calledFrom, String name) {
		FunctionNode newFunction = new FunctionNode(calledFrom, name,
				calledFrom.stackPosition + 1);

		progGraph.add(newFunction);
	}

	/**
	 * creates a connection (edge) b/w a function node and Object node
	 * 
	 * @param from
	 * @param to
	 */
	public void AddFunctionToObjectNodeConnection(FunctionNode from,
			ObjectNode to, String edgeName) {
		from.ObjectsConnectedTo.put(edgeName, to);
	}
	
	public static int findPrevFuncNode( Vector<Node> g ) {
		for ( int i = g.size()-1; i >= 0; i-- )
		{
			if ( g.get(i).nodeType == "function" )
			{
				return i;
			}
		}
		return -1;
	}
}
