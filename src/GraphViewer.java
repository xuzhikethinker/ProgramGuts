import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

/**
 * This is where the graphics guys will put all of their code.
 * Can make more classes but have everything return to here.
 * 
 * Need to have a window that draws a canvas
 * 
 * Goal for first revision is to be able to draw a basic graph
 * 
 * Functions should be represented by a rectangles and objects are circles
 * 
 */
@SuppressWarnings("serial")
public class GraphViewer extends Canvas {
	
	Vector<Node> graph = new Vector<Node>();

	 /** 
     * constructor
     */
    public GraphViewer() {
	        this.setSize(800,600);
            this.setBackground(Color.white);
    }
    
    /** 
     * constructor
     * 
     * @param graph
     */
    public GraphViewer(Vector<Node> graph) {
    		this.graph = graph;
            this.setSize(800,600);
            this.setBackground(Color.white);
    }
    
    @Override
    /**
     * draws all nodes to canvas
     */
    public void paint(Graphics g)
    {
    	 for(Node n: graph)
    	 {
    		 n.drawConnection(g);
    	 }
         for(Node n: graph)
         {	     	 
        	 n.drawNode(g);
         }
         
         
    }

}
