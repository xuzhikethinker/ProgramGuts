import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public class SaveGraph implements ActionListener{
	
	/*
	 * SaveGraph is accessed through the menu bar.  It allows
	 * the user to save the current view of their graph as a 
	 * jpeg.
	 */
	
	VisualizationViewer vv;
	JFrame parent;
	
	public SaveGraph(VisualizationViewer vv, JFrame parent){
		this.vv = vv;
		this.parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Pops up a save dialog
		FileDialog fd = new FileDialog(parent, "Save Graph Image");
		fd.setMode(FileDialog.SAVE);
		fd.setVisible(true);
		
		//Sets up your destination
		String filename = fd.getFile() + ".jpg";
		String directory = fd.getDirectory();
		File fileDest = new File(directory + filename);
		
	    //Paints your current Viewer and saves it as a jpeg
		int width = vv.getWidth();
	    int height = vv.getHeight();

	    Color bg = vv.getBackground();

	    BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
	    Graphics2D graphics = bi.createGraphics();
	    graphics.setColor(bg);
	    graphics.fillRect(0,0, width, height);

	    vv.paint(graphics);

	    try{
	       ImageIO.write(bi,"jpeg",fileDest);
	    }catch(Exception e1){e1.printStackTrace();}
	}
		
	}

