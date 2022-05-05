package screenElements;

import core.DrawingSurface;

public class Store {
	private int x,y,width,height;
	public Store (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		surface.fill(0,255,0);
		surface.rect(x, y, width, height);
		surface.pop();
	}
}
