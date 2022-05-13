package screenElements;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import core.DrawingSurface;
import gameElements.*;
import screens.GameScreen;

public class Grid extends ScreenElement{
	private int cols;
	private int rows;
	private static final int CELL_WIDTH = 20;
	private ArrayList<Enemy> enemies;
	private ArrayList<Tower> towers;
	private Point goal;
	private int[][] gridMatrix;
	public static final int EMPTY_SPACE = 0;
	public static final int BLOCKED_SPACE = -1;
	public static final int PATH_SPACE = 1;
	public static final int GOAL_SPACE = 2;
	private Point[][] flowField;
	private GameScreen gScreen;
	private int waveNum;
	private Point[] startSpaces;
	public Grid(int x, int y, int width, int height,GameScreen sc) {
		super(x,y,width,height);
		cols = width/CELL_WIDTH;
		rows = height/CELL_WIDTH;
		gridMatrix = new int[cols][rows];
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		goal = new Point(cols-1, rows/2);
		gridMatrix[goal.x][goal.y] = GOAL_SPACE;
		gScreen = sc;
		waveNum = 1;
		startSpaces = new Point[rows];
		for (int i = 0; i < gridMatrix[0].length; i++) {
			startSpaces[i] = new Point(0, i);
		}
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		surface.noFill();
		surface.rect(x, y, width, height);
		
		for (Tower t:towers) {
			t.draw(surface);
		}
		for (Enemy e:enemies) {
			e.draw(surface,this);
		}
		surface.pop();
	}
	
	public void next() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy currentE = enemies.get(i);
			if (!currentE.act(this)) {
				gScreen.addGold(currentE.getGoldValue());
				enemies.remove(i);
				i--;
			}
		}
		for (Tower t:towers) {
			t.act(enemies);
		}
	}
	
	// Returns a 2D array of points. Each point in the array are the coordinates of where
	// the enemy should from their current coordinates, where the current coordinates is
	// the 2D index of the point in the array. In other words, this method returns a
	// flow field of where the enemy should given any coordinates in the grid.
	public Point[][] breadthFirstSearch(int[][] matrix) {
		Point[][] flowField = new Point[cols][rows];
		flowField[goal.x][goal.y] = new Point(goal.x, goal.y);
		Queue<Point> frontier = new LinkedList<Point>();
		frontier.add(new Point(goal.x, goal.y));
		boolean[][] reachedSpaces = new boolean[cols][rows];
		reachedSpaces[goal.x][goal.y] = true;
		
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
	
	public void setSpace(int col, int row, int val) {
		gridMatrix[col][row] = val;
	}
	
	// for testing
	public void go() {
		resetGridMatrix();
		flowField = breadthFirstSearch(gridMatrix);
		spawnWave(waveNum);
		waveNum++;
	}
	
	private void resetGridMatrix() {
		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				if (gridMatrix[col][row] == PATH_SPACE) {
					gridMatrix[col][row] = EMPTY_SPACE;
				}
			}
		}
	}
	
	public Point[][] getFlowField() {
		return flowField;
	}
	
	public void addToGrid(GameElement e) {
		if (e instanceof Enemy) {
			enemies.add((Enemy)e);
		} else if (e instanceof Tower) {
			towers.add((Tower)e);
		}
	}
	
	public void removeFromGrid(GameElement e) {
		if (e instanceof Enemy) {
			enemies.remove(e);
		} else if (e instanceof Tower) {
			towers.remove(e);
		}
	}
	
	public void spawnWave(int waveNum) {
		for (int i = 0; i < waveNum; i++) {
			addToGrid(new Enemy(gScreen.indexToPos(0),gScreen.indexToPos((int)(Math.random()*rows))));
		}
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getCellWidth() {
		return CELL_WIDTH;
	}
	
	public int[][] getGridMatrix() {
		return gridMatrix;
	}
	
	public int getScreenBorderWidth() {
		return gScreen.getBorderWidth();
	}
	
	public void takeDamage(int amount) {
		gScreen.takeDamage(amount);
	}
	
	public Point[] getStartSpaces() {
		return startSpaces;
	}
}
