package core;
import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * This class represents the head of the program. All other classes "branch out" from this class.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class Main {

	/**
	 * creates a JFrame window and runs the program
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setSize(new Dimension(1280,720));
		window.setMinimumSize(new Dimension(1280, 720));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setLocationRelativeTo(null);
		
		window.setVisible(true);
		
		canvas.requestFocus();
	}

}
