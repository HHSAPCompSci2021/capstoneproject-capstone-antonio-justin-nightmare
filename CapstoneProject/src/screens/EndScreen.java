package screens;

import core.DrawingSurface;
import processing.core.*;

public class EndScreen extends Screen{
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private DrawingSurface surface;
	public EndScreen(DrawingSurface surface) {
		super(WIDTH,HEIGHT);
		this.surface = surface;
	}
	
	public void draw() {
		surface.background(255);
		surface.fill(20);
		surface.textAlign(PConstants.CENTER);
		surface.text("You died\nPress Enter to restart", WIDTH/2, HEIGHT/2);
	}
}
