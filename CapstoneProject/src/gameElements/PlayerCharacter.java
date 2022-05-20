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
		surface.stroke(0);
		surface.circle(posX, posY, 20);
		weapon.draw(surface);
	}
	
	/**
	 * Moves the player by the specified amounts
	 * @param moveAmounts First variable is change in x-value, second variable is change in y-value
	 */
	public void move(double[] moveAmounts) {
		realPosX += moveAmounts[0];
		realPosY += moveAmounts[1];
		posX = (int) realPosX;
		posY = (int) realPosY;
	}
	
	/**
	 * Moves the player to a specific coordinate
	 * @param x The x-coordinate of the target location
	 * @param y The y-coordinate of the target location
	 */
	public void moveTo(int x, int y) {
		realPosX = x;
		realPosY = y;
		posX = (int) realPosX;
		posY = (int) realPosY;
	}
	
	/**
	 * Moves the weapon to face a certain location
	 * @param targetCoords Represents the location that the player is turned to face
	 */
	public void moveWeapon(Point targetCoords) {
		weapon.turnTo(Math.atan2(targetCoords.y-posY,targetCoords.x-posX));

	}
	
	/**
	 * Makes the weapon begin attacking if it is off cooldown
	 */
	public void attack() {
		weapon.attack();
	}
	
	/**
	 * Returns the move speed of the player
	 * @return The move speed of the player
	 */
	public double getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * Performs one step
	 * @param enemies All enemies in the grid
	 */
	public void act(ArrayList<Enemy> enemies) {
		weapon.act(enemies);
	}
	
	/**
	 * Returns whether the player is currently attacking
	 * @return True if player is attacking, false if player is not
	 */
	public boolean getIsAttacking() {
		return weapon.getIsAttacking();
	}
}
