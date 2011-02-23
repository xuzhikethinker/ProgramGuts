
import java.util.Vector;
/*
 import com.sun.jdi.Bootstrap;
 import com.sun.jdi.ReferenceType;
 import com.sun.jdi.VirtualMachine;
 import com.sun.jdi.VirtualMachineManager;
 import com.sun.jdi.connect.AttachingConnector;
 import com.sun.jdi.connect.Connector;
 import com.sun.jdi.connect.Transport;

 import com.sun.jdi.*;
 import com.sun.jdi.connect.*;
 import com.sun.jdi.connect.IllegalConnectorArgumentsException;

 import java.io.DataInputStream;
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;

import java.lang.reflect.Constructor;

import java.lang.reflect.Method;
*/
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
		VMtest.main(args);

		// makeNodes();

		// AddObjectNode(getClassName()); -> use only if we can get a stack
		// trace for Interesting.Java

	}

	/* works for only current thread a/k/a this running program */

	/*
	 * public String getClassName() {
	 * 
	 * String className = new
	 * Exception().getStackTrace()[VectorCount].getClassName().toString();
	 * 
	 * //progGraph.add(VectorCount,(Node) className);
	 * 
	 * VectorCount = VectorCount++;
	 * 
	 * //System.out.println (new Exception().getStackTrace()[0].getClassName());
	 * 
	 * return className; }
	 * 
	 * public String getMethodName() {
	 * 
	 * String MethodName = new
	 * Exception().getStackTrace()[VectorCount].getMethodName().toString();
	 * 
	 * //progGraph.add(VectorCount,(Node) className);
	 * 
	 * VectorCount = VectorCount++;
	 * 
	 * //System.out.println (new Exception().getStackTrace()[0].getClassName());
	 * 
	 * return MethodName; }
	 */

	public void makeNodes() {

		/*
		 * old rev //Obtain the Class instance Class interestingClass =
		 * Interesting.class; Class listClass = List.class; Class bstClass =
		 * BST.class; Class leafClass = Leaf.class;
		 * 
		 * //Get the methods Method[] methods1 =
		 * interestingClass.getDeclaredMethods(); Method[] methods2 =
		 * listClass.getDeclaredMethods(); Method[] methods3 =
		 * bstClass.getDeclaredMethods(); Method[] methods4 =
		 * leafClass.getDeclaredMethods();
		 * 
		 * Constructor[] cons1 = interestingClass.getDeclaredConstructors();
		 * Constructor[] cons2 = listClass.getDeclaredConstructors();
		 * Constructor[] cons3 = bstClass.getDeclaredConstructors();
		 * Constructor[] cons4 = leafClass.getDeclaredConstructors();
		 * 
		 * FunctionNode f = new FunctionNode();
		 * 
		 * 
		 * for (Constructor constructor : cons1) { //ObjectNode objcon1 = new
		 * ObjectNode(constructor.getName()); -> tried to make and objectnode
		 * that //would help make the connection commented out below
		 * 
		 * AddObjectNode(constructor.getName());
		 * 
		 * 
		 * for (Method method : methods1) {
		 * 
		 * if(method == methods1[0]){ AddFunctionNode(f,method.getName());
		 * 
		 * //AddFunctionToObjectNodeConnection(f, objcon1);
		 * 
		 * } else {
		 * AddFunctionNode((FunctionNode)progGraph.lastElement(),method.
		 * getName()); } }
		 * 
		 * }
		 * 
		 * //Nested loop through the constructors (then methods) and puts the in
		 * constructors in object nodes // and the methods in function nodes for
		 * (Constructor constructor : cons2) {
		 * 
		 * AddObjectNode(constructor.getName());
		 * 
		 * 
		 * 
		 * // Loop through methods and puts them in function nodes for (Method
		 * method : methods2) {
		 * 
		 * 
		 * AddFunctionNode((FunctionNode)progGraph.lastElement(),method.getName()
		 * );
		 * 
		 * }
		 * 
		 * }
		 * 
		 * for (Constructor constructor : cons3) {
		 * 
		 * AddObjectNode(constructor.getName());
		 * 
		 * 
		 * for (Method method : methods3) {
		 * 
		 * 
		 * AddFunctionNode((FunctionNode)progGraph.lastElement(),method.getName()
		 * );
		 * 
		 * }
		 * 
		 * }
		 * 
		 * for (Constructor constructor : cons4) {
		 * 
		 * AddObjectNode(constructor.getName());
		 * 
		 * 
		 * for (Method method : methods4) {
		 * 
		 * 
		 * AddFunctionNode((FunctionNode)progGraph.lastElement(),method.getName()
		 * );
		 * 
		 * }
		 * 
		 * }
		 */

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
	public void AddObjectNodeConnection(ObjectNode from, ObjectNode to) {
		to.ObjectsConnectedTo.add(from);
		from.ObjectsConnectedTo.add(to);
	}

	/**
	 * can use this func to add more function nodes to the graph
	 */
	public void AddFunctionNode(FunctionNode calledFrom, String name) {
		FunctionNode newFunction = new FunctionNode(calledFrom, name,
				calledFrom.position + 1);

		progGraph.add(newFunction);
	}

	/**
	 * creates a connection (edge) b/w a function node and Object node
	 * 
	 * @param from
	 * @param to
	 */
	public void AddFunctionToObjectNodeConnection(FunctionNode from,
			ObjectNode to) {
		from.ObjectsConnectedTo.add(to);
	}
}
