package gameElements;

import core.DrawingSurface;

public abstract class GameElement {
	protected int posX, posY;
	
	public GameElement(int x, int y) {
		posX = x;
		posY = y;
	}
	
	public double distanceTo(GameElement g) {
		return (Math.sqrt(Math.pow(posX-g.getX(), 2)+Math.pow(posY-g.getY(), 2)));
	}
	public abstract void draw(DrawingSurface surface);
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
}
