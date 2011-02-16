import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;

/**
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

	FunctionNode calledFrom;
	Vector<ObjectNode> ObjectsConnectedTo;
	String name;
	
	/**
	 * constructor
	 */
	public FunctionNode() {
		this.calledFrom = null; 
		name = "main";
		ObjectsConnectedTo = new Vector<ObjectNode>(); 
	}
	
	/**
	 * constructor
	 * @param calledFrom
	 * @param name
	 */
	public FunctionNode(FunctionNode calledFrom, String name) {
		this.calledFrom = calledFrom; 
		this.name = name;
		ObjectsConnectedTo = new Vector<ObjectNode>(); 
	}
	
	/**
	 * adds connection (edge) between this functionNode object
	 * and connectedTo(ObjectNode)
	 * 
	 * @param connectedTo
	 */
	public void AddConnectionToObject(ObjectNode connectedTo)
	{
		this.ObjectsConnectedTo.add(connectedTo);
	}
	
	/**
	 * draws node to canvas
	 */
	public void drawNode(Graphics g){
<<<<<<< HEAD
		Random r = new Random();
        g.drawOval(r.nextInt(800), r.nextInt(600), 50, 25); //draws node at origin
=======
        g.drawRect(0, 0, 100, 100); //draws node at origin
>>>>>>> 87c188b7c834d128d0b343e4ccf652ea443b5374
        //g.drawString(name, 0, 0);
    }
	
	
	
}
