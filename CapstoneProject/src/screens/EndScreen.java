package screens;

import core.DrawingSurface;

public class EndScreen extends Screen{
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private DrawingSurface surface;
	public EndScreen(DrawingSurface surface) {
		super(WIDTH,HEIGHT);
		this.surface = surface;
	}
	
	public void draw() {
		surface.text("You died", 0, 0);
	}
}
