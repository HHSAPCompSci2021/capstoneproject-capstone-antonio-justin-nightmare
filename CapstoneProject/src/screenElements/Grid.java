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
	private Point goal;
//	key for grid matrix:
//	0: empty
//	1: enemy
//	2: tower
	private int[][] gridMatrix;
	private Queue<Point> frontier;
	public Grid(int x, int y, int width, int height) {
		super(x,y,width,height);
		cols = width/CELL_WIDTH;
		rows = height/CELL_WIDTH;
		gridMatrix = new int[cols][rows];
		enemies = new ArrayList<Enemy>();
		goal = new Point(95, 34);
		frontier = new LinkedList<Point>();
		
		breadthFirstSearch();
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
		
		surface.pop();
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
					gridMatrix[adjacentSpaces[i].x][adjacentSpaces[i].y] = 1;
				}
			}
		}
		
		return flowField;
	}
	
	private Point getValidPoint(int x, int y) {
		if (x < 0 || x >= cols || y < 0 || y >= rows) {
			return null;
		}
		
		return new Point(x, y);
	}
	
	// for testing
	public void go() {
		breadthFirstSearch();
	}
	
	public void addToGrid(Enemy e) {
		enemies.add(e);
	}
	
	public void removeFromGrid(Enemy e) {
		enemies.remove(e);
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
