/**
 * The ObjectNode class represents a node representing an object
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