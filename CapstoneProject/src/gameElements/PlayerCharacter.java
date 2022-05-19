package gameElements;

import java.awt.Point;
import java.util.ArrayList;

import core.DrawingSurface;

/**
 * This class represents the player character.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class PlayerCharacter extends GameElement{
	private Weapon weapon;
	private double realPosX,realPosY;
	private double moveSpeed;
	public PlayerCharacter(int x, int y) {
		super(x,y);
		realPosX = x;
		realPosY = y;
		weapon = new Weapon(this);
		moveSpeed = 3;
	}
	
	public void draw(DrawingSurface surface) {
		surface.fill(0);
		surface.circle(posX, posY, 20);
		weapon.draw(surface);
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
	
	public void moveWeapon(Point targetCoords) {
		weapon.turnTo(Math.atan2(targetCoords.y-posY,targetCoords.x-posX));

	}
	public void attack() {
		weapon.attack();
	}
	
	public double getMoveSpeed() {
		return moveSpeed;
	}

	public void act(ArrayList<Enemy> enemies) {
		weapon.act(enemies);
	}
	
	public boolean getIsAttacking() {
		return weapon.getIsAttacking();
	}
}
