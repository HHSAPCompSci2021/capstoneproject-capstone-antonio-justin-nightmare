package screenElements;

import java.util.ArrayList;

import core.DrawingSurface;
import gameElements.*;

public class Grid {
	private int x,y,width,height;
	private ArrayList<Enemy> enemies;
	public Grid(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		// Sets the background
		surface.fill(255,0,0);
		surface.rect(x, y, width, height);
		
		// Draws all the enemies
		for (Enemy e:enemies) {
			e.draw();
		}
		surface.pop();
	}

	public void next() {
		for (Enemy e:enemies) {
			if (!e.act()) {
				enemies.remove(e);
			}
		}
	}
	
	public void addToGrid(Enemy e) {
		enemies.add(e);
	}
}
