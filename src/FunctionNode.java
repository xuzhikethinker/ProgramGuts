import java.util.Vector;

/*
 * The FunctionNode class represents a node representing a function
 * 
 * The calledFrom is a pointer to the function that this 
 * function was called from. (previous func in call stack)
 * 
 * Each FunctionNode has a list(vector) of the ObjectNodes it is
 * connected to.
 * 
 */

public class FunctionNode extends Node {

	FunctionNode calledFrom;
	Vector<ObjectNode> ObjectsConnectedTo;
	
	public FunctionNode() {
		this.calledFrom = null; 
		ObjectsConnectedTo = new Vector<ObjectNode>(); 
	}
	
	public FunctionNode(FunctionNode calledFrom) {
		this.calledFrom = calledFrom; 
		ObjectsConnectedTo = new Vector<ObjectNode>(); 
	}
	
	
	
}
