package gameElements;

import core.DrawingSurface;

/**
 * This class represents a game element and is the superclass of all game elements.
 * @author Antonio Cuan and Justin Yen
 *
 */
public abstract class GameElement {
	protected int posX, posY;
	
	/**
	 * creates a game element and position x, y
	 * @param x
	 * @param y
	 */
	public GameElement(int x, int y) {
		posX = x;
		posY = y;
	}
	
	/**
	 * calculates the distance to another game element
	 * @param g other game element
	 * @return distance
	 */
	public double distanceTo(GameElement g) {
		return (Math.sqrt(Math.pow(posX-g.getX(), 2)+Math.pow(posY-g.getY(), 2)));
	}
	
	/**
	 * draws the game element
	 * @param surface
	 */
	public abstract void draw(DrawingSurface surface);
	
	/**
	 * returns the x position of the game element
	 * @return x position
	 */
	public int getX() {
		return posX;
	}
	
	/**
	 * returns the y position of the game element
	 * @return y position
	 */
	public int getY() {
		return posY;
	}
}
