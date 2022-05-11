package screenElements;

import java.awt.Rectangle;

import core.DrawingSurface;
import processing.core.PConstants;
import screens.GameScreen;

public class Store extends ScreenElement{
	private int storeItemWidth;
	private int headerSize;
	private Rectangle storeItemRefRect;
	private int storeItemX, storeItemY;
	private GameScreen gScreen;
	public Store (int x, int y, int width, int height, GameScreen sc) {
		super(x,y,width,height);
		storeItemWidth = (int)(width*0.25);
		headerSize = 30;
		storeItemX = x+width/2;
		storeItemY = y+storeItemWidth/2 + headerSize*2;
		storeItemRefRect = new Rectangle(storeItemX, storeItemY, storeItemWidth, storeItemWidth);
		gScreen = sc;
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		surface.fill(0, 200, 0);
		surface.stroke(0);
		surface.rect(x, y, width, height);
		surface.fill(0);
		surface.textSize(headerSize);
		surface.text("Store", x + width*0.1f, y + width*0.1f + headerSize*0.5f);
		surface.fill(0, 0, 255);
		surface.stroke(0, 0, 255);
		surface.rectMode(PConstants.CENTER);
		surface.rect(storeItemX, storeItemY, storeItemWidth, storeItemWidth);
		surface.pop();
	}
	
	public Rectangle getStoreItemRefRect() {
		return storeItemRefRect;
	}
}
