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
		surface.fill(255,0,0);
		surface.rect(x, y, width, height);
		surface.pop();
	}

	public void next() {
		for (Enemy e:enemies) {
			e.act();
		}
	}
}
