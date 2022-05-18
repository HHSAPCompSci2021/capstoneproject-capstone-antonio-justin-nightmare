package gameElements;

import java.util.ArrayList;

import core.DrawingSurface;

/**
 * This class represents a weapon.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class Weapon extends GameElement{
	int attackDamage;
	double attackSpeed, TBA;
	double attackCooldown;
	PlayerCharacter playerChar;
	double angle;
	private final double WEAPON_LENGTH = 100;
	public Weapon(PlayerCharacter playerCharacter) {
		super(playerCharacter.getX(),playerCharacter.getY());
		attackDamage = 3;
		attackSpeed = 1;
		TBA = 60/attackSpeed;
		attackCooldown = 0;
		playerChar = playerCharacter;
		angle = Math.PI;
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
	
	public void attack(ArrayList<Enemy> enemies) {
		if (attackCooldown <= 0) {
			for (Enemy e:enemies) {
				if (e.distanceTo(playerChar) < WEAPON_LENGTH) {
					e.takeDamage(attackDamage);
				}
			}
			attackCooldown = TBA;
		}
	}

	public void act() {
		attackCooldown--;
	}
}
