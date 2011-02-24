/**
 * The ObjectNode class represents a node representing an object
 * 
 * Each ObjectNode has a list(vector) of the ObjectNodes it is connected to.
 * 
 */

public class ObjectNode extends Node {

	String name;

	/**
	 * constructor
	 */
	ObjectNode() {
		super.nodeType = "object";
		super.stackPosition = -1;
	}

	/**
	 * constructor
	 * 
	 * @param name
	 */

	public ObjectNode(String name) {
		super.name = name;
		super.stackPosition = -1;
		super.nodeType = "object";
	}

}