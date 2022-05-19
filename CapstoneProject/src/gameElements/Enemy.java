package gameElements;

import core.DrawingSurface;
import screenElements.Grid;
import screens.GameScreen;

import java.awt.Point;

/**
 * This class represents an enemy.
 * @author Antonio Cuan and Justin Yen
 *
 */
public abstract class Enemy extends GameElement{
	protected int health;
	protected int diameter;
	protected int goldValue;
	private boolean hasReachedGoal;
	private Grid grid;
	private GameScreen gScreen;
	private int prevMoveX, prevMoveY;
	private boolean hasFoundNextPos;
	/**
	 * creates an Enemy with position x, y
	 * @param x x position
	 * @param y y position
	 */
	public Enemy(int x, int y, Grid g, GameScreen sc) {
		super(x,y);
		hasReachedGoal = false;
		grid = g;
		gScreen = sc;
		hasFoundNextPos = false;
	}
	
	public abstract void draw(DrawingSurface surface);
	
	/**
	 * Moves the enemy by 1 step
	 * @return true if enemy is still alive, false if it is not
	 */
	public boolean act() {
		Point currentSpace = getCurrentSpace();
		if (currentSpace.equals(grid.getGoalSpaces()[0]) || currentSpace.equals(grid.getGoalSpaces()[1])) {
			grid.takeDamage(1);
			hasReachedGoal = true;
			return true;
		} else {
			Point nextPos = findNextPos();
			move(nextPos);
			return health > 0;
		}
	}
	

	/**
	 * Makes the enemy take a certain amount of damage
	 * @param damage The amount of damage dealt to the enemy
	 * @return true if the enemy is still alive, false if it is not (health <= 0)
	 */
	public boolean takeDamage(int damage) {
		health -= damage;
		if (health < 0) {
			health = 0;
		}
		return health > 0;
	}
	
	protected Point findNextPos() {
		Point currentSpace = getCurrentSpace();
		Point [][] flowField = grid.getFlowField();
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
		
		if (hasFoundNextPos) {
			if (moveX != prevMoveX || moveY != prevMoveY) {
				int midX = gScreen.indexToPos(currentSpace.x);
				int midY = gScreen.indexToPos(currentSpace.y);
				if (posX != midX || posY != midY) {
					return new Point(posX + prevMoveX, posY + prevMoveY);
				}
			}
		}
		
		prevMoveX = moveX;
		prevMoveY = moveY;
		hasFoundNextPos = true;
		
		return new Point(posX + moveX, posY + moveY);
	}
	
	/**
	 * calculates the current grid position of the enemy
	 * @return grid position
	 */
	public Point getCurrentSpace() {
		int col = (int)((posX - gScreen.getBorderWidth())/grid.getCellWidth());
		int row = (int)((posY - gScreen.getBorderWidth())/grid.getCellWidth());
		
		return new Point(col, row);
	}
	
	protected void move(Point p) {
		posX = (int) p.getX();
		posY = (int) p.getY();
	}

	/**
	 * returns the enemy's gold value
	 * @return gold value
	 */
	public int getGoldValue() {
		return goldValue;
	}
	
	/**
	 * returns enemy's status of reaching the goal
	 * @return true if the enemy has reached the goal, false otherwise
	 */
	public boolean getHasReachedGoal() {
		return hasReachedGoal;
	}
}
