package gameElements;

import java.util.ArrayList;

import core.DrawingSurface;

public class Tower extends GameElement{
	private final int price,attackDamage;
	private final double attackSpeed;
	// It stands for Time Between Attacks (in 60ths of a second)
	private final int TBA;
	private int attackCooldown;
	ArrayList<Projectile> projectiles;
	public Tower(int x, int y) {
		super(x,y);
		price = 100;
		attackDamage = 2;
		attackSpeed = 1;
		TBA = (int) (60/attackSpeed);
		attackCooldown = TBA;
		projectiles = new ArrayList<Projectile>();
	}
	
	public void draw(DrawingSurface surface) {
		surface.rectMode(DrawingSurface.CENTER);
		surface.fill(0,200,200);
		surface.rect(posX, posY, 10, 10);
		for (Projectile p:projectiles) {
			p.draw(surface);
		}
	}
	
	public void act(ArrayList<Enemy> enemies) {
		if (attackCooldown == TBA) {
			attack(enemies);
			attackCooldown--;
		} else if (attackCooldown > 0){
			attackCooldown--;
		} else {
			attackCooldown = TBA;
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (!projectiles.get(i).act()) {
				projectiles.remove(i);
				i--;
			}
		}
	}
	
	private void attack(ArrayList<Enemy> enemies) {
		double shortestDist = Double.MAX_VALUE;
		Enemy closestEnemy= null;
		for (Enemy e:enemies) {
			if (this.distanceTo(e) < shortestDist) {
				shortestDist = this.distanceTo(e);
				closestEnemy = e;
			}
		}
		projectiles.add(new Projectile(attackDamage,posX,posY,closestEnemy));
	}
}
