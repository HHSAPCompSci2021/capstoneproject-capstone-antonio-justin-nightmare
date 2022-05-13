package gameElements;

import java.util.Arrays;

import core.DrawingSurface;

public class PlayerCharacter extends GameElement{
	private Weapon weapon;
	private double realPosX,realPosY;
	private double moveSpeed;
	public PlayerCharacter(int x, int y) {
		super(x,y);
		realPosX = x;
		realPosY = y;
		weapon = new Weapon(x,y);
		moveSpeed = 2;
	}
	
	public void draw(DrawingSurface surface) {
		surface.fill(0);
		surface.circle(posX, posY, 10);
	}
	
	public void move(double[] moveAmounts) {
		realPosX += moveAmounts[0];
		realPosY += moveAmounts[1];
		posX = (int) realPosX;
		posY = (int) realPosY;
	}
	
	public void moveTo(int x, int y) {
		realPosX = x;
		realPosY = y;
		posX = (int) realPosX;
		posY = (int) realPosY;
	}
	public void attack() {
		
	}
	
	public double getMoveSpeed() {
		return moveSpeed;
	}
}
