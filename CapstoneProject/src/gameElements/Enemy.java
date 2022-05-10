package gameElements;

import core.DrawingSurface;
import screenElements.Grid;

import java.awt.Point;
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
			surface.fill(0,0,0);
			surface.text(health, posX, posY);
			act(g);
		} else {
			//g.removeFromGrid(this);
		}
	}
	/**
	 * Moves the enemy by 1 step
	 * @return true if enemy is still alive, false if it is not
	 */
	public boolean act(Grid g){
//		move(findPath(g));
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
	
	private Point findPath(Grid g) {
//		ArrayList<int[]> possibleDirs = new ArrayList<int[]>();
//		if (posY < g.getUpperGoal()[1]) {
//			possibleDirs.add(new int[] {0,1});
//		} else if (posY > g.getLowerGoal()[1]) {
//			possibleDirs.add(new int[] {0,-1});
//		}
//		if (posX < g.getUpperGoal()[0]) {
//			possibleDirs.add(new int[] {1,0});
//		}
//		if (possibleDirs.size() > 0) {
//			int[] moveDist = possibleDirs.get((int)(Math.random()*possibleDirs.size()));
//			return new Point(posX+moveDist[0],posY+moveDist[1]);
//		} else {
//			g.removeFromGrid(this);
//			return new Point(0,0);
//		}
		
		return null;
	}
	
	private void move(Point p) {
		posX = (int) p.getX();
		posY = (int) p.getY();
	}
	
	private boolean isInBounds(Grid g) {
		return posX > g.getX() && posX < g.getX()+g.getWidth() && 
				posY > g.getY() && posY < g.getY()+g.getHeight();
	}
}
