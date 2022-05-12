package screens;
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
	private DrawingSurface surface;
	private Grid grid;
	private Store store;
	private double gold;
	private int dragOffsetX, dragOffsetY;
	private Rectangle storeItemRect;
	private boolean hasClickedTower;
	private boolean isDraggingTower;
	private int draggedTowerAlpha;
	private int baseHealth;
	public GameScreen(DrawingSurface surface) {
		super(WIDTH, HEIGHT);
		grid = new Grid(BORDER_WIDTH,BORDER_WIDTH,960,HEIGHT - BORDER_WIDTH*2,this);
		grid.setScreenBorderWidth(BORDER_WIDTH);
		store = new Store(1000,BORDER_WIDTH,WIDTH-1000-BORDER_WIDTH,HEIGHT - BORDER_WIDTH*2,this);
		this.surface = surface;
		gold = 0;
		storeItemRect = new Rectangle();
		hasClickedTower = false;
		isDraggingTower = false;
		draggedTowerAlpha = 50;
		baseHealth = 20;
	}

	public void draw() { 
		if (baseHealth > 0) {
			surface.background(150,150,200);
			fillGrid();
			grid.draw(surface);
			grid.next();
			store.draw(surface);
			surface.fill(store.getItemColor().getRed(), store.getItemColor().getGreen(), store.getItemColor().getBlue(), draggedTowerAlpha);
			surface.stroke(0);
			surface.rect(storeItemRect.x, storeItemRect.y, storeItemRect.width, storeItemRect.height);
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
				
				boolean test = false;
				
				if (isDraggingTower) {
					Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
					boolean inX = cellX <= assumedCoords.getX() && assumedCoords.getX() < cellX + cellWidth;
					boolean inY = cellY <= assumedCoords.getY() && assumedCoords.getY() < cellY + cellWidth;
					
					if (inX && inY) {
						surface.fill(0, 255, 0);
						surface.stroke(0, 255, 0);
						test = true;
					}
				}
				
				if (grid.getGridMatrix()[col][row] == Grid.GOAL_SPACE) {
					// for testing
					surface.fill(255, 200, 0);
					surface.stroke(255, 200, 0);
				}
				
				if (test) {
					surface.rect(cellX - cellWidth, cellY, cellWidth, cellWidth);
					surface.rect(cellX, cellY - cellWidth, cellWidth, cellWidth);
					surface.rect(cellX - cellWidth, cellY - cellWidth, cellWidth, cellWidth);
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
	
	public void mousePressed() {
		if (store.getStoreItemRefRect().contains(surface.mouseX, surface.mouseY)) {
			dragTower(store.getStoreItemRefRect());
		}
		
		// for testing
//		Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
//		Point cellCoord = realPosToGridPos(assumedCoords);
//		if (cellCoord != null) {
//			grid.setSpace(cellCoord.x, cellCoord.y);
//		}
	}
	
	public void mouseDragged() {
		if (storeItemRect != null) {
			storeItemRect.x = surface.mouseX - dragOffsetX;
			storeItemRect.y = surface.mouseY - dragOffsetY;
		}
		
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
		placeTower();
		isDraggingTower = false;
		hasClickedTower = false;
		storeItemRect.x = 0;
		storeItemRect.y = 0;
		storeItemRect.width = 0;
		storeItemRect.height = 0;
	}
	
	private void placeTower() {
		if (!isDraggingTower) {
			return;
		}
		
		Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		Point gridPos = realPosToGridPos(assumedCoords);
		if (gridPos == null) {
			return;
		}
		if (gridPos.x-1 < 0 || gridPos.x >= grid.getCols() || gridPos.y-1 < 0 || gridPos.y >= grid.getRows()) {
			return;
		}
		if (checkDoesTowerOverlap(gridPos.x, gridPos.y)) {
			return;
		}
		
		grid.setSpace(gridPos.x, gridPos.y, Grid.BLOCKED_SPACE);
		grid.setSpace(gridPos.x-1, gridPos.y, Grid.BLOCKED_SPACE);
		grid.setSpace(gridPos.x, gridPos.y-1, Grid.BLOCKED_SPACE);
		grid.setSpace(gridPos.x-1, gridPos.y-1, Grid.BLOCKED_SPACE);
		int x = indexToPosNoBuffer(gridPos.x);
		int y = indexToPosNoBuffer(gridPos.y);
		grid.addToGrid(new Tower(x, y, grid.getCellWidth()*2, store));
	}
	
	private int indexToPosNoBuffer(int index) {
		return index*grid.getCellWidth() + BORDER_WIDTH;
	}
	
	private boolean checkDoesTowerOverlap(int col, int row) {
		return grid.getGridMatrix()[col][row] == Grid.BLOCKED_SPACE ||
				grid.getGridMatrix()[col-1][row] == Grid.BLOCKED_SPACE ||
				grid.getGridMatrix()[col][row-1] == Grid.BLOCKED_SPACE ||
				grid.getGridMatrix()[col-1][row-1] == Grid.BLOCKED_SPACE;
	}
	
	public void dragTower(Rectangle r) {
		if (!hasClickedTower) {
			storeItemRect.x = r.x;
			storeItemRect.y = r.y;
			storeItemRect.width = r.width;
			storeItemRect.height = r.height;
			hasClickedTower = true;
		}
		
		dragOffsetX = surface.mouseX - storeItemRect.x;
		dragOffsetY = surface.mouseY - storeItemRect.y;
		isDraggingTower = true;
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
