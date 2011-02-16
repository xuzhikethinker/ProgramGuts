import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;

/*
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
	String name;
	Integer position;

	/**
	 * 
	 */
	public FunctionNode() {
		this.calledFrom = null;
		name = "main";
		super.xCord = 10;
		super.yCord = 5;
	}

	/**
	 * constructor
	 * 
	 * @param calledFrom
	 * @param name
	 * @param position
	 */
	public FunctionNode(FunctionNode calledFrom, String name, Integer position) {
		super.ObjectsConnectedTo.add(calledFrom);
		this.name = name;
		this.position = position;
		super.xCord = 10;
		super.yCord = position * 50 + 5;
	}

	/**
	 * draws the function node to canvas
	 */
	public void drawNode(Graphics g) {
		Color originalColor = g.getColor();//save current canvas color 
		Random r = new Random();
		g.setColor(Color.gray);
		g.fillRect(xCord, yCord, 150, 40); // draws node at origin
		g.setColor(Color.white);
		g.drawString(this.name, xCord + 30, yCord + 20);
		g.setColor(originalColor);//revert to original canvas color
	}
	
	

}
