package screens;

import core.DrawingSurface;
import processing.core.*;

/**
 * This class represents the end screen.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class EndScreen extends Screen{
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private DrawingSurface surface;
	/**
	 * creates an end screen
	 */
	public EndScreen(DrawingSurface surface) {
		super(WIDTH,HEIGHT);
		this.surface = surface;
	}
	
	public void draw() {
		surface.background(255);
		surface.fill(20);
		surface.textAlign(PConstants.CENTER);
		surface.text("Game Over\nPress Enter to restart.", WIDTH/2, HEIGHT/2);
	}
}
