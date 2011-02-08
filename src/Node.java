import java.util.Vector;

enum NODE_TYPE { FUNCTION, OBJECT };

public class Node {
	Vector<Node> neighbors;
	NODE_TYPE thisType;
	
	
	public Node(NODE_TYPE thisType){
		neighbors = new Vector<Node>();
		this.thisType = thisType;
	}		
}
