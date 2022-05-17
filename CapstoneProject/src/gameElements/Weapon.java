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
	double angle;
	private final double WEAPON_LENGTH = 20;
	public Weapon(PlayerCharacter playerCharacter) {
		super(playerCharacter.getX(),playerCharacter.getY());
		attackDamage = 3;
		attackSpeed = 1;
		playerChar = playerCharacter;
		angle = 0;
	}

	public void draw(DrawingSurface surface) {
		surface.stroke(100,0,100);
		surface.line(playerChar.getX(), playerChar.getY(), (float)(playerChar.getX()+Math.cos(angle)*WEAPON_LENGTH), (float)(playerChar.getY()+Math.sin(angle)*WEAPON_LENGTH));
	}
	
	public int getAttackDamage() {
		return attackDamage;
	}
	
	public double getAttackSpeed() {
		return attackSpeed;
	}

	public void turnTo(double angle) {
		this.angle = angle;
	}
	
	public void updateEndpoints() {
		
	}
}
