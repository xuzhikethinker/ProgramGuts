import java.util.Hashtable;


enum NODE_TYPE { FUNCTION, OBJECT };

/**
 * general abstract node class
 */
public class Node {
	
    int xCord, yCord; //stackPosition of node on canvas
    int vertexID;
    //Vector<Node> ObjectsConnectedTo = new Vector<Node>(); //vector of nodes connected to this node
    String nodeType;
    String name;
    int stackPosition;
    
    Hashtable<String, Node> ObjectsConnectedTo = new Hashtable<String, Node>(); // string is edgename

    /**
     * generic constructor for abstract node class
     */
    public Node() {
    }

    public void addConnection(String edgeName, Node connectedTo) {
        ObjectsConnectedTo.put(edgeName, connectedTo);
    }
	
}