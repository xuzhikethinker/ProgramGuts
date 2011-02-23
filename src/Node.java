import java.util.Vector;


enum NODE_TYPE { FUNCTION, OBJECT };

/**
 * general abstract node class
 */
public class Node {
	
    int xCord, yCord; //stackPosition of node on canvas
    int vertexID;
    Vector<Node> ObjectsConnectedTo = new Vector<Node>(); //vector of nodes connected to this node
    String nodeType;
    String name;
    int stackPosition;
    
	/**
	 * generic constructor for abstract node class
	 */
    /**
     * generic constructor for abstract node class
     */
    public Node() {
    }

    public void addConnection(Node connectedTo) {
        ObjectsConnectedTo.add(connectedTo);
    }
	
}