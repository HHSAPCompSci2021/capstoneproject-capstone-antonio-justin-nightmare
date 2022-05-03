package screens;
import java.awt.event.KeyEvent;

import core.DrawingSurface;

public class GameScreen extends Screen{
	private DrawingSurface surface;
	public GameScreen(DrawingSurface surface) {
		super(1280, 720);
		this.surface = surface;
	}

	public void draw() {
		surface.background(150,150,200);
		surface.fill(100,0,100);
		surface.rect(20, 20, 200, 200);
		processKeyPresses();
	}
	
	private void processKeyPresses() {
		if (surface.isPressed(KeyEvent.VK_W)) {
			System.out.println("W pressed");
		}
	}
}
