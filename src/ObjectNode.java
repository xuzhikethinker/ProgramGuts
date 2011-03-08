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
		super.stackPosition = -1;/**
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

	public ObjectNode(String name, String value) {
		super.name = name;
		super.value = value;
		super.stackPosition = -1;
		super.nodeType = "object";
	}
}
	}

	/**
	 * constructor
	 * 
	 * @param name
	 */

	public ObjectNode(String name, String value) {
		super.name = name;
		super.value = value;
		super.stackPosition = -1;
		super.nodeType = "object";
	}
}