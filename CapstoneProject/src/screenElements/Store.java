package screenElements;

import java.awt.Color;
import java.awt.Rectangle;

import core.DrawingSurface;
import processing.core.PConstants;
import screens.GameScreen;

public class Store extends ScreenElement{
	private int itemWidth;
	private int headerSize;
	private Rectangle itemRefRect;
	private int itemX, itemY;
	private Color itemColor;
	public Store (int x, int y, int width, int height) {
		super(x,y,width,height);
		itemWidth = (int)(width*0.25);
		headerSize = 30;
		itemX = x + width/2 - itemWidth/2;
		itemY = y+itemWidth/2 - itemWidth/2 + headerSize*2;
		itemRefRect = new Rectangle(itemX, itemY, itemWidth, itemWidth);
		itemColor = new Color(0, 0, 255);
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		surface.fill(0, 200, 0);
		surface.stroke(0);
		surface.rect(x, y, width, height);
		surface.fill(0);
		surface.textSize(headerSize);
		surface.text("Store", x + width*0.1f, y + width*0.1f + headerSize*0.5f);
		surface.fill(itemColor.getRed(), itemColor.getGreen(), itemColor.getBlue());
		surface.stroke(itemColor.getRed(), itemColor.getGreen(), itemColor.getBlue());
		surface.rect(itemX, itemY, itemWidth, itemWidth);
		surface.pop();
	}
	
	public Rectangle getStoreItemRefRect() {
		return itemRefRect;
	}
	
	public Color getItemColor() {
		return itemColor;
	}
}
