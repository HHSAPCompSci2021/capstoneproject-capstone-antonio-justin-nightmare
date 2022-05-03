package gameElements;
import java.awt.Point;

public class Enemy extends GameElement{
	private int health;
	
	public Enemy(int x, int y) {
		super(x,y);
		health = 10;
	} 
	
	public void draw() {
		
	}
	
	public void act(){
		move(findPath());
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
