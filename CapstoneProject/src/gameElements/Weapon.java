package gameElements;

import java.util.ArrayList;

import core.DrawingSurface;

/**
 * This class represents a weapon.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class Weapon extends GameElement{
	private int attackDamage;
	private double attackSpeed, TBA;
	private double attackCooldown;
	// swingSpeed is how long it takes for the weapon to complete a full attack
	private double swingSpeed;
	private PlayerCharacter playerChar;
	private double angle,attackAngle;
	private final double WEAPON_LENGTH = 100;
	private boolean isAttacking;
	private ArrayList<Enemy> hitEnemies;
	public Weapon(PlayerCharacter playerCharacter) {
		super(playerCharacter.getX(),playerCharacter.getY());
		attackDamage = 3;
		attackSpeed = 1;
		TBA = 60/attackSpeed;
		attackCooldown = 0;
		swingSpeed = 15;
		playerChar = playerCharacter;
		angle = -Math.PI/2;
		attackAngle = 0;
		isAttacking = false;
		hitEnemies = new ArrayList<Enemy>();
	}

	public void draw(DrawingSurface surface) {
		surface.stroke(100,0,100);
		surface.line(playerChar.getX(), playerChar.getY(), (float)(playerChar.getX()+Math.cos(angle+attackAngle)*WEAPON_LENGTH), (float)(playerChar.getY()+Math.sin(angle+attackAngle)*WEAPON_LENGTH));
	}
	
	public int getAttackDamage() {
		return attackDamage;
	}
	
	public double getAttackSpeed() {
		return attackSpeed;
	}

	public void turnTo(double angle) {
		this.angle = angle+Math.PI/2;
	}
	
	public void attack() {
		if (attackCooldown <= 0) {
			isAttacking = true;
			attackCooldown = swingSpeed;
		}
	}

	public void act(ArrayList<Enemy> enemies) {
		if (isAttacking) {
			if (attackCooldown > 0) {
				for (Enemy e:enemies) {
					if (!hitEnemies.contains(e)) {
						if (e.distanceTo(playerChar) < WEAPON_LENGTH) {
							e.takeDamage(attackDamage);
							hitEnemies.add(e);
						}
					}
				}
				attackAngle-=0.2;
			} else {
				isAttacking = false;
				hitEnemies.clear();
				attackCooldown = TBA-swingSpeed;
			}
		} else {
			if (attackAngle < 0) {
				attackAngle += 0.4;
			}
		}
		attackCooldown--;
	}
}
