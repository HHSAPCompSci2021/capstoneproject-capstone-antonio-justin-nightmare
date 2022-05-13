package screens;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
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
	private static final int GRID_WIDTH = 960;
	private DrawingSurface surface;
	private Grid grid;
	private Store store;
	private double gold;
	private int highlightedX, highlightedY;
	private Color highlightedColor;
	private int baseHealth;
	public GameScreen(DrawingSurface surface) {
		super(WIDTH, HEIGHT);
		grid = new Grid(BORDER_WIDTH,BORDER_WIDTH,GRID_WIDTH,HEIGHT - BORDER_WIDTH*2,this);
		store = new Store(1000,BORDER_WIDTH,WIDTH-1000-BORDER_WIDTH,HEIGHT - BORDER_WIDTH*2,this);
		this.surface = surface;
		gold = 0;
		highlightedColor = new Color(0, 255, 0, 100);
		baseHealth = 20;
	}

	public void draw() { 
		if (baseHealth > 0) {
			surface.background(150,150,200);
			fillGrid();
			grid.draw(surface);
			grid.next();
			store.draw(surface);
			highlightGrid();
			processKeyPresses();
			
			surface.push();
			surface.fill(0,0,0);
			surface.textAlign(PConstants.RIGHT);
			surface.textSize(15);
			surface.text("Gold: "+(int)gold, WIDTH-BORDER_WIDTH-200, BORDER_WIDTH, 200,20);
			surface.text("Base health: "+baseHealth, WIDTH-BORDER_WIDTH-200, BORDER_WIDTH+20,200,20);
			surface.pop();
		} else {
			surface.switchScreen(ScreenSwitcher.END_SCREEN);
		}
	}
	
	private void fillGrid() {
		float cellWidth = grid.getCellWidth();
		
		surface.fill(255);
		surface.stroke(255);
		
		for (int col = 0; col < grid.getCols(); col++) {
			for (int row = 0; row < grid.getRows(); row++) {
				float cellX = grid.getX() + col*cellWidth;
				float cellY = grid.getY() + row*cellWidth;
				
				// for testing
				if (grid.getGridMatrix()[col][row] == Grid.GOAL_SPACE) {
					surface.fill(255, 200, 0);
					surface.stroke(255, 200, 0);
				}
				
				surface.rect(cellX, cellY, cellWidth, cellWidth);
				surface.fill(255);
				surface.stroke(255);
			}
		}
	}
	
	public Point realPosToGridPos(Point p) {
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
	
	private void highlightGrid() {
		Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		if (assumedCoords.x < BORDER_WIDTH || assumedCoords.x > GRID_WIDTH || assumedCoords.y < BORDER_WIDTH || assumedCoords.y > HEIGHT - BORDER_WIDTH*2) {
			return;
		}
		
		Point mouseGridPos = realPosToGridPos(assumedCoords);
		highlightedX = indexToPosNoBuffer(mouseGridPos.x);
		highlightedY = indexToPosNoBuffer(mouseGridPos.y);
		surface.fill(highlightedColor.getRed(), highlightedColor.getGreen(), highlightedColor.getBlue(), highlightedColor.getAlpha());
		surface.stroke(highlightedColor.getRed(), highlightedColor.getGreen(), highlightedColor.getBlue(), highlightedColor.getAlpha());
		surface.rect(highlightedX, highlightedY, grid.getCellWidth()*2, grid.getCellWidth()*2);
	}
	
	public void mousePressed() {
		Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		if (store.checkIsPointInItem(assumedCoords)) {
			store.toggleItemSelect();
		}
		
		// for testing
//		Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
//		Point cellCoord = realPosToGridPos(assumedCoords);
//		if (cellCoord != null) {
//			grid.setSpace(cellCoord.x, cellCoord.y);
//		}
	}
	
	public void mouseDragged() {
		// for testing
//		if (surface.mouseButton == PConstants.LEFT) {
//			Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
//			Point cellCoord = realPosToGridPos(assumedCoords);
//			if (cellCoord != null) {
//				grid.setSpace(cellCoord.x, cellCoord.y);
//			}
//		}
	}
	
	public void mouseReleased() {
		if (store.getIsItemSelected()) {
			placeTower();
		}
	}
	
	private void placeTower() {
		Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		if (assumedCoords.x < BORDER_WIDTH || assumedCoords.x > GRID_WIDTH || assumedCoords.y < BORDER_WIDTH || assumedCoords.y > HEIGHT - BORDER_WIDTH*2) {
			return;
		}
		Point gridPos = realPosToGridPos(assumedCoords);
		if (gridPos == null) {
			return;
		}
		if (gridPos.x < 0 || gridPos.x+1 >= grid.getCols() || gridPos.y < 0 || gridPos.y+1 >= grid.getRows()) {
			return;
		}
		if (checkDoesTowerOverlap(gridPos.x, gridPos.y)) {
			return;
		}
		if (checkIsPathBlocked(gridPos)) {
			return;
		}
		
		grid.setSpace(gridPos.x, gridPos.y, Grid.BLOCKED_SPACE);
		grid.setSpace(gridPos.x+1, gridPos.y, Grid.BLOCKED_SPACE);
		grid.setSpace(gridPos.x, gridPos.y+1, Grid.BLOCKED_SPACE);
		grid.setSpace(gridPos.x+1, gridPos.y+1, Grid.BLOCKED_SPACE);
		int x = indexToPosNoBuffer(gridPos.x);
		int y = indexToPosNoBuffer(gridPos.y);
		grid.addToGrid(new Tower(x, y, grid.getCellWidth()*2, store));
	}
	
	private int indexToPosNoBuffer(int index) {
		return index*grid.getCellWidth() + BORDER_WIDTH;
	}
	
	private boolean checkDoesTowerOverlap(int col, int row) {
		return grid.getGridMatrix()[col][row] == Grid.BLOCKED_SPACE ||
				grid.getGridMatrix()[col+1][row] == Grid.BLOCKED_SPACE ||
				grid.getGridMatrix()[col][row+1] == Grid.BLOCKED_SPACE ||
				grid.getGridMatrix()[col+1][row+1] == Grid.BLOCKED_SPACE;
	}
	
	private boolean checkIsPathBlocked(Point pos) {
		int[][] gMatrix = new int[grid.getCols()][grid.getRows()];
		for (int col = 0; col < grid.getCols(); col++) {
			for (int row = 0; row < grid.getRows(); row++) {
				gMatrix[col][row] = grid.getGridMatrix()[col][row];
			}
		}
		grid.clearGridPathSpaces(gMatrix);
		
		gMatrix[pos.x][pos.y] = Grid.BLOCKED_SPACE;
		gMatrix[pos.x+1][pos.y] = Grid.BLOCKED_SPACE;
		gMatrix[pos.x][pos.y+1] = Grid.BLOCKED_SPACE;
		gMatrix[pos.x+1][pos.y+1] = Grid.BLOCKED_SPACE;
		
		Point[][] flowField = grid.breadthFirstSearch(gMatrix);
		
		for (int i = 0; i < grid.getStartSpaces().length; i++) {
			Point space = new Point(grid.getStartSpaces()[i].x, grid.getStartSpaces()[i].y);
			if (flowField[space.x][space.y] == null) {
				return true;
			}
		}
		
		return false;
	}
	
	public int getBorderWidth() {
		return BORDER_WIDTH;
	}
	
	// for testing
	public void go() {
		grid.go();
	}
	
	private void processKeyPresses() {
//		if (surface.isPressed(KeyEvent.VK_W)) {
//			System.out.println("W pressed");
//		}
	}
	
	public void addGold(int amount) {
		gold += amount;
	}
	
	public void removeGold(int amount) {
		gold -= amount;
	}
	
	public void takeDamage(int amount) {
		baseHealth -= amount;
	}
}
