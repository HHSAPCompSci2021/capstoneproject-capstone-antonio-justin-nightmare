package gameElements;
import java.awt.Point;

import core.DrawingSurface;

public class Enemy extends GameElement{
	private int health;
	
	public Enemy(int x, int y) {
		super(x,y);
		health = 10;
	} 
	
	@Override
	public void draw(DrawingSurface surface) {
		surface.circle(posX, posY, 10);
	}
	
	/**
	 * Moves the enemy by 1 step
	 * @return true if enemy is still alive, false if it is not
	 */
	public boolean act(){
		move(findPath());
		return true;
	}
	

	/**
	 * Makes the enemy take a certain amount of damage
	 * @param damage The amount of damage dealt to the enemy
	 * @return true if the enemy is still alive, false if it is not (health <= 0)
	 */
	public boolean takeDamage(int damage) {
		health -= damage;
		return health > 0;
	}
	
	private Point findPath() {
		return null;
	} 
	
	private void move(Point p) {
		
	}
}
