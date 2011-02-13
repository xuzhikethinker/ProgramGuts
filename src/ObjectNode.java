import java.util.Vector;

/*
 * The ObjectNode class represents a node representing an object
 * 
 * Each ObjectNode has a list(vector) of the ObjectNodes it is
 * connected to.
 * 
 */

public class ObjectNode extends Node {

	Vector<ObjectNode> ObjectsConnectedTo;
	
	public ObjectNode() {
		ObjectsConnectedTo = new Vector<ObjectNode>(); 
	}

	public void AddConnection(ObjectNode connectedTo)
	{
		this.ObjectsConnectedTo.add(connectedTo);
	}
}
