package gameElements;

import core.DrawingSurface;
import screenElements.Grid;
import screens.GameScreen;

/**
 * This class represents the regular enemy.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class RegularEnemy extends Enemy{
	/**
	 * creates a regular enemy with position x, y
	 * @param x x position
	 * @param y y position
	 */
	public RegularEnemy(int x, int y, Grid g, GameScreen sc) {
		super(x, y, g, sc);
		health = 10;
		super.increaseHealth(g);
		diameter = 18;
		goldValue = 10;
	}
	
	public void draw(DrawingSurface surface) {
		surface.fill(200,200,0);
		surface.stroke(100,100,0);
		surface.circle(posX, posY, diameter);
		surface.fill(0,0,0);
		surface.textSize(10);
		surface.text(health, posX, posY);
	}
}
