package core;
import java.awt.Point;
import java.util.ArrayList;

import processing.core.PApplet;
import screens.*;

public class DrawingSurface extends PApplet implements ScreenSwitcher{
	float ratioX, ratioY;
	private ArrayList<Integer> keys;
	private Screen activeScreen;
	private ArrayList<Screen> screens;
	
	public DrawingSurface() {
		screens = new ArrayList<Screen>();
		keys = new ArrayList<Integer>();
		screens.add(new MenuScreen(this));
		screens.add(new GameScreen(this));
		screens.add(new EndScreen(this));
		activeScreen = screens.get(0);
	}
	
	
	public void setup() {
		for (Screen s : screens)
			s.setup();
	}
	
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
	
	public void mouseMoved() {
		activeScreen.mouseMoved();
	}
	
	public void mouseDragged() {
		activeScreen.mouseDragged();
	}
	
	public void mouseReleased() {
		activeScreen.mouseReleased();
	}
	
	public Point assumedCoordinatesToActual(Point assumed) {
		return new Point((int)(assumed.getX()*ratioX), (int)(assumed.getY()*ratioY));
	}

	public Point actualCoordinatesToAssumed(Point actual) {
		return new Point((int)(actual.getX()/ratioX) , (int)(actual.getY()/ratioY));
	}

	@Override
	public void switchScreen(int i) {
		activeScreen = screens.get(i);
	}
}
