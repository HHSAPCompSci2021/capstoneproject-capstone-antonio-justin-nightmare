package screenElements;

import core.DrawingSurface;

public class Grid {
	private int x,y,width,height;
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
}
