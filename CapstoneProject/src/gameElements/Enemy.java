package gameElements;

import core.DrawingSurface;
import screenElements.Grid;

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
	
	public void draw(DrawingSurface surface, Grid g) {
		if (isInBounds(g)) {
			surface.fill(200,200,0);
			surface.circle(posX, posY, 10);
		} else {
			//g.removeFromGrid(this);
		}
	}
	/**
	 * Moves the enemy by 1 step
	 * @return true if enemy is still alive, false if it is not
	 */
	public boolean act(Grid g){
		move(findPath(g));
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
	
	private int[] findPath(Grid g) {
		return new int[] {posX+1,posY};
	} 
	
	private void move(int[] p) {
		posX = p[0];
		posY = p[1];
	}
	
	private boolean isInBounds(Grid g) {
		return posX > g.getX() && posX < g.getX()+g.getWidth() && 
				posY > g.getY() && posY < g.getY()+g.getHeight();
	}
}
