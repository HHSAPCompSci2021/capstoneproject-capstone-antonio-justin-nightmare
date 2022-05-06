package screenElements;

import core.DrawingSurface;

public class Store extends ScreenElement{
	public Store (int x, int y, int width, int height) {
		super(x,y,width,height);
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		surface.fill(0,255,0);
		surface.rect(x, y, width, height);
		surface.pop();
	}

	public void next() {
		// TODO Auto-generated method stub
		
	}
}
