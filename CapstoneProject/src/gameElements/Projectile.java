package gameElements;

import core.DrawingSurface;

public class Projectile extends GameElement{
	private int damage;
	private Enemy target;
	public Projectile(int damage, int x, int y) {
		super(x,y);
		this.damage = damage;
	}
	
	public Projectile(int damage, int x, int y, Enemy target) {
		super(x,y);
		this.damage = damage;
		this.target = target;
	}
	
	public void draw(DrawingSurface surface) {
		System.out.println("drawing");
		surface.fill(255,255,0);
		surface.line(posX, posY, target.getX(), target.getY());
	}
	
	public boolean act() {
		System.out.println("dealing damage");
		return target.takeDamage(0);
	}
	
	public void move() {
		
	}
}
