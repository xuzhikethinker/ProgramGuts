import java.util.Hashtable;
import java.util.Vector;


enum NODE_TYPE { FUNCTION, OBJECT };

/**
 * general node classimport java.util.Hashtable;
import java.util.Vector;


enum NODE_TYPE { FUNCTION, OBJECT };

/**
 * general node class
 */
public class Node {
	
    int xCord, yCord; //stackPosition of node on canvas
    int vertexID;
    
    String nodeType;
    String name;
    String value;
    
    int stackPosition;
    

    Vector<Node> ObjectsConnectedTo = new Vector<Node>();
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
    public void addConnection(Node connectedTo) {
        ObjectsConnectedTo.add(connectedTo);
    }
	
}
 */
public class Node {
	
    int xCord, yCord; //stackPosition of node on canvas
    int vertexID;
    
    String nodeType;
    String name;
    String value;
    
    int stackPosition;
    

    Vector<Node> ObjectsConnectedTo = new Vector<Node>();
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
    public void addConnection(Node connectedTo) {
        ObjectsConnectedTo.add(connectedTo);
    }
	
}