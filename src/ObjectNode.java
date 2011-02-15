import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;

/*
 * The ObjectNode class represents a node representing an object
 * 
 * Each ObjectNode has a list(vector) of the ObjectNodes it is
 * connected to.
 * 
 */

public class ObjectNode extends Node {

	Vector<ObjectNode> ObjectsConnectedTo;
	String name;
	
	public ObjectNode(String name) {
		this.name = name;
		ObjectsConnectedTo = new Vector<ObjectNode>(); 
	}
	

	public void AddConnection(ObjectNode connectedTo)
	{
		this.ObjectsConnectedTo.add(connectedTo);
	}
	
	public void drawNode(Graphics g){
        
		Random r = new Random();
        g.drawOval(r.nextInt(600)+100,r.nextInt(500),100,50); //draws at origin 
        //g.drawString(objName, 65, 65);
    }
}
