package gameElements;

import core.DrawingSurface;

public class Projectile extends GameElement{
	private int damage;
	private Enemy target;
	private int damageDelay,damageStep;
	public Projectile(int damage, int x, int y) {
		super(x,y);
		this.damage = damage;
		damageDelay = 5;
		damageStep = damageDelay;
	}
	
	public Projectile(int damage, int x, int y, Enemy target) {
		super(x,y);
		this.damage = damage;
		this.target = target;
		damageDelay = 20;
		damageStep = damageDelay;
	}
	
	/**
	 * Draws the projectile, currently from tower location to location of target
	 */
	public void draw(DrawingSurface surface) {
		surface.stroke(255,120,0);
		surface.line(posX, posY, target.getX(), target.getY());
	}
	
	/**
	 * Deals damage once it is the correct time
	 * @return true if projectile has not yet dealt its damage (is still there), false if projectile has dealt its damage
	 */
	public boolean act() {
		if (damageStep > 0) {
			damageStep--;
			return true;
		} else {
			target.takeDamage(damage);
			return false;
		}
	}
	
	public void move() {
		
	}
}
