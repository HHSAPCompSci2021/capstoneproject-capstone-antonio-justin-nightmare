package screens;

/**
 * This class represents a screen and is the superclass of all screens.
 * @author Antonio Cuan and Justin Yen
 *
 */
public abstract class Screen {
	public final int DRAWING_WIDTH, DRAWING_HEIGHT;
	
	/**
	 * creates a screen with the specified width and height
	 * @param width width
	 * @param height height
	 */
	public Screen(int width, int height) {
		this.DRAWING_WIDTH = width;
		this.DRAWING_HEIGHT = height;
	}
	
	/**
	 * draws the screen
	 */
	public void draw() {
		
	}
	
	/**
	 * detects mouse presses
	 */
	public void mousePressed() {
		
	}
	
	/**
	 * detects mouse movement
	 */
	public void mouseMoved() {
		
	}
	
	/**
	 * detects the mouse being moved while clicked
	 */
	public void mouseDragged() {
		
	}
}
