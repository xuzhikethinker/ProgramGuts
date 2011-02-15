import java.util.Vector;
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
import java.io.*;


/**
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
	
	//can use this func to add more objects nodes to the graph
	public void AddObjectNode()
	{
		ObjectNode newObject = new ObjectNode();
		
		progGraph.add(newObject);
	}
    
       //creates a connection(edge)  b/w Object nodes
	public void AddObjectNodeConnection(ObjectNode from, ObjectNode to)
	{
		to.ObjectsConnectedTo.add(from);
                from.ObjectsConnectedTo.add(to);
	}
	
	//can use this func to add more function nodes to the graph
	public void AddFunctionNode(FunctionNode calledFrom, String name)
	{
		FunctionNode newFunction = new FunctionNode(calledFrom, name);
		
		progGraph.add(newFunction);
	}

       //creates a connection (edge)  b/w a function node and Object node
	public void AddFunctionToObjectNodeConnection(FunctionNode from, ObjectNode to)
	{
                from.ObjectsConnectedTo.add(to);
	}
}
