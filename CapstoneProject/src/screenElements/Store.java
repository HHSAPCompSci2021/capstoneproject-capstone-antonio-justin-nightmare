package screenElements;

import java.awt.Color;
import java.awt.Point;

import core.DrawingSurface;
import screens.GameScreen;

/**
 * This class represents a store.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class Store extends ScreenElement{
	private Color color;
	private int itemWidth;
	private int headerSize;
	private int itemX, itemY;
	private Color itemColor;
	private boolean isItemSelected;
	private Color highlightColor;
	private int highlightWeight;
	private int towerPrice;
	/**
	 * creates a store with the specified position and dimensions
	 * @param x x position
	 * @param y y position
	 * @param width width
	 * @param height height
	 */
	public Store (int x, int y, int width, int height) {
		super(x,y,width,height);
		color = new Color(200, 150, 0);
		itemWidth = (int)(width*0.25);
		headerSize = 30;
		itemX = x + width/2 - itemWidth/2;
		itemY = y+itemWidth/2 - itemWidth/2 + headerSize*2;
		itemColor = new Color(0, 200, 200);
		isItemSelected = false;
		highlightColor = new Color(0, 255, 0);
		highlightWeight = 5;
		towerPrice = 100;
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		surface.fill(color.getRed(), color.getGreen(), color.getBlue());
		surface.stroke(0);
		surface.rect(x, y, width, height);
		surface.fill(0);
		surface.textSize(headerSize);
		surface.text("Store", x + width*0.1f, y + width*0.1f + headerSize*0.5f);
		surface.fill(itemColor.getRed(), itemColor.getGreen(), itemColor.getBlue());
		if (isItemSelected) {
			surface.stroke(highlightColor.getRed(), highlightColor.getGreen(), highlightColor.getBlue());
			surface.strokeWeight(highlightWeight);
		} else {
			surface.stroke(0);
			surface.strokeWeight(1);
		}
		surface.rect(itemX, itemY, itemWidth, itemWidth);
		surface.fill(0);
		surface.textSize(15);
		surface.text("Tower", itemX, itemY+itemWidth+15);
		surface.text("Cost: " + towerPrice, itemX, itemY+itemWidth+30);
		surface.pop();
	}
	
	/**
	 * returns the tower item color
	 * @return color
	 */
	public Color getItemColor() {
		return itemColor;
	}
	
	/**
	 * returns status of the specified point being in the tower item
	 * @param p position
	 * @return true if the specified position is in the tower item, false otherwise
	 */
	public boolean checkIsPointInItem(Point p) {
		if (p.x >= itemX && p.x <= itemX+itemWidth && p.y >= itemY && p.y <= itemY+itemWidth) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * toggles whether tower item is selected or not
	 */
	public void toggleItemSelect() {
		isItemSelected = !isItemSelected;
	}
	
	/**
	 * returns status of the tower item being selected
	 * @return true if tower item is selected, false otherwise
	 */
	public boolean getIsItemSelected() {
		return isItemSelected;
	}
	
	/**
	 * returns the tower price
	 * @return price
	 */
	public int getTowerPrice() {
		return towerPrice;
	}
}
