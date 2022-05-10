package screenElements;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import core.DrawingSurface;
import gameElements.*;

public class Grid extends ScreenElement{
	private int cols;
	private int rows;
	private static final int CELL_WIDTH = 10;
	private ArrayList<Enemy> enemies;
	private ArrayList<Tower> towers;
	private Point goal;
	private int[][] gridMatrix;
	public static final int EMPTY = 0;
	public static final int PATH_SPACE = 1;
	public static final int BLOCKED_SPACE = 2;
	private Queue<Point> frontier;
	private Point[][] flowField;
	public Grid(int x, int y, int width, int height) {
		super(x,y,width,height);
		cols = width/CELL_WIDTH;
		rows = height/CELL_WIDTH;
		gridMatrix = new int[cols][rows];
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		goal = new Point(95, 34);
		frontier = new LinkedList<Point>();
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		// Sets the background
		surface.noFill();
		surface.rect(x, y, width, height);
		
		// Draws all the enemies
		for (Enemy e:enemies) {
			e.draw(surface,this);
		}
		for (Tower t:towers) {
			t.draw(surface);
		}
		surface.pop();
	}
	
	public void next() {
		for (Enemy e:enemies) {
			e.act(this);
		}
		for (Tower t:towers) {
			t.act(enemies);
		}
	}
	// Returns a 2D array of points. Each point in the array are the coordinates of where
	// the enemy should from their current coordinates, where the current coordinates is
	// the 2D index of the point in the array. In other words, this method returns a
	// flow field of where the enemy should given any coordinates in the grid.
	private Point[][] breadthFirstSearch() {
		Point[][] flowField = new Point[cols][rows];
		frontier.add(new Point(goal.x, goal.y));
		boolean[][] reachedSpaces = new boolean[cols][rows];
		reachedSpaces[goal.x][goal.y] = true;
		
		// for testing
//		int n = 0;
		// condition for testing: n < 100
		while(frontier.size() != 0) {
			Point currentSpace = frontier.poll();
			Point[] adjacentSpaces = new Point[4];
			
			adjacentSpaces[0] = getValidPoint(currentSpace.x+1, currentSpace.y);
			adjacentSpaces[1] = getValidPoint(currentSpace.x, currentSpace.y+1);
			adjacentSpaces[2] = getValidPoint(currentSpace.x, currentSpace.y-1);
			adjacentSpaces[3] = getValidPoint(currentSpace.x-1, currentSpace.y);
			
			for (int i = 0; i < adjacentSpaces.length; i++) {
				if (adjacentSpaces[i] == null) {
					continue;
				}
				
				if (!reachedSpaces[adjacentSpaces[i].x][adjacentSpaces[i].y]) {
					frontier.add(adjacentSpaces[i]);
					reachedSpaces[adjacentSpaces[i].x][adjacentSpaces[i].y] = true;
					flowField[adjacentSpaces[i].x][adjacentSpaces[i].y] = new Point(currentSpace.x, currentSpace.y);
					
					// for testing
					gridMatrix[adjacentSpaces[i].x][adjacentSpaces[i].y] = PATH_SPACE;
				}
			}
			
			// for testing
//			n++;
		}
		
		return flowField;
	}
	
	private Point getValidPoint(int x, int y) {
		if (x < 0 || x >= cols || y < 0 || y >= rows) {
			return null;
		}
		
		if (gridMatrix[x][y] == BLOCKED_SPACE) {
			return null;
		}
		
		return new Point(x, y);
	}
	
	// for testing
	public void setSpace(int x, int y) {
		if (gridMatrix[x][y] != 2) {
			gridMatrix[x][y] = 2;
		}
		else {
			gridMatrix[x][y] = 0;
		}
	}
	
	// for testing
	public void go() {
		flowField = breadthFirstSearch();
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
}
