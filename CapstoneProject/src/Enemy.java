import java.awt.Point;

public class Enemy {
	private int health;
	
	public Enemy() {
		health = 10;
	} 
	
	public void act(){
		move(findPath());
	}
	
	private Point findPath() {
		return null;
	} 
	
	private void takeDamage() {
		
	}
	
	private void move(Point p) {
		
	}
}
