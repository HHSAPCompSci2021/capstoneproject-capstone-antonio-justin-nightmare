package gameElements;

public abstract class GameElement {
	protected int posX, posY;
	
	public GameElement(int x, int y) {
		posX = x;
		posY = y;
	}
	
	public abstract void draw();
}
