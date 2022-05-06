package screenElements;

import java.util.ArrayList;

import core.DrawingSurface;
import gameElements.*;

public class Grid extends ScreenElement{
	private ArrayList<Enemy> enemies;
	public Grid(int x, int y, int width, int height) {
		super(x,y,width,height);
		enemies = new ArrayList<Enemy>();
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		// Sets the background
		surface.fill(255,0,0);
		surface.rect(x, y, width, height);
		
		// Draws all the enemies
		for (Enemy e:enemies) {
			e.draw(surface,this);
		}
		surface.pop();
	}

	public void next() {
		for (Enemy e:enemies) {
			e.act(this);
		}
	}
	
	public void addToGrid(Enemy e) {
		enemies.add(e);
	}
	
	public void removeFromGrid(Enemy e) {
		enemies.remove(e);
	}
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
