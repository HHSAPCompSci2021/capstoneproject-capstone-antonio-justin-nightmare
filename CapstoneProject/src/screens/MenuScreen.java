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
		surface.rectMode(PConstants.CENTER);
		surface.strokeWeight(2);
		surface.fill(0, 200, 200);
		surface.rect(WIDTH-200, 200, 100, 100);
		surface.fill(200, 0, 255);
		surface.rect(WIDTH-400, 600, 100, 100);
		surface.fill(0, 200, 200);
		surface.rect(WIDTH-100, 450, 100, 100);
		surface.stroke(255,120,0);
		surface.line(WIDTH-200, 200, 300, 300);
		surface.line(WIDTH-400, 600, 200, 500);
		surface.stroke(0);
		surface.fill(200, 0, 0);
		surface.circle(200, 500, 150);
		surface.circle(100, 250, 150);
		surface.fill(200, 200, 0);
		surface.circle(300, 300, 75);
		surface.circle(125, 100, 75);
		surface.circle(150, 650, 75);
		
		surface.fill(0);
		surface.textAlign(PConstants.CENTER);
		surface.textSize(40);
		surface.text("Polygon Protection", WIDTH/2, 100);
		surface.fill(255, 150, 0);
		surface.stroke(0);
		surface.rect(WIDTH/2, HEIGHT/2, 200, 50);
		surface.fill(0);
		surface.textSize(30);
		surface.text("Play", WIDTH/2, HEIGHT/2 + 8);
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
