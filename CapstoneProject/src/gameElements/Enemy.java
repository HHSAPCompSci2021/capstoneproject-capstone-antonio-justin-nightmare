package gameElements;

import core.DrawingSurface;
import screenElements.Grid;

import java.awt.Point;
public class Enemy extends GameElement{
	private int health;
	private int diameter;
	private static final int goldValue = 10;
	public Enemy(int x, int y) {
		super(x,y);
		health = 10;
		diameter = 18;
	}
	
	@Override
	public void draw(DrawingSurface surface) {
		surface.circle(posX, posY, diameter);
	}
	
	public void draw(DrawingSurface surface, Grid g) {
		surface.fill(200,200,0);
		surface.stroke(100,100,0);
		surface.circle(posX, posY, diameter);
		surface.fill(0,0,0);
		surface.textSize(10);
		surface.text(health, posX, posY);
//		act(g);
	}
	/**
	 * Moves the enemy by 1 step
	 * @return true if enemy is still alive, false if it is not
	 */
	public boolean act(Grid g){
		Point nextPos = findNextPos(g);
		if (!nextPos.equals(new Point(posX,posY))) {
			move(nextPos);
		}
		
		return health > 0;
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
	
	private Point findNextPos(Grid g) {
		Point currentSpace = getCurrentSpace(g);
		Point [][] flowField = g.getFlowField();
		if (flowField == null) {
			return new Point(posX, posY);
		}
		
		Point nextSpace = flowField[currentSpace.x][currentSpace.y];
		if (nextSpace == null) {
			return new Point(posX, posY);
		}
		
		int moveX = 0;
		int moveY = 0;
		
		if (nextSpace.x - currentSpace.x > 0) {
			moveX = 1;
		} else if (nextSpace.x - currentSpace.x < 0) {
			moveX = -1;
		}
		
		if (nextSpace.y - currentSpace.y > 0) {
			moveY = 1;
		} else if (nextSpace.y - currentSpace.y < 0) {
			moveY = -1;
		}
		
		return new Point(posX + moveX, posY + moveY);
		
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
	}
	
	private Point getCurrentSpace(Grid g) {
		int col = (int)((posX - g.getScreenBorderWidth())/g.getCellWidth());
		int row = (int)((posY - g.getScreenBorderWidth())/g.getCellWidth());
		
		return new Point(col, row);
	}
	
	private void move(Point p) {
		posX = (int) p.getX();
		posY = (int) p.getY();
	}

	public int getGoldValue() {
		return goldValue;
	}
}
