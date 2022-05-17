package core;
import java.awt.Point;
import java.util.ArrayList;

import processing.core.PApplet;
import screens.*;

/**
 * This class represents the drawing surface on which the game is displayed.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class DrawingSurface extends PApplet implements ScreenSwitcher{
	float ratioX, ratioY;
	private ArrayList<Integer> keys;
	private Screen activeScreen;
	private ArrayList<Screen> screens;
	
	/**
	 * initializes screens and displays the first screen
	 */
	public DrawingSurface() {
		screens = new ArrayList<Screen>();
		keys = new ArrayList<Integer>();
		screens.add(new MenuScreen(this));
		screens.add(new GameScreen(this));
		screens.add(new EndScreen(this));
		activeScreen = screens.get(0);
	}
	
	// delete if not needed
//	public void setup() {
//		for (Screen s : screens)
//			s.setup();
//	}
	
	public void draw() {
		ratioX = (float)width/activeScreen.DRAWING_WIDTH;
		ratioY = (float)height/activeScreen.DRAWING_HEIGHT;

		push();
		
		scale(ratioX, ratioY);
		
		activeScreen.draw();
		
		pop();
	}
	
	public void keyPressed() {
		if (key == ENTER || key == RETURN) {
			if (activeScreen instanceof EndScreen) {
				screens.set(ScreenSwitcher.GAME_SCREEN,new GameScreen(this));
				switchScreen(ScreenSwitcher.GAME_SCREEN);
			}
		}
		
		keys.add(keyCode);
		
		if (key == ESC)  // This prevents a processing program from closing on escape key
			key = 0;
	}

	public void keyReleased() {
		while(keys.contains(keyCode)) {
			keys.remove(Integer.valueOf(keyCode));
		}
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
	public void mousePressed() {
		activeScreen.mousePressed();
	}
	
	// remove if not needed
//	public void mouseMoved() {
//		activeScreen.mouseMoved();
//	}
	
	// remove if not needed
//	public void mouseDragged() {
//		activeScreen.mouseDragged();
//	}
	
	// remove if not needed
//	public void mouseReleased() {
//		activeScreen.mouseReleased();
//	}
	
	/**
	 * calculates the actual Java window coordinates of a Processing coordinate
	 * @param assumed Processing coordinates of a point
	 * @return actual Java coordinates
	 */
	public Point assumedCoordinatesToActual(Point assumed) {
		return new Point((int)(assumed.getX()*ratioX), (int)(assumed.getY()*ratioY));
	}

	/**
	 * calculates the assumed Processing coordinates of a Java window coordinate
	 * @param actual Java coordinates
	 * @return assumed Processing coordinates
	 */
	public Point actualCoordinatesToAssumed(Point actual) {
		return new Point((int)(actual.getX()/ratioX) , (int)(actual.getY()/ratioY));
	}

	@Override
	public void switchScreen(int i) {
		activeScreen = screens.get(i);
	}
}
