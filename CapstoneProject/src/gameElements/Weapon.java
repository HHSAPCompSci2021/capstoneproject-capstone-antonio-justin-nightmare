package gameElements;

import core.DrawingSurface;

/**
 * This class represents a weapon.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class Weapon extends GameElement{
	int attackDamage;
	double attackSpeed;
	PlayerCharacter playerChar;
	
	public Weapon(PlayerCharacter playerCharacter) {
		super(playerCharacter.getX(),playerCharacter.getY());
		attackDamage = 3;
		attackSpeed = 1;
		playerChar = playerCharacter;
	}

	public void draw(DrawingSurface surface) {
		surface.stroke(100,0,100);
		surface.line(playerChar.getX(), playerChar.getY(), playerChar.getX()-20, playerChar.getY());
	}
	
	public int getAttackDamage() {
		return attackDamage;
	}
	
	public double getAttackSpeed() {
		return attackSpeed;
	}
}
