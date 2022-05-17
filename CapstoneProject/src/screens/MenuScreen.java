package screens;

import java.awt.Point;

import core.DrawingSurface;
import processing.core.PConstants;

/**
 * This class represents the menu screen.
 * @author Antonio Cuan
 *
 */
public class MenuScreen extends Screen{
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private DrawingSurface surface;
	/**
	 * creates a menu screen
	 */
	public MenuScreen(DrawingSurface surface) {
		super(WIDTH,HEIGHT);
		this.surface = surface;
	}
	
	public void draw() {
		surface.background(255);
		surface.fill(0);
		surface.textAlign(PConstants.CENTER);
		surface.textSize(40);
		surface.text("Polygon Protection", WIDTH/2, 100);
		surface.fill(200, 150, 0);
		surface.rectMode(PConstants.CENTER);
		surface.rect(WIDTH/2, HEIGHT/2, 200, 50);
		surface.fill(0);
		surface.textSize(30);
		surface.text("Start", WIDTH/2, HEIGHT/2 + 8);
	}
	
	public void mousePressed() {
		Point assumedCoords = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		boolean inX = assumedCoords.x >= WIDTH/2 - 100 && assumedCoords.x <= WIDTH/2 + 100;
		boolean inY = assumedCoords.y >= HEIGHT/2 - 25 && assumedCoords.y <= HEIGHT/2 + 25;
		if (inX && inY) {
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
		}
	}
}
