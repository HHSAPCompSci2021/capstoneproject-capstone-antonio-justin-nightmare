package gameElements;

import core.DrawingSurface;

public class Projectile extends GameElement{
	private int damage;
	private Enemy target;
	private int damageDelay,damageStep;
	public Projectile(int damage, int x, int y) {
		super(x,y);
		this.damage = damage;
		damageDelay = 20;
		damageStep = damageDelay;
	}
	
	public Projectile(int damage, int x, int y, Enemy target) {
		super(x,y);
		this.damage = damage;
		this.target = target;
		damageDelay = 20;
		damageStep = damageDelay;
	}
	
	public void draw(DrawingSurface surface) {
		//System.out.println("drawing");
		surface.stroke(255,255,0);
		surface.line(posX, posY, target.getX(), target.getY());
	}
	
	public boolean act() {
		System.out.println(damageStep);
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
