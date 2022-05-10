package gameElements;

import java.util.ArrayList;

import core.DrawingSurface;

public class Tower extends GameElement{
	int price,attackDamage;
	double attackSpeed;
	ArrayList<Projectile> projectiles;
	public Tower(int x, int y) {
		super(x,y);
		price = 100;
		attackDamage = 2;
		attackSpeed = 1;
		projectiles = new ArrayList<Projectile>();
	}
	
	public void draw(DrawingSurface surface) {
		surface.rectMode(surface.CENTER);
		surface.fill(0,200,200);
		surface.rect(posX, posY, 10, 10);
		for (Projectile p:projectiles) {
			p.draw(surface);
		}
	}
	
	public void act(ArrayList<Enemy> enemies) {
		attack(enemies);
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
