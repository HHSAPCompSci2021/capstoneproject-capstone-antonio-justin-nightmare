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
	private static final double GOLD_PER_SECOND = 5;
	private DrawingSurface surface;
	private Grid grid;
	private Store store;
	private double gold;
	private int dragOffsetX, dragOffsetY;
	private Rectangle storeItemRect;
	private boolean hasClickedTower;
	private int draggedTowerAlpha;
	public GameScreen(DrawingSurface surface) {
		super(WIDTH, HEIGHT);
		grid = new Grid(BORDER_WIDTH,BORDER_WIDTH,960,HEIGHT - BORDER_WIDTH*2,this);
		grid.setScreenBorderWidth(BORDER_WIDTH);
		store = new Store(1000,BORDER_WIDTH,WIDTH-1000-BORDER_WIDTH,HEIGHT - BORDER_WIDTH*2);
		this.surface = surface;
		grid.addToGrid(new Enemy(indexToPos(0),indexToPos(30)));
		grid.addToGrid(new Tower(indexToPos(1),indexToPos(25)));
		gold = 0;
		storeItemRect = new Rectangle();
		hasClickedTower = false;
		draggedTowerAlpha = 100;
	}

	public void draw() {
		surface.background(150,150,200);
		fillGrid();
		grid.draw(surface);
		grid.next();
		store.draw(surface);
		surface.fill(store.getItemColor().getRed(), store.getItemColor().getGreen(), store.getItemColor().getBlue(), draggedTowerAlpha);
		surface.stroke(store.getItemColor().getRed(), store.getItemColor().getGreen(), store.getItemColor().getBlue(), draggedTowerAlpha);
		surface.rect(storeItemRect.x, storeItemRect.y, storeItemRect.width, storeItemRect.height);
		processKeyPresses();
		
		surface.push();
		surface.fill(0,0,0);
		surface.textAlign(surface.RIGHT);
		//surface.textSize(100);
		surface.text("Gold: "+(int)gold, WIDTH-BORDER_WIDTH-100, BORDER_WIDTH, 100,100);
		surface.pop();
		
		gold += GOLD_PER_SECOND/60;
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
		if (store.getStoreItemRefRect().contains(surface.mouseX, surface.mouseY)) {
			dragTower(store.getStoreItemRefRect());
		}
		
		if (surface.mouseButton == PConstants.LEFT) {
			Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
			Point cellCoord = posToIndex(assumedCoords);
			if (cellCoord != null) {
				// for testing
//				grid.setSpace(cellCoord.x, cellCoord.y);
			}
		}
	}
	
	public void mouseDragged() {
		if (storeItemRect != null) {
			storeItemRect.x = surface.mouseX - dragOffsetX;
			storeItemRect.y = surface.mouseY - dragOffsetY;
		}
		
		// for testing
//		if (surface.mouseButton == PConstants.LEFT) {
//			Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
//			Point cellCoord = posToIndex(assumedCoords);
//			if (cellCoord != null) {
//				grid.setSpace(cellCoord.x, cellCoord.y);
//			}
//		}
	}
	
	public void mouseReleased() {
		storeItemRect.x = 0;
		storeItemRect.y = 0;
		storeItemRect.width = 0;
		storeItemRect.height = 0;
		hasClickedTower = false;
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
}
