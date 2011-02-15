import java.awt.Graphics;
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
	String name;
	
	public FunctionNode() {
		this.calledFrom = null; 
		name = "main";
		ObjectsConnectedTo = new Vector<ObjectNode>(); 
	}
	
	public FunctionNode(FunctionNode calledFrom, String name) {
		this.calledFrom = calledFrom; 
		this.name = name;
		ObjectsConnectedTo = new Vector<ObjectNode>(); 
	}
	
	public void AddConnectionToObject(ObjectNode connectedTo)
	{
		this.ObjectsConnectedTo.add(connectedTo);
	}
	
	public void drawNode(Graphics g){
        g.drawRect(0, 0, 100, 100); //draws node at origin
        //g.drawString(name, 0, 0);
    }
	
	
	
}
