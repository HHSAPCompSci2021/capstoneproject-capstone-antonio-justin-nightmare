package screens;
import java.awt.Point;
import java.awt.event.KeyEvent;

import core.DrawingSurface;
import gameElements.*;
import processing.core.PConstants;
import screenElements.Grid;
import screenElements.Store;

public class GameScreen extends Screen{
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int BORDER_WIDTH = 20;
	private static final double GOLD_PER_SECOND = 60;
	private DrawingSurface surface;
	private Grid grid;
	private Store store;
	private double gold;
	public GameScreen(DrawingSurface surface) {
		super(WIDTH, HEIGHT);
		grid = new Grid(BORDER_WIDTH,BORDER_WIDTH,960,HEIGHT - BORDER_WIDTH*2);
		grid.setScreenBorderWidth(BORDER_WIDTH);
		store = new Store(1000,BORDER_WIDTH,WIDTH-1000-BORDER_WIDTH,HEIGHT - BORDER_WIDTH*2);
		this.surface = surface;
		grid.addToGrid(new Enemy(indexToPos(0),indexToPos(30)));
		grid.addToGrid(new Tower(indexToPos(1),indexToPos(25)));
		gold = 0;
	}

	public void draw() {
		surface.background(150,150,200);
		fillGrid();
		grid.draw(surface);
		grid.next();
		store.draw(surface);
		processKeyPresses();
	}
	
	private void fillGrid() {
		float cellWidth = grid.getCellWidth();
		
		surface.fill(255);
		surface.stroke(255);
		
		for (int i = 0; i < grid.getCols(); i++) {
			for (int j = 0; j < grid.getRows(); j++) {
				float cellX = grid.getX() + i*cellWidth;
				float cellY = grid.getY() + j*cellWidth;
				
				Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
				boolean inX = cellX <= assumedCoords.getX() && assumedCoords.getX() < cellX + cellWidth;
				boolean inY = cellY <= assumedCoords.getY() && assumedCoords.getY() < cellY + cellWidth;
				
				if (inX && inY) {
					surface.fill(0, 255, 0);
					surface.stroke(0, 255, 0);
				}
				
				if (grid.getGridMatrix()[i][j] == Grid.BLOCKED_SPACE) {
					// for testing
					surface.fill(0, 0, 255);
					surface.stroke(0, 0, 255);
				}
				
				if (grid.getGridMatrix()[i][j] == Grid.GOAL_SPACE) {
					// for testing
					surface.fill(255, 200, 0);
					surface.stroke(255, 200, 0);
				}
				
				surface.rect(cellX, cellY, cellWidth, cellWidth);
				surface.fill(255);
				surface.stroke(255);
			}
		}
	}
	
	public Point posToIndex(Point p) {
		int gridX = (int)((p.x - BORDER_WIDTH)/grid.getCellWidth());
		int gridY = (int)((p.y - BORDER_WIDTH)/grid.getCellWidth());
		
		if (gridX < 0 || gridY < 0 || gridX >= grid.getCols() || gridY >= grid.getRows()) {
			return null;
		}
		
		return new Point(gridX, gridY);
	}
	
	// assumes index is in bounds
	public int indexToPos(int index) {
		return index*grid.getCellWidth() + BORDER_WIDTH + grid.getCellWidth()/2;
	}
	
	public void mousePressed() {
		if (surface.mouseButton == PConstants.LEFT) {
			Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
			Point cellCoord = posToIndex(assumedCoords);
			if (cellCoord != null) {
				// for testing
				grid.setSpace(cellCoord.x, cellCoord.y);
			}
		}
	}
	
	// for testing
	public void mouseDragged() {
		if (surface.mouseButton == PConstants.LEFT) {
			Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
			Point cellCoord = posToIndex(assumedCoords);
			if (cellCoord != null) {
				// for testing
				grid.setSpace(cellCoord.x, cellCoord.y);
			}
		}
	}
	
	// for testing
	public void go() {
		grid.go();
	}
	
	private void processKeyPresses() {
		if (surface.isPressed(KeyEvent.VK_W)) {
			System.out.println("W pressed");
		}
	}
}
