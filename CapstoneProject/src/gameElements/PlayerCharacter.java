package gameElements;

import core.DrawingSurface;

public class PlayerCharacter extends GameElement{
	private Weapon weapon;
	
	public PlayerCharacter(int x, int y) {
		super(x,y);
		weapon = new Weapon(x,y);
	}
	
	public void draw(DrawingSurface surface) {
		surface.fill(0);
		surface.circle(posX, posY, 10);
	}
	
	public void move() {
		
	}
	
	public void attack() {
		
	}
}
