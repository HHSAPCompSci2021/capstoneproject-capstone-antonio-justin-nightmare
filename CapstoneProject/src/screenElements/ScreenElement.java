package screenElements;

/**
 * This class represents a screen element and is the superclass of all screen elements.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class ScreenElement {
	protected int x,y,width,height;
	public ScreenElement (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * returns the x position of the screen element
	 * @return x position
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * returns the y position of the screen element
	 * @return y position
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * returns the width of the screen element
	 * @return width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * returns the height of the screen element
	 * @return
	 */
	public int getHeight() {
		return height;
	}
}
