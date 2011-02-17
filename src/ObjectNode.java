import java.awt.Color;
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

	String name;
	
	/**
	 * constructor
	 */
	ObjectNode() {
		ObjectsConnectedTo = new Vector<Node>();
		Random r = new Random();
		super.xCord = r.nextInt(600)+100;
		super.yCord = r.nextInt(500);
	}	
	
	/** 
	 * constructor
	 * @param name
	 */
	
	public ObjectNode(String name) {
		this.name = name;
		Random r = new Random();
		super.xCord = r.nextInt(550)+150;
		super.yCord = r.nextInt(500);
	}	


	/**
	 *  draws object node to canvas
	 */
	public void drawNode(Graphics g){
		
		Color originalColor = g.getColor();//save current canvas color 
		
		g.setColor(Color.gray);		
                g.fillOval(xCord,yCord,150,75); 
           g.setColor(Color.white);
           if(name!=null)
        	   g.drawString(this.name, xCord + 30, yCord + 20);
		
                g.setColor(originalColor);//revert to original canvas color
        

    }
	
	
}
