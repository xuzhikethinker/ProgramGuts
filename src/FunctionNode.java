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

	 /**
     * Constructor
     * @param calledFrom
     * @param name
     * @param position
     */
public FunctionNode(FunctionNode calledFrom, String name, int position) {
	super.name = name;
            super.nodeType = "function";
            super.addConnection(calledFrom);
            super.stackPosition = position;
}

    /**
     * Constructor
     *
     */
    public FunctionNode() {
	super.name = "main()";
            super.nodeType = "function";
            this.stackPosition = 0;

}
	
	

}