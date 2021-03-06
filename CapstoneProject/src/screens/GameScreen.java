package screens;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import core.DrawingSurface;
import gameElements.*;
import processing.core.PConstants;
import screenElements.Grid;
import screenElements.Store;

/**
 * This class represents the game screen, where the player plays the game.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class GameScreen extends Screen{
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int BORDER_WIDTH = 20;
	private static final int GRID_WIDTH = 960;
	private int storeX;
	private int statusOffset;
	private int waveButtonPadding;
	private int waveButtonWidth;
	private int waveButtonHeight;
	private int waveButtonX, waveButtonY;
	private DrawingSurface surface;
	private Grid grid;
	private Store store;
	private Color waveButtonColor;
	private double gold;
	private int highlightedX, highlightedY;
	private Color highlightedColor;
	private int baseHealth;
	private boolean isTowerSelected;
	private Tower selectedTower;
	/**
	 * creates a GameScreen
	 */
	public GameScreen(DrawingSurface surface) {
		super(WIDTH, HEIGHT);
		storeX = 1000;
		statusOffset = 220;
		waveButtonPadding = 30;
		waveButtonWidth = WIDTH-storeX-BORDER_WIDTH - waveButtonPadding*2;
		waveButtonHeight = 30;
		waveButtonX = storeX + waveButtonPadding;
		waveButtonY = BORDER_WIDTH + waveButtonPadding/2;
		grid = new Grid(BORDER_WIDTH,BORDER_WIDTH,GRID_WIDTH,HEIGHT - BORDER_WIDTH*2, this);
		store = new Store(storeX, BORDER_WIDTH + waveButtonHeight + waveButtonPadding,
				WIDTH-storeX-BORDER_WIDTH, HEIGHT - BORDER_WIDTH*2 - waveButtonHeight - waveButtonPadding, this);
		this.surface = surface;
		waveButtonColor = new Color(255, 200, 200);
		gold = 400;
		highlightedColor = new Color(0, 255, 0, 100);
		baseHealth = 20;
		isTowerSelected = false;
	}

	public void draw() {
		if (baseHealth > 0) {
			surface.background(150,150,200);
			surface.fill(255);
			surface.stroke(255);
			surface.rect(indexToPosNoBuffer(grid.getGoalSpaces()[0].x), indexToPosNoBuffer(grid.getGoalSpaces()[0].y),
					grid.getCellWidth(), grid.getCellWidth());
			surface.rect(indexToPosNoBuffer(grid.getGoalSpaces()[1].x), indexToPosNoBuffer(grid.getGoalSpaces()[1].y),
					grid.getCellWidth(), grid.getCellWidth());
			grid.draw(surface);
			grid.next();
			store.draw(surface);
			highlightGrid();
			processKeyPresses();
			if (surface.mousePressed && !store.getIsItemSelected()) {
				grid.playerAttack();
			}
			
			surface.push();
			if (grid.getCanSpawnNextWave()) {
				surface.fill(waveButtonColor.getRed(), waveButtonColor.getGreen(), waveButtonColor.getBlue(), 255);
				surface.stroke(0, 0, 0, 255);
			} else {
				surface.fill(waveButtonColor.getRed(), waveButtonColor.getGreen(), waveButtonColor.getBlue(), 100);
				surface.stroke(0, 0, 0, 100);
			}
			surface.rect(waveButtonX, waveButtonY, waveButtonWidth, waveButtonHeight);
			if (grid.getCanSpawnNextWave()) {
				surface.fill(0, 0, 0, 255);
			} else {
				surface.fill(0, 0, 0, 100);
			}
			surface.textSize(18);
			surface.textAlign(PConstants.CENTER);
			surface.text("Start Next Wave",
					storeX + waveButtonPadding + waveButtonWidth/2,
					BORDER_WIDTH + waveButtonPadding/2 + waveButtonHeight - 10);
			surface.fill(0,0,0);
			surface.textAlign(PConstants.RIGHT);
			surface.textSize(15);
			surface.text("Gold: "+(int)gold,
					WIDTH-BORDER_WIDTH-statusOffset,
					BORDER_WIDTH + waveButtonHeight + waveButtonPadding,
					200, 20);
			surface.text("Base health: "+baseHealth, WIDTH-BORDER_WIDTH-statusOffset, 5, 200, 20);
			surface.textAlign(PConstants.LEFT);
			surface.text("Wave: "+(grid.getWaveNum()-1), storeX, 5, 200, 20);
			if (isTowerSelected) {
				surface.textAlign(PConstants.LEFT);
				store.displayTowerLevel(surface, selectedTower);
			}
			
			if (grid.getHasWonGame()) {
				surface.textSize(50);
				surface.textAlign(PConstants.CENTER);
				surface.text("Game Complete!", WIDTH/2, HEIGHT/2);
			}
			surface.pop();
		} else {
			surface.switchScreen(ScreenSwitcher.END_SCREEN);
		}
	}
	
	/**
	 * calculates the grid position of a real screen position
	 * @param p screen position
	 * @return grid position
	 */
	public Point realPosToGridPos(Point p) {
		int gridX = (int)((p.x - BORDER_WIDTH)/grid.getCellWidth());
		int gridY = (int)((p.y - BORDER_WIDTH)/grid.getCellWidth());
		
		if (gridX < 0 || gridY < 0 || gridX >= grid.getCols() || gridY >= grid.getRows()) {
			return null;
		}
		
		return new Point(gridX, gridY);
	}
	
	/**
	 * calculates the grid index of a real screen x or y position
	 * @param index grid index
	 * @return screen x or y position
	 * @precondition index is in bounds for the grid
	 */
	public int indexToPos(int index) {
		return index*grid.getCellWidth() + BORDER_WIDTH + grid.getCellWidth()/2;
	}
	
	private void highlightGrid() {
		if (!store.getIsItemSelected()) {
			return;
		}
		
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
		
		if (store.getIsItemSelected()) {
			placeTower();
		} else {
			grid.playerAttack();
		}
		
		if (checkIsPointInWaveButton(assumedCoords) && grid.getCanSpawnNextWave()) {
			grid.spawnWave();
			grid.setCanSpawnWaveFalse();
		}
		
		selectTower(assumedCoords);
		
		if (isTowerSelected) {
			if (store.checkIsPointInUpgradeButton(assumedCoords)) {
				upgradeTower();
			}
			
			if (store.checkIsPointInSellButton(assumedCoords)) {
				sellTower();
			}
		}
	}
	
	public void mouseMoved() {
		grid.playerMoveWeapon(surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY)));
	}
	
	public void mouseDragged() {
		if (!grid.getIsPlayerAttacking()) {
			grid.playerMoveWeapon(surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY)));
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
		if (checkDoesTowerOverlap(gridPos)) {
			return;
		}
		if (checkIsPathBlocked(gridPos)) {
			return;
		}
		if (checkIsBlockingSymmetricGoal(gridPos)) {
			return;
		}
		if (checkIsOnEnemy(gridPos)) {
			return;
		}
		
		int x = indexToPosNoBuffer(gridPos.x+1);
		int y = indexToPosNoBuffer(gridPos.y+1);
		Tower tower = new Tower(x, y, grid.getCellWidth()*2, store);
		if (gold - store.getTowerPrice() < 0) {
			return;
		}
		grid.setSpace(gridPos.x, gridPos.y, Grid.BLOCKED_SPACE);
		grid.setSpace(gridPos.x+1, gridPos.y, Grid.BLOCKED_SPACE);
		grid.setSpace(gridPos.x, gridPos.y+1, Grid.BLOCKED_SPACE);
		grid.setSpace(gridPos.x+1, gridPos.y+1, Grid.BLOCKED_SPACE);
		grid.computeFlowField();
		grid.addToGrid(tower);
		gold -= store.getTowerPrice();
	}
	
	private int indexToPosNoBuffer(int index) {
		return index*grid.getCellWidth() + BORDER_WIDTH;
	}
	
	private boolean checkDoesTowerOverlap(Point space) {
		return grid.getGridMatrix()[space.x][space.y] == Grid.BLOCKED_SPACE ||
				grid.getGridMatrix()[space.x+1][space.y] == Grid.BLOCKED_SPACE ||
				grid.getGridMatrix()[space.x][space.y+1] == Grid.BLOCKED_SPACE ||
				grid.getGridMatrix()[space.x+1][space.y+1] == Grid.BLOCKED_SPACE;
	}
	
	private boolean checkIsPathBlocked(Point gPos) {
		int[][] gMatrix = new int[grid.getCols()][grid.getRows()];
		for (int col = 0; col < grid.getCols(); col++) {
			for (int row = 0; row < grid.getRows(); row++) {
				gMatrix[col][row] = grid.getGridMatrix()[col][row];
			}
		}
		grid.clearGridPathSpaces(gMatrix);
		
		gMatrix[gPos.x][gPos.y] = Grid.BLOCKED_SPACE;
		gMatrix[gPos.x+1][gPos.y] = Grid.BLOCKED_SPACE;
		gMatrix[gPos.x][gPos.y+1] = Grid.BLOCKED_SPACE;
		gMatrix[gPos.x+1][gPos.y+1] = Grid.BLOCKED_SPACE;
		
		Point[][] flowField = grid.breadthFirstSearch(gMatrix);
		
		for (Point space : grid.getStartSpaces()) {
			if (flowField[space.x][space.y] == null) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkIsOnEnemy(Point gPos) {
		Point[] enemySpaces = grid.getEnemySpaces();
		for (Point space : enemySpaces) {
			boolean result = gPos.x == space.x && gPos.y == space.y ||
					gPos.x+1 == space.x && gPos.y == space.y ||
					gPos.x == space.x && gPos.y+1 == space.y ||
					gPos.x+1 == space.x && gPos.y+1 == space.y;
			if (result == true) {
				return true; 
			}
		}
		
		return false;
	}
	
	private boolean checkIsBlockingSymmetricGoal(Point gPos) {
		Point space = grid.getGoalSpaces()[1];
		space.setLocation(space.x-1, space.y);
		return gPos.x == space.x && gPos.y == space.y ||
				gPos.x+1 == space.x && gPos.y == space.y ||
				gPos.x == space.x && gPos.y+1 == space.y ||
				gPos.x+1 == space.x && gPos.y+1 == space.y;
	}
	
	private boolean checkIsPointInWaveButton(Point p) {
		boolean inWaveButtonX = p.x >= waveButtonX && p.x <= waveButtonX+waveButtonWidth;
		boolean inWaveButtonY = p.y >= waveButtonY && p.y <= waveButtonY+waveButtonHeight;
		return inWaveButtonX && inWaveButtonY;
	}
	
	private void selectTower(Point p) {
		if (store.checkIsPointInUpgradeButton(p) || store.checkIsPointInSellButton(p)) {
			return;
		}
		
		if (selectedTower != null) {
			if (selectedTower.getIsSelected()) {
				selectedTower.toggleSelect();
				isTowerSelected = false;
			}
		}
		
		ArrayList<Tower> towers = grid.getTowers();
		for (Tower t : towers) {
			if (t.isPointInTower(p)) {
				t.toggleSelect();
				selectedTower = t;
				isTowerSelected = true;
				break;
			}
		}
	}
	
	private void upgradeTower() {
		if (gold - store.getTowerUpgradePrice() < 0) {
			return;
		}
		
		if (selectedTower.getLevel() + 1 > selectedTower.getMaxLevel()) {
			return;
		}
		
		selectedTower.upgradeTower();
		gold -= store.getTowerUpgradePrice();
		
	}
	
	private void sellTower() {
		isTowerSelected = false;
		Point gridPos = realPosToGridPos(new Point(selectedTower.getX(), selectedTower.getY()));
		grid.setSpace(gridPos.x, gridPos.y, Grid.EMPTY_SPACE);
		grid.setSpace(gridPos.x-1, gridPos.y, Grid.EMPTY_SPACE);
		grid.setSpace(gridPos.x, gridPos.y-1, Grid.EMPTY_SPACE);
		grid.setSpace(gridPos.x-1, gridPos.y-1, Grid.EMPTY_SPACE);
		grid.removeFromGrid(selectedTower);
		gold += store.getTowerPrice();
		grid.computeFlowField();
	}
	
	/**
	 * returns the border width
	 * @return border width
	 */
	public int getBorderWidth() {
		return BORDER_WIDTH;
	}
	
	private void processKeyPresses() {
		int[] directions = {0,0};
		// W is down and S is up because of the way the coordinates work
		if (surface.isPressed(KeyEvent.VK_W)) {
			directions[1]--;
		} 
		if (surface.isPressed(KeyEvent.VK_A)) {
			directions[0]--;
		}
		if (surface.isPressed(KeyEvent.VK_S)) {
			directions[1]++;
		}
		if (surface.isPressed(KeyEvent.VK_D)) {
			directions[0]++;
		}
		grid.movePlayer(directions);
		
		if (surface.isPressed(KeyEvent.VK_SPACE)) {
			if (store.getIsItemSelected()) {
				store.toggleItemSelect();
			}
		}
	}
	
	/**
	 * adds gold to the player's gold amount
	 * @param amount amount of gold to add
	 */
	public void addGold(int amount) {
		gold += amount;
	}
	
	/**
	 * makes the player's base take damage by the specified amount
	 * @param amount amount of damage to deal
	 */
	public void takeDamage(int amount) {
		baseHealth -= amount;
	}
	
	/**
	 * returns status of a tower being selected
	 * @return true if a tower is selected, false otherwise
	 */
	public boolean getIsTowerSelected() {
		return isTowerSelected;
	}
}
