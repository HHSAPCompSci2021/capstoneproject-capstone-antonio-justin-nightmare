package gameElements;

import core.DrawingSurface;
import screenElements.Grid;
import screens.GameScreen;

/**
 * This class represents a big enemy.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class BigEnemy extends Enemy{
	/**
	 * creates a big enemy with position x, y
	 * @param x x position
	 * @param y y position
	 */
	public BigEnemy(int x, int y, Grid g, GameScreen sc) {
		super(x, y, g, sc);
		health = 40;
		super.increaseHealth(g);
		diameter = 36;
		goldValue = 20;
	}
	
	public void draw(DrawingSurface surface) {
		surface.fill(200,0,0);
		surface.stroke(100,0,0);
		surface.circle(posX, posY, diameter);
		surface.fill(0,0,0);
		surface.textSize(10);
		surface.text(health, posX, posY);
	}
}
