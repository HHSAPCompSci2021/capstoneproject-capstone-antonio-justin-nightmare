package screens;
import java.awt.event.KeyEvent;

import core.DrawingSurface;
import gameElements.*;
import screenElements.Grid;
import screenElements.Store;

public class GameScreen extends Screen{
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int BORDER_WIDTH = 20;
	private DrawingSurface surface;
	private Grid grid;
	private Store store;
	public GameScreen(DrawingSurface surface) {
		super(WIDTH, HEIGHT);
		grid = new Grid(BORDER_WIDTH,BORDER_WIDTH,960,HEIGHT - BORDER_WIDTH*2);
		store = new Store(1000,BORDER_WIDTH,WIDTH-1000-BORDER_WIDTH,HEIGHT - BORDER_WIDTH*2);
		this.surface = surface;
		grid.addToGrid(new Enemy(30,30));
	}

	public void draw() {
		surface.background(150,150,200);
		grid.draw(surface);
		grid.next();
		store.draw(surface);
		store.next();
		processKeyPresses();
	}
	
	private void processKeyPresses() {
		if (surface.isPressed(KeyEvent.VK_W)) {
			System.out.println("W pressed");
		}
	}
}
