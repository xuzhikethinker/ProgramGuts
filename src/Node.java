import java.util.Hashtable;

/**
 * general node 
 */
public class Node {
	
    int xCord, yCord; //stackPosition of node on canvas
    int vertexID;
    
    String nodeType;
    String name;
    String value;
    
    int stackPosition;
    
    Hashtable<String,Node> ObjectsConnectedTo = new Hashtable<String,Node>();
    /**
     * generic constructor for node class
     */
    public Node() {
    }

    /**
     * Adds a connection between this node and "connectedTo" by 
     * adding an entry into the hashtable
     * @param edgeName
     * @param connectedTo
     */
    public void addConnection(String edgeName, Node connectedTo) {
        System.out.println("Connecting " + name + " to " + connectedTo.name + " via " + edgeName);
        connectedTo.ObjectsConnectedTo.put(edgeName, this);
    }
	
}