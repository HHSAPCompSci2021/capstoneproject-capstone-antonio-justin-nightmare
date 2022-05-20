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
	
	// delete if not needed
//	public void setup() {
//		// TODO Auto-generated method stub
//		
//	}
	
	/**
	 * draws the screen
	 */
	public void draw() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * detects mouse presses
	 */
	public void mousePressed() {
		// TODO Auto-generated method stub
		
	}
	
	// delete if not needed
	/**
	 * detects mouse movement
	 */
	public void mouseMoved() {
		// TODO Auto-generated method stub
		
	}
	
	// delete if not needed
	/**
	 * detects the mouse being moved while clicked
	 */
	public void mouseDragged() {
		// TODO Auto-generated method stub
		
	}
	
	// delete if not needed
//	public void mouseReleased() {
//		// TODO Auto-generated method stub
//		
//	}
}
