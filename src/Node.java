import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;


enum NODE_TYPE { FUNCTION, OBJECT };

/**
 * general abstract node class
 */
public abstract class Node {
	
	int xCord, yCord; //position of node on canvas
	Vector<Node> ObjectsConnectedTo = new Vector<Node>(); //vector of nodes connected to this node
	
	/**
	 * generic constructor for abstract node class
	 */
	public Node(){}	
	
	/**
	 * generic abstract drawNode function that must be implemented by each node type
	 * @param g
	 */
	public void drawNode(Graphics g){}
	
	/**\
	 * adds a node connection to the current node
	 * @param connectedTo
	 */
	public void addConnection(Node connectedTo){
			ObjectsConnectedTo.add(connectedTo);
	}
	
	/** 
	 * draws connections between nodes
	 * @param g
	 */
	public void drawConnection(Graphics g){
		
		Color originalColor = g.getColor();//save current canvas color 
		
		g.setColor(Color.black);
		if(!ObjectsConnectedTo.isEmpty()){
			for(Node o: ObjectsConnectedTo){				
					g.drawLine(o.xCord+140, o.yCord+25, xCord+100, yCord+25);
			}
		}
		g.setColor(originalColor);//revert to original canvas color
    }
	
}
