package screenElements;

import core.DrawingSurface;
import processing.core.PConstants;

public class Store extends ScreenElement{
	private int storeItemWidth;
	private int headerSize;
	
	public Store (int x, int y, int width, int height) {
		super(x,y,width,height);
		storeItemWidth = (int)(width*0.25);
		headerSize = 30;
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		surface.fill(0,255,0);
		surface.rect(x, y, width, height);
		surface.fill(0);
		surface.textSize(headerSize);
		surface.text("Store", x + width*0.1f, y + width*0.1f + headerSize*0.5f);
		surface.fill(0, 0, 255);
		surface.rectMode(PConstants.CENTER);
		surface.rect(x+width/2, y+storeItemWidth/2 + headerSize*2, storeItemWidth, storeItemWidth);
		surface.pop();
	}
}
