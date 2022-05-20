package screenElements;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import core.DrawingSurface;
import gameElements.*;
import screens.GameScreen;

/**
 * This class represents a grid.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class Grid extends ScreenElement{
	private int cols;
	private int rows;
	private static final int CELL_WIDTH = 20;
	private ArrayList<Enemy> enemies;
	private ArrayList<Tower> towers;
	private Point goalSpace;
	private int[][] gridMatrix;
	public static final int EMPTY_SPACE = 0;
	public static final int BLOCKED_SPACE = -1;
	public static final int PATH_SPACE = 1;
	public static final int GOAL_SPACE = 2;
	private Point[][] flowField;
	private GameScreen gScreen;
	private int waveNum;
	private ArrayList<int[]> activeWaves;
	private int timeBetweenSpawns = 60;
	private Point[] startSpaces;
	private PlayerCharacter player;
	private int bigEnemyOnlyWave;
	private boolean canSpawnNextWave;
	/**
	 * creates a grid with the specified position and dimensions
	 * @param x x position
	 * @param y y position
	 * @param width width
	 * @param height height
	 */
	public Grid(int x, int y, int width, int height,GameScreen sc) {
		super(x,y,width,height);
		cols = width/CELL_WIDTH + 1;
		rows = height/CELL_WIDTH;
		gridMatrix = new int[cols][rows];
		goalSpace = new Point(cols-1, rows/2);
		gridMatrix[goalSpace.x][goalSpace.y] = GOAL_SPACE;
		for (int i = 0; i < gridMatrix[0].length; i++) {
			if (gridMatrix[gridMatrix.length-1][i] != goalSpace.y) {
				gridMatrix[gridMatrix.length-1][i] = BLOCKED_SPACE;
			}
		}
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		gScreen = sc;
		waveNum = 1;
		activeWaves = new ArrayList<int[]>();
		startSpaces = new Point[rows];
		for (int i = 0; i < gridMatrix[0].length; i++) {
			startSpaces[i] = new Point(0, i);
		}
		player = new PlayerCharacter((goalSpace.x-1)*CELL_WIDTH+gScreen.getBorderWidth(),(goalSpace.y)*CELL_WIDTH+gScreen.getBorderWidth());
		bigEnemyOnlyWave = 8;
		canSpawnNextWave = true;
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		surface.fill(255);
		surface.stroke(255);
		surface.rect(x, y, width, height);
		for (Tower t:towers) {
			t.draw(surface);
		}
		for (Enemy e:enemies) {
			e.draw(surface);
		}
		player.draw(surface);
		surface.pop();
	}
	
	/**
	 * processes objects in the grid
	 */
	public void next() {
		for (int i = 0; i < activeWaves.size(); i++) {
			int[] currentWave = activeWaves.get(i);
			int currentWaveNum = currentWave[0];
			int currentWaveLeft = currentWave[1];
			int timeToNextSpawn = currentWave[2];
			if (currentWaveLeft > 0) {
				if (currentWaveNum == currentWaveLeft) {
					spawnEnemy(currentWaveNum, currentWave);
					currentWave[1]--;
				} else if (timeToNextSpawn < timeBetweenSpawns) {
					currentWave[2]++;
				} else if (Math.random()*10<1) {
					spawnEnemy(currentWaveNum, currentWave);
					currentWave[1]--;
					currentWave[2] = 0;
				}
			} else {
				activeWaves.remove(i);
				i--;
			}
			
			if (currentWaveLeft == 0) {
				canSpawnNextWave = true;
			}
		}
		for (int i = 0; i < enemies.size(); i++) {
			Enemy currentE = enemies.get(i);
			if (!currentE.act()) {
				gScreen.addGold(currentE.getGoldValue());
				enemies.remove(i);
				i--;
			} else if (currentE.getHasReachedGoal()) {
				enemies.remove(i);
			}
		}
		for (Tower t:towers) {
			t.act(enemies);
		}
		player.act(enemies);
	}
	
	private void spawnEnemy(int currentWaveNum, int[] currentWave) {
		if (currentWaveNum % bigEnemyOnlyWave == 0) {
			addToGrid(new BigEnemy(gScreen.indexToPos(0),gScreen.indexToPos((int)(Math.random()*rows)), this, gScreen));
			currentWave[1]--;
		} else if (currentWaveNum > bigEnemyOnlyWave) {
			boolean shouldSpawnBigEnemy = Math.random()*2 < 1;
			if (shouldSpawnBigEnemy) {
				addToGrid(new BigEnemy(gScreen.indexToPos(0),gScreen.indexToPos((int)(Math.random()*rows)), this, gScreen));
			} else {
				addToGrid(new RegularEnemy(gScreen.indexToPos(0),gScreen.indexToPos((int)(Math.random()*rows)), this, gScreen));
			}
		} else {
			addToGrid(new RegularEnemy(gScreen.indexToPos(0),gScreen.indexToPos((int)(Math.random()*rows)), this, gScreen));
		}
	}
	
	/**
	 * performs a breadth first search and returns a 2D array of points where each point is the coordinates of where the enemy should
	 * move from their current grid position; in other words, this method returns a flow field of where the enemy should move given any
	 * grid position
	 * @param matrix grid matrix to perform a breadth first search on
	 * @return a 2D array flow field
	 */
	public Point[][] breadthFirstSearch(int[][] matrix) {
		Point[][] flowField = new Point[cols][rows];
		flowField[goalSpace.x][goalSpace.y] = new Point(goalSpace.x, goalSpace.y);
		Queue<Point> frontier = new LinkedList<Point>();
		frontier.add(new Point(goalSpace.x, goalSpace.y));
		boolean[][] reachedSpaces = new boolean[cols][rows];
		reachedSpaces[goalSpace.x][goalSpace.y] = true;
		
		while(frontier.size() != 0) {
			Point currentSpace = frontier.poll();
			Point[] adjacentSpaces = new Point[4];
			
			adjacentSpaces[0] = getValidSpace(currentSpace.x+1, currentSpace.y, matrix);
			adjacentSpaces[1] = getValidSpace(currentSpace.x, currentSpace.y+1, matrix);
			adjacentSpaces[2] = getValidSpace(currentSpace.x, currentSpace.y-1, matrix);
			adjacentSpaces[3] = getValidSpace(currentSpace.x-1, currentSpace.y, matrix);
			
			for (int i = 0; i < adjacentSpaces.length; i++) {
				if (adjacentSpaces[i] == null) {
					continue;
				}
				
				if (!reachedSpaces[adjacentSpaces[i].x][adjacentSpaces[i].y]) {
					frontier.add(adjacentSpaces[i]);
					reachedSpaces[adjacentSpaces[i].x][adjacentSpaces[i].y] = true;
					matrix[adjacentSpaces[i].x][adjacentSpaces[i].y] = PATH_SPACE;
					flowField[adjacentSpaces[i].x][adjacentSpaces[i].y] = new Point(currentSpace.x, currentSpace.y);
				}
			}
		}
		
		return flowField;
	}
	
	private Point getValidSpace(int col, int row, int[][] matrix) {
		if (col < 0 || col >= cols || row < 0 || row >= rows) {
			return null;
		}
		
		if (matrix[col][row] == BLOCKED_SPACE) {
			return null;
		}
		
		if (matrix[col][row] == PATH_SPACE) {
			return null;
		}
		
		return new Point(col, row);
	}
	
	/**
	 * sets the grid position with the specified row and column in the grid matrix to the specified value
	 * @param col column
	 * @param row row
	 * @param val value
	 */
	public void setSpace(int col, int row, int val) {
		gridMatrix[col][row] = val;
	}
	
	public void clearGridPathSpaces(int[][] matrix) {
		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				if (matrix[col][row] == PATH_SPACE) {
					matrix[col][row] = EMPTY_SPACE;
				}
			}
		}
	}
	
	/**
	 * computes the flow field for the grid matrix
	 */
	public void computeFlowField() {
		clearGridPathSpaces(gridMatrix);
		flowField = breadthFirstSearch(gridMatrix);
	}
	
	/**
	 * returns the flow field for the grid matrix
	 * @return a 2D array flow field
	 */
	public Point[][] getFlowField() {
		return flowField;
	}
	
	/**
	 * adds the specified game element to the grid
	 * @param e game element
	 */
	public void addToGrid(GameElement e) {
		if (e instanceof Enemy) {
			enemies.add((Enemy)e);
		} else if (e instanceof Tower) {
			towers.add((Tower)e);
		}
	}
	
	/**
	 * removes the specified game element from the grid
	 * @param e game element
	 */
	public void removeFromGrid(GameElement e) {
		if (e instanceof Enemy) {
			enemies.remove(e);
		} else if (e instanceof Tower) {
			towers.remove(e);
		}
	}
	
	/**
	 * spawns a wave of enemies
	 */
	public void spawnWave() {
		computeFlowField();
		/*
		for (int i = 0; i < waveNum; i++) {
			addToGrid(new Enemy(gScreen.indexToPos(0),gScreen.indexToPos((int)(Math.random()*rows))));
		}
		*/
		activeWaves.add(new int[] {waveNum,waveNum+10,0});
		waveNum++;
	}
	
	/**
	 * returns the number of rows in the grid
	 * @return rows
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * returns the number of columns in the grid
	 * @return columns
	 */
	public int getCols() {
		return cols;
	}
	
	/**
	 * returns the width of each cell in the grid
	 * @return cell width
	 */
	public int getCellWidth() {
		return CELL_WIDTH;
	}
	
	/**
	 * returns the grid matrix
	 * @return the 2D array grid matrix
	 */
	public int[][] getGridMatrix() {
		return gridMatrix;
	}
	
	/**
	 * makes the player's base take damage by the specified amount
	 * @param amount amount of damage to deal
	 */
	public void takeDamage(int amount) {
		gScreen.takeDamage(amount);
	}
	
	/**
	 * returns an array of the starting grid positions of enemies
	 * @return array of grid positions
	 */
	public Point[] getStartSpaces() {
		return startSpaces;
	}
	
	/**
	 * returns an array of the goal positions for enemies
	 * @return array of grid positions
	 */
	public Point[] getGoalSpaces() {
		return new Point[] {goalSpace, new Point(goalSpace.x, goalSpace.y-1)};
	}
	
	/**
	 * returns an array of all enemy grid positions
	 * @return array of grid positions
	 */
	public Point[] getEnemySpaces() {
		Point[] enemySpaces = new Point[enemies.size()];
		for (int i = 0; i < enemies.size(); i++) {
			enemySpaces[i] = enemies.get(i).getCurrentSpace();
		}
		
		return enemySpaces;
	}
	
	/**
	 * returns the tower array list
	 * @return array list
	 */
	public ArrayList<Tower> getTowers(){
		return towers;
	}
	
	/**
	 * returns the wave number
	 * @return wave number
	 */
	public int getWaveNum() {
		return waveNum;
	}
	
	/**
	 * returns the status of being able to spawn the next wave
	 * @return true if can spawn next wave, false otherwise
	 */
	public boolean getCanSpawnNextWave() {
		return canSpawnNextWave;
	}
	
	/**
	 * makes it so a wave cannot be spawned
	 */
	public void setCanSpawnWaveFalse() {
		canSpawnNextWave = false;
	}
	
	/**
	 * Moves the player in the specified direction (Will move at the same speed, even when diagonally moving)
	 * @param directions First value: left-right movement, second value: up-down movement
	 */
	public void movePlayer(int[] directions) {
		double moveSpeed = player.getMoveSpeed();
		if (directions[0] == 0) {
			player.move(new double[] {0,moveSpeed*directions[1]});
		} else {
			if (directions[1] == 0) {
				player.move(new double[] {moveSpeed*directions[0],0});
			} else {
				double diagonalSpeed = Math.sqrt(2)*moveSpeed/2;
				player.move(new double[] {diagonalSpeed*directions[0],diagonalSpeed*directions[1]});
			}
		}
		int playerX = player.getX();
		int playerY = player.getY();
		if (playerX < gScreen.getBorderWidth()) {
			player.moveTo(gScreen.getBorderWidth(), playerY);
		} else if (playerX > gScreen.getBorderWidth() + CELL_WIDTH*(cols-1)) {
			player.moveTo(gScreen.getBorderWidth() + CELL_WIDTH*(cols-1), playerY);
		}
		playerX = player.getX();
		if (playerY < gScreen.getBorderWidth()) {
			player.moveTo(playerX, gScreen.getBorderWidth());
		} else if (playerY > gScreen.getBorderWidth() + CELL_WIDTH*rows) {
			player.moveTo(playerX, gScreen.getBorderWidth() + CELL_WIDTH*rows);
		}
	}
	
	public void playerMoveWeapon(Point p) {
		player.moveWeapon(p);
	}
	public void playerAttack() {
		player.attack();
	}
	public boolean getIsPlayerAttacking() {
		return player.getIsAttacking();
	}
}
