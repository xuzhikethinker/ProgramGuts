import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;

/**
 * The ObjectNode class represents a node representing an object
 * 
 * Each ObjectNode has a list(vector) of the ObjectNodes it is
 * connected to.
 * 
 */
public class ObjectNode extends Node {

	Vector<ObjectNode> ObjectsConnectedTo;
	String name;
	
	/** 
	 * constructor
	 * @param name
	 */
	public ObjectNode(String name) {
		this.name = name;
		ObjectsConnectedTo = new Vector<ObjectNode>(); 
	}	

	/** 
	 * creates a connection between this object node and 
	 * connectedTo object node
	 * 
	 * @param connectedTo
	 */
	public void AddConnection(ObjectNode connectedTo)
	{
		this.ObjectsConnectedTo.add(connectedTo);
	}
	
	/**
	 * draws this object node to canvas 
	 */
	public void drawNode(Graphics g){
        
		Random r = new Random();
        g.drawOval(r.nextInt(600)+100,r.nextInt(500),100,50); //draws at origin 
        //g.drawString(name, 65, 65);
    }
}
